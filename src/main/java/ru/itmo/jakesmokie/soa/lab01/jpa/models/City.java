package ru.itmo.jakesmokie.soa.lab01.jpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "coalesce(TRIM(name), '') != ''")
public class City {
    @Id @GeneratedValue private long id;
    @NotNull private String name;
    @NotNull private java.time.LocalDate creationDate;
    @NotNull @Min(0) private Integer area;
    @NotNull @Min(0L) private Long population;
    private Integer metersAboveSeaLevel;
    @NotNull @Min(0) @Max(1000) private Long carCode;
    private Float agglomeration;

    @NotNull private Government government;
    @Embedded @NotNull private Coordinates coordinates;
    @Embedded @NotNull private Human governor;
}
