package ru.itmo.jakesmokie.soa.lab01.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.itmo.jakesmokie.soa.lab01.helpers.CitySpecificationParser;
import ru.itmo.jakesmokie.soa.lab01.models.CityDto;
import ru.itmo.jakesmokie.soa.lab01.models.input.CityInputDto;
import ru.itmo.jakesmokie.soa.lab01.models.input.GovernmentDto;
import ru.itmo.jakesmokie.soa.lab01.services.ICityService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
    private final ICityService cityService;

    public CitiesController(ICityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<?> list(Pageable pageable, @Nullable @RequestParam List<List<String>> criteria) {
        return CitySpecificationParser.parse(criteria).map(
            error -> ResponseEntity.badRequest().body(error),
            spec -> ResponseEntity.ok(cityService.list(pageable, spec))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> get(@PathVariable Long id) {
        return cityService.get(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/car-code/{carCode}/count")
    public long countByCarCode(@PathVariable @NonNull Long carCode) {
        return cityService.countByCarCode(carCode);
    }

    @GetMapping("/name/starts-with/{name}")
    public Page<CityDto> countByCarCode(Pageable pageable, @PathVariable @NonNull String name) {
        return cityService.findAllByNameStartsWith(name, pageable);
    }

    @GetMapping("/government/greater-than/{government}")
    public Page<CityDto> countByCarCode(Pageable pageable, @PathVariable @NonNull GovernmentDto government) {
        return cityService.findAllByGovernmentGreaterThan(government, pageable);
    }

    @PutMapping
    public ResponseEntity<?> create(@RequestBody @Valid CityInputDto city) {
        return cityService.create(city).map(
            error -> ResponseEntity.badRequest().body(error),
            ResponseEntity::ok
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CityInputDto city, @PathVariable Long id) {
        return cityService.update(id, city).map(
            error -> ResponseEntity.badRequest().body(error),
            ResponseEntity::ok
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePatch(@RequestBody @Valid CityInputDto city, @PathVariable Long id) {
        return cityService.update(id, city).map(
            error -> ResponseEntity.badRequest().body(error),
            ResponseEntity::ok
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return cityService.delete(id).map(
            e -> ResponseEntity.notFound().build(),
            ResponseEntity::ok
        );
    }
}
