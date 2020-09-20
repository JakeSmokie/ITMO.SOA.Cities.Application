package ru.itmo.jakesmokie.soa.lab01.models;

public interface CityDto {
    long getId();
    String getName();
    java.time.LocalDate getCreationDate();
    Integer getArea();
    Long getPopulation();
    Integer getMetersAboveSeaLevel();
    Long getCarCode();
    Float getAgglomeration();
    String getGovernment();
    CoordinatesDto getCoordinates();
    HumanDto getGovernor();
}
