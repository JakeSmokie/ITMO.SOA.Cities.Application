package ru.itmo.jakesmokie.soa.lab01.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.City;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.Government;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.Optional;

public interface ICityRepository extends PagingAndSortingRepository<City, Long>, JpaSpecificationExecutorWithProjection<City> {
    <T> Optional<T> findById(Long id, Class<T> type);

    long countByCarCode(Long carCode);

    <T> Page<T> findAllByNameStartsWith(String name, Pageable pageable, Class<T> type);

    <T> Page<T> findAllByGovernmentGreaterThan(Government government, Pageable pageable, Class<T> type);
}
