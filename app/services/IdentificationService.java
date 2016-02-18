package services;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import models.Company;
import models.Identification;
import utils.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.inject.Singleton;

/**
 * Created by bduisenov on 18/02/16.
 */
@Singleton
public class IdentificationService {

    /**
     * sorts ids according to idnow SLA rules
     * @param companies of ids
     * @param ids to sort
     * @return sorted {@code ImmutableList<Identification>} of passed ids
     */
    public ImmutableList<Identification> sort(Set<Company> companies, List<Identification> ids) {
        checkNotNull(companies);
        checkNotNull(ids);

        if (companies.isEmpty() || ids.isEmpty()) {
            return ImmutableList.of();
        }

        final ImmutableMap<String, Company> idToCompany = Maps.uniqueIndex(companies, Company::getId);
        Map<String, Pair<Company, List<Identification>>> pairs = new HashMap<>();

        ids.forEach(id -> {
            Pair<Company, List<Identification>> pair = pairs.computeIfAbsent(id.getCompanyId(), k -> new Pair<>(idToCompany.get(k), new ArrayList<>()));
            pair._2().add(id);
        });

        return ImmutableList.copyOf(sort(pairs.values(), new TreeMap<>()).entrySet().stream().flatMap(
                percentageSLAMap -> percentageSLAMap.getValue().entrySet().stream()
                        .flatMap(timeMultimap -> timeMultimap.getValue().values().stream())).iterator());
    }

    // expect StackOverFlow on large amount of Companies. This should be considered for demonstration only
    private Map<Float, TreeMap<Float, ListMultimap<Integer, Identification>>> sort(Iterable<Pair<Company, List<Identification>>> pairs, Map<Float, TreeMap<Float, ListMultimap<Integer, Identification>>> acc) {
        if (Iterables.isEmpty(pairs)) {
            return acc;
        } else {
            Pair<Company, List<Identification>> pair = Iterables.getFirst(pairs, null);

            Company company = pair._1();
            List<Identification> ids = pair._2();

            Map<Float, ListMultimap<Integer, Identification>> percentageSLAMap = acc
                    .computeIfAbsent(company.getCurrentSlaPercentage(), k -> new TreeMap<>(Comparator.reverseOrder()));

            ListMultimap<Integer, Identification> timeMap = percentageSLAMap
                    .computeIfAbsent(company.getSlaPercentage(), k -> MultimapBuilder.treeKeys().linkedListValues().build());

            ids.stream().forEach(el -> timeMap.put(company.getSlaTime() - el.getWaitingTime(), el));
            return sort(Iterables.skip(pairs, 1), acc);
        }
    }

}
