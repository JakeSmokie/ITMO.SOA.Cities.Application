import { governments } from './government';

export type FilterOperation =
  | 'GREATER_THAN'
  | 'LESS_THAN'
  | 'GREATER_THAN_EQUAL'
  | 'LESS_THAN_EQUAL'
  | 'NOT_EQUAL'
  | 'EQUAL'
  | 'MATCH'
  | 'MATCH_START'
  | 'MATCH_END';

export const filterOperationLabels: { [operation: string]: string } = {
  GREATER_THAN: 'Greater than',
  LESS_THAN: 'Less than',
  GREATER_THAN_EQUAL: 'Greater than equal',
  LESS_THAN_EQUAL: 'Less than equal',
  NOT_EQUAL: 'Not equal',
  EQUAL: 'Equal',
  MATCH: 'Match',
  MATCH_START: 'Match start [%xyz]',
  MATCH_END: 'Match end [xyz%]',
};

export type FilterType =
  | 'number'
  | 'integer'
  | 'string'
  | 'select'
  | 'date';

export const filterOperationsForType: { [type: string]: FilterOperation[] } = {
  number: ['EQUAL', 'NOT_EQUAL', 'GREATER_THAN', 'LESS_THAN', 'GREATER_THAN_EQUAL', 'LESS_THAN_EQUAL'],
  integer: ['EQUAL', 'NOT_EQUAL', 'GREATER_THAN', 'LESS_THAN', 'GREATER_THAN_EQUAL', 'LESS_THAN_EQUAL'],
  string: ['EQUAL', 'NOT_EQUAL', 'MATCH', 'MATCH_START', 'MATCH_END'],
  date: ['EQUAL', 'NOT_EQUAL', 'GREATER_THAN', 'LESS_THAN', 'GREATER_THAN_EQUAL', 'LESS_THAN_EQUAL'],
  select: ['EQUAL', 'NOT_EQUAL', 'GREATER_THAN', 'LESS_THAN', 'GREATER_THAN_EQUAL', 'LESS_THAN_EQUAL'],
};

export interface FilterFieldType {
  label: string;
  field: string;
  type: FilterType;
  selectSet?: string[];
}

export const defaultFields: FilterFieldType[] = [
  { label: 'Id', field: 'id', type: 'integer' },
  { label: 'Name', field: 'name', type: 'string' },
  { label: 'Creation date', field: 'creationDate', type: 'date' },
  { label: 'Area', field: 'area', type: 'integer' },
  { label: 'Population', field: 'population', type: 'integer' },
  { label: 'Meters above sea level', field: 'metersAboveSeaLevel', type: 'integer' },
  { label: 'Car code', field: 'carCode', type: 'integer' },
  { label: 'Agglomeration', field: 'agglomeration', type: 'number' },
  { label: 'Government', field: 'government', type: 'select', selectSet: governments },
  { label: 'Coordinate: X', field: 'coordinates.x', type: 'number' },
  { label: 'Coordinate: Y', field: 'coordinates.y', type: 'integer' },
  { label: 'Governor: Height', field: 'governor.height', type: 'integer' },
  { label: 'Governor: Birthday', field: 'governor.birthday', type: 'date' },
];

export interface FilterFieldValue {
  field: FilterFieldType;
  operation: FilterOperation;
  value: any;
}

export interface SearchCriterion {
  field: string;
  operation: FilterOperation;
  value: any;
}

export type SortDirection = 'ASC' | 'DESC';

export interface FieldSort {
  field: string;
  sort: SortDirection;
}
