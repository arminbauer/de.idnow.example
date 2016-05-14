package services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import dao.CompanyStore;
import models.Company;
import models.Identification;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * In memory data structure which represents a queue of {@link Identification} for further processing in certain order.
 * Companies are not stored here in order to avoid addition update operations, e.g. {@link Company#currentSlaPercentage}
 * was re-calculated and so on.
 * <p>
 * NOTE: {@link IdentificationsQueue#queue} might be easily used with a {@link ExecutorService} for asynchronous processing.
 */
@Singleton
public class IdentificationsQueue {

    public static final int DEFAULT_INITIAL_CAPACITY = 1000;
    private final PriorityBlockingQueue<Identification> queue;
    private final Set<Long> identIdsInQueue;
    private final CompanyStore companyStore;

    @Inject
    public IdentificationsQueue(CompanyStore companyStore) {
        this.queue = new PriorityBlockingQueue<>(DEFAULT_INITIAL_CAPACITY, new IdentificationComparator(companyStore, new CompanyComparator()));
        this.identIdsInQueue = Sets.newConcurrentHashSet();
        this.companyStore = companyStore;
    }

    public void add(Identification ident) throws IdentificationsQueueDuplicateException {
        Preconditions.checkArgument(ident.getId() != null && ident.getId() > 0, "Identification must be saved before!");
        Company company = companyStore.findByIdOrThrow(ident.getCompanyId());
        if (identIdsInQueue.add(ident.getId())) {
            queue.add(ident);
            if (Logger.isDebugEnabled()) {
                Logger.debug("Added ident to queue. {}, {}", ident, company);
            }
        } else {
            String errMsg = "Ident was queued previously.";
            Logger.error(errMsg + " '{}'.", ident);
            throw new IdentificationsQueueDuplicateException(errMsg);
        }
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Collection<Identification> getAllIdentifications() {
        return Collections.unmodifiableCollection(queue);
    }

    public Identification poll() {
        Identification ident = queue.poll();
        identIdsInQueue.remove(ident.getId());
        Preconditions.checkState(identIdsInQueue.size() == queue.size(), "Queue is not synchronized.");
        if (Logger.isDebugEnabled()) {
            Logger.debug("Poll ident from queue. {}", ident);
        }
        return ident;
    }


    static class IdentificationComparator implements java.util.Comparator<Identification> {

        private final CompanyStore companyStore;
        private final Comparator<Company> companyComparator;

        public IdentificationComparator(CompanyStore companyStore, Comparator<Company> companyComparator) {
            this.companyStore = Preconditions.checkNotNull(companyStore, "Companies repository must be provided.");
            this.companyComparator = Preconditions.checkNotNull(companyComparator, "Companies comparator must be provided.");
        }

        @Override
        public int compare(Identification left, Identification right) {
            Company companyLeft = companyStore.findByIdOrThrow(left.getCompanyId());
            Company companyRight = companyStore.findByIdOrThrow(right.getCompanyId());
            int companiesComparisonResult = companyComparator.compare(companyLeft, companyRight);
            if (companiesComparisonResult == 0) {
                return -1 * Integer.compare(left.getWaitTimeSeconds(), right.getWaitTimeSeconds());
            } else {
                return companiesComparisonResult;
            }
        }
    }

    static class CompanyComparator implements java.util.Comparator<Company> {

        @Override
        public int compare(Company left, Company right) {
            int currentSlaComparisonResult = Float.compare(left.getCurrentSlaPercentage(), right.getCurrentSlaPercentage());
            if (currentSlaComparisonResult == 0) {
                return Integer.compare(left.getSlaTimeSeconds(), right.getSlaTimeSeconds());
            } else {
                return currentSlaComparisonResult;
            }
        }
    }
}
