package ru.itmo.jakesmokie.soa.lab01.jpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    @Column(name = "coordinates_x") @Max(273) private Double x;
    @Column(name = "coordinates_y") @NotNull private Integer y;
}
