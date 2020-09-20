package ru.itmo.jakesmokie.soa.lab01.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itmo.jakesmokie.soa.lab01.helpers.Either;
import ru.itmo.jakesmokie.soa.lab01.jpa.specs.CitySpecification;
import ru.itmo.jakesmokie.soa.lab01.models.CityDto;
import ru.itmo.jakesmokie.soa.lab01.models.input.CityInputDto;
import ru.itmo.jakesmokie.soa.lab01.models.input.GovernmentDto;

import java.util.Optional;

public interface ICityService {
    Optional<CityDto> get(Long id);

    Either<String, Long> create(CityInputDto city);

    Either<String, Object> update(CityInputDto city);

    Boolean delete(Long id);

    Page<CityDto> list(Pageable first, CitySpecification citySpecification);

    long countByCarCode(Long carCode);

    Page<CityDto> findAllByNameStartsWith(String name, Pageable pageable);

    Page<CityDto> findAllByGovernmentGreaterThan(GovernmentDto government, Pageable pageable);
}
