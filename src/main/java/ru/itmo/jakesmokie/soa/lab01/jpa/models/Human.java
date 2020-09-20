package ru.itmo.jakesmokie.soa.lab01.jpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Human {
    @Column(name = "governor_height") @Min(0L) private Long height;
    @Column(name = "governor_birthday") private LocalDate birthday;
}
