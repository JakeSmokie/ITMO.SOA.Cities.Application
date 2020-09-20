import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  defaultFields,
  FilterFieldValue,
  FilterOperation,
  filterOperationLabels,
  filterOperationsForType,
  SearchCriterion,
} from 'src/app/models/filters';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

type FilterForm = { fieldIndex: number; operation: FilterOperation; value: any };

const filterValue = (filter: FilterForm) =>
  defaultFields[filter.fieldIndex].type === 'date'
    ? new Date(filter.value.year, filter.value.month - 1, filter.value.day + 1).toISOString().substring(0, 10)
    : filter.value;

const formToCriteria = (filters: FilterForm[]) =>
  filters.map(x => ({
    field: defaultFields[x.fieldIndex].field,
    operation: x.operation,
    value: filterValue(x),
  } as SearchCriterion));

@Component({
  selector: 'app-city-filter',
  templateUrl: './city-filter.component.html',
  styleUrls: ['./city-filter.component.scss'],
})
export class CityFilterComponent implements OnInit {
  filters: FilterFieldValue[] = [];

  defaultFields = defaultFields;
  filterOperationLabels = filterOperationLabels;
  filterOperationsForType = filterOperationsForType;

  form: FormArray;

  @Output() filterUpdated = new EventEmitter<{ valid: boolean, criteria: SearchCriterion[] }>();

  constructor(
    private fb: FormBuilder,
  ) {
    this.form = this.fb.array([]);
    this.form.valueChanges.subscribe(value => {
      if (!this.form.valid) {
        this.filterUpdated.emit({ valid: false, criteria: [] });
        return;
      }

      const criteria = formToCriteria(value);
      this.filterUpdated.emit({ valid: true, criteria });
    });
  }

  ngOnInit(): void {
  }

  addFilter() {
    this.form.push(
      this.fb.group({
        fieldIndex: [0],
        operation: [filterOperationsForType[defaultFields[0].type][0], Validators.required],
        value: [null, Validators.required],
      }),
    );

    this.form.markAllAsTouched();
  }

  removeFilter(i) {
    this.form.removeAt(i);
  }

  fieldSelected(filter: FormGroup) {
    const type = defaultFields[filter.value.fieldIndex].type;
    const operations = filterOperationsForType[type];

    if (!operations.includes(filter.value.operation)) {
      filter.patchValue({ operation: null });
    }

    setTimeout(() => {
      filter.setControl('value', this.fb.control(null, Validators.required));
    });
  }
}
