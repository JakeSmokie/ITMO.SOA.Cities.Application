package ru.itmo.jakesmokie.soa.lab01.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.jakesmokie.soa.lab01.jpa.specs.SearchCriteria;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaList {
    private Set<SearchCriteria> criteria;
}
