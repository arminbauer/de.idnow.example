package actors;

import java.util.Set;

import models.Company;
import models.Identification;
import services.CompanyService;
import services.IdentificationService;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * Actor for accumulating ids and retrieval of sorted list of ids.
 *
 * list of acc ids is immutable.
 *
 * storage of ids could be implemented in more simples way, but I wanted to
 * recall how things are done in Play/Akka, as almost forgot it :)
 *
 * Created by bduisenov on 18/02/16.
 */
public class QueueActor extends UntypedActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    /**
     * Command to accumulate Identifications in Actor
     */
    public static class Append {
        private final Identification identification;

        public Append(Identification identification) {
            this.identification = identification;
        }

        @Override
        public String toString() {
            return "Append{" +
                    "identification=" + identification +
                    '}';
        }
    }

    /**
     * Command to retrieve sorted ids
     */
    public static class Retrieve {

        @Override
        public String toString() {
            return "Retrieve{}";
        }
    }

    @Inject
    private IdentificationService identificationService;

    @Inject
    private CompanyService companyService;

    @Override
    public void onReceive(Object message) throws Exception {
        onReceive(ImmutableList.of(), message);
    }

    // simple stateful actor#receive method implementation
    private void onReceive(ImmutableList<Identification> acc, Object message) {
        if (message instanceof Append) {
            ImmutableList<Identification> xs = ImmutableList.<Identification>builder(). //
                    addAll(acc). //
                    add(((Append) message).identification).build();
            getContext().become(param -> {
                onReceive(xs, param);
            });
        } else if (message instanceof Retrieve) {
            final Set<Company> allCompanies = companyService.getAllCompanies();
            final ImmutableList<Identification> sorted = identificationService.sort(allCompanies, acc);
            getSender().tell(sorted, self());
            getContext().unbecome();
        } else {
            unhandled(message);
        }
    }

}
