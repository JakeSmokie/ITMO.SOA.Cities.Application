export interface City {
  id: number;
  name: string;
  creationDate: Date;
  area: number;
  population: number;
  metersAboveSeaLevel: number;
  carCode: number;
  agglomeration: number;
  government: string;
  coordinates: Coordinates;
  governor: Human;
}

export interface Coordinates {
  x: number;
  y: number;
}

export interface Human {
  height: number;
  birthday: Date;
}
