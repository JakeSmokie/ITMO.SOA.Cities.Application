package ru.itmo.jakesmokie.soa.lab01.helpers;

import lombok.val;
import ru.itmo.jakesmokie.soa.lab01.jpa.specs.CitySpecification;
import ru.itmo.jakesmokie.soa.lab01.jpa.specs.SearchCriteria;
import ru.itmo.jakesmokie.soa.lab01.jpa.specs.SearchOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CitySpecificationParser {
    public static Either<String, CitySpecification> parse(List<List<String>> criteria) {
        if (criteria == null) {
            criteria = new ArrayList<>();
        }

        if (criteria.stream().allMatch(x -> x.size() == 1) && criteria.size() == 3) {
            val newCriteria = new ArrayList<List<String>>();

            newCriteria.add(new ArrayList<>());
            newCriteria.get(0).add(criteria.get(0).get(0));
            newCriteria.get(0).add(criteria.get(1).get(0));
            newCriteria.get(0).add(criteria.get(2).get(0));

            criteria = newCriteria;
        }

        if (criteria.stream().anyMatch(x -> x.size() < 3)) {
            return Either.left("Some criterion has not enough parameters");
        }

        try {
            val searchCriteria = criteria.stream()
                .map(x ->
                    SearchCriteria.builder()
                        .keyPath(x.get(0).split("\\."))
                        .value(x.get(1))
                        .operation(SearchOperation.valueOf(x.get(2)))
                        .build()
                )
                .collect(Collectors.toList());

            return Either.right(new CitySpecification(searchCriteria));
        } catch (Exception e) {
            return Either.left(e.getMessage());
        }
    }
}
