export interface CityInput {
  id: number;
  name: string;
  area: number;
  population: number;
  metersAboveSeaLevel: number;
  carCode: number;
  agglomeration: number;
  government: string;
  coordinates: {
    x: number;
    y: number;
  };
  governor: {
    height: number;
    birthday: Date;
  };
}
