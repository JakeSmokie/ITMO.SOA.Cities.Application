package ru.itmo.jakesmokie.soa.lab01.jpa.specs;

import lombok.*;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String[] keyPath;
    private String value;
    private SearchOperation operation;
}
