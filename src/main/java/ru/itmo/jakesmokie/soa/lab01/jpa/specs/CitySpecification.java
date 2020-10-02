package ru.itmo.jakesmokie.soa.lab01.jpa.specs;

import org.springframework.data.jpa.domain.Specification;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.City;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.Government;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CitySpecification implements Specification<City> {
    private final List<SearchCriteria> list;

    public CitySpecification(List<SearchCriteria> list) {
        this.list = list;
    }

    @Override
    public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : list) {
            final Class<?> javaType = getPath(root, criteria.getKeyPath()).getJavaType();

            if (javaType.equals(LocalDate.class)) {
                add(builder, predicates, criteria, getPath(root, criteria.getKeyPath()), LocalDate.parse(criteria.getValue()));
            } else if (javaType.equals(Government.class)) {
                add(builder, predicates, criteria, getPath(root, criteria.getKeyPath()), Government.valueOf(criteria.getValue()));
            } else {
                add(builder, predicates, criteria, getPath(root, criteria.getKeyPath()), criteria.getValue());
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private <Y extends Comparable<? super Y>> void add(CriteriaBuilder builder, List<Predicate> predicates, SearchCriteria criteria, Path<Y> path, Y value) {
        switch (criteria.getOperation()) {
            case GREATER_THAN:
                predicates.add(builder.greaterThan(path, value));
                break;
            case LESS_THAN:
                predicates.add(builder.lessThan(path, value));
                break;
            case GREATER_THAN_EQUAL:
                predicates.add(builder.greaterThanOrEqualTo(path, value));
                break;
            case LESS_THAN_EQUAL:
                predicates.add(builder.lessThanOrEqualTo(path, value));
                break;
            case NOT_EQUAL:
                predicates.add(builder.notEqual(path, value));
                break;
            case EQUAL:
                predicates.add(builder.equal(path, value));
                break;
            case MATCH:
                if (value instanceof String) {
                    predicates.add(builder.like(builder.lower((Path<String>) path), "%" + ((String) value).toLowerCase() + "%"));
                }
                break;
            case MATCH_END:
                if (value instanceof String) {
                    predicates.add(builder.like(builder.lower((Path<String>) path), ((String) value).toLowerCase() + "%"));
                }
                break;
            case MATCH_START:
                if (value instanceof String) {
                    predicates.add(builder.like(builder.lower((Path<String>) path), "%" + ((String) value).toLowerCase()));
                }
                break;
        }
    }

    private static <T> Path<T> getPath(Root<City> cityRoot, String[] key) {
        Path<Object> path = cityRoot.get(key[0]);

        for (int i = 1; i < key.length; i++) {
            path = path.get(key[i]);
        }

        return (Path<T>) path;
    }
}