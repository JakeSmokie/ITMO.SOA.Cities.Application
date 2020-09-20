import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { defaultFields, FieldSort, FilterFieldType, SortDirection } from '../../models/filters';

@Component({
  selector: 'app-cities-ordering',
  templateUrl: './cities-ordering.component.html',
  styleUrls: ['./cities-ordering.component.scss'],
})
export class CitiesOrderingComponent implements OnInit {
  availableFields: FilterFieldType[] = [...defaultFields];
  selectedFields: { field: FilterFieldType, sort: SortDirection }[] = [];

  @Output() orderingUpdated = new EventEmitter<FieldSort[]>();

  constructor() {
  }

  ngOnInit(): void {
  }

  selectField(field: FilterFieldType, ascending: boolean) {
    this.selectedFields.push({ field, sort: ascending ? 'ASC' : 'DESC' });
    this.availableFields.splice(this.availableFields.findIndex(x => x === field), 1);

    this.notifyThatOrderingUpdated();
  }

  selectDirection(field: { field: FilterFieldType; sort: SortDirection }, ascending: boolean, button: HTMLButtonElement) {
    field.sort = ascending ? 'ASC' : 'DESC';
    button.blur();

    this.notifyThatOrderingUpdated();
  }

  unselectField(field: FilterFieldType) {
    this.selectedFields.splice(this.selectedFields.findIndex(x => x.field === field), 1);
    this.availableFields.push(field);

    this.notifyThatOrderingUpdated();
  }

  moveField(field: { field: FilterFieldType; sort: SortDirection }, up: boolean) {
    const index = this.selectedFields.findIndex(x => x === field);

    this.selectedFields.splice(index, 1);
    this.selectedFields.splice(index + (up ? -1 : 1), 0, field);

    this.notifyThatOrderingUpdated();
  }

  private notifyThatOrderingUpdated() {
    this.orderingUpdated.emit(this.selectedFields.map(x => ({
      field: x.field.field,
      sort: x.sort,
    })));
  }
}
