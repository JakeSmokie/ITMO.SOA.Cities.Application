package ru.itmo.jakesmokie.soa.lab01.services;


import lombok.val;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itmo.jakesmokie.soa.lab01.helpers.Either;
import ru.itmo.jakesmokie.soa.lab01.jpa.ICityRepository;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.City;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.Coordinates;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.Government;
import ru.itmo.jakesmokie.soa.lab01.jpa.models.Human;
import ru.itmo.jakesmokie.soa.lab01.jpa.specs.CitySpecification;
import ru.itmo.jakesmokie.soa.lab01.models.CityDto;
import ru.itmo.jakesmokie.soa.lab01.models.input.CityInputDto;
import ru.itmo.jakesmokie.soa.lab01.models.input.GovernmentDto;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CityService implements ICityService {
    private final ICityRepository cityRepository;

    public CityService(ICityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Optional<CityDto> get(Long id) {
        return cityRepository.findById(id, CityDto.class);
    }

    @Override
    public Either<String, Long> create(CityInputDto city) {
        val createdCity = MapCityDto(city)
            .id(0L)
            .creationDate(LocalDate.now())
            .build();

        try {
            val savedCity = cityRepository.save(createdCity);

            return Either.right(savedCity.getId());
        } catch (Exception e) {
            return Either.left(e.getMessage());
        }
    }

    @Override
    public Either<String, Object> update(CityInputDto cityDto) {
        val optionalCity = cityRepository.findById(cityDto.getId());

        if (!optionalCity.isPresent()) {
            return Either.left("City with that id does not exist");
        }

        val city = optionalCity.get();
        val updatedCity = MapCityDto(cityDto)
            .creationDate(city.getCreationDate())
            .id(city.getId())
            .build();

        try {
            cityRepository.save(updatedCity);
            return Either.right("");
        } catch (Exception e) {
            return Either.left(e.getMessage());
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            cityRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Page<CityDto> list(Pageable pageable, CitySpecification citySpecification) {
        return cityRepository.findAll(citySpecification, CityDto.class, pageable);
    }

    @Override
    public long countByCarCode(Long carCode) {
        return cityRepository.countByCarCode(carCode);
    }

    @Override
    public Page<CityDto> findAllByNameStartsWith(String name, Pageable pageable) {
        return cityRepository.findAllByNameStartsWith(name, pageable, CityDto.class);
    }

    @Override
    public Page<CityDto> findAllByGovernmentGreaterThan(GovernmentDto government, Pageable pageable) {
        return cityRepository.findAllByGovernmentGreaterThan(Government.values()[government.ordinal()], pageable, CityDto.class);
    }

    private static City.CityBuilder MapCityDto(CityInputDto city) {
        return City.builder()
            .name(city.getName())
            .area(city.getArea())
            .population(city.getPopulation())
            .metersAboveSeaLevel(city.getMetersAboveSeaLevel())
            .carCode(city.getCarCode())
            .agglomeration(city.getAgglomeration())
            .government(Government.values()[city.getGovernment().ordinal()])
            .coordinates(
                Coordinates.builder()
                    .x(city.getCoordinates().getX())
                    .y(city.getCoordinates().getY())
                    .build()
            )
            .governor(
                Human.builder()
                    .birthday(city.getGovernor().getBirthday())
                    .height(city.getGovernor().getHeight())
                    .build()
            );
    }
}
