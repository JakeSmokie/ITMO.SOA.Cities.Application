import {Component, OnInit, ViewChild} from '@angular/core';
import {CityService} from '../../services/city.service';
import {City} from '../../models/city';
import {CityFormComponent} from '../city-form/city-form.component';
import {FieldSort, SearchCriterion} from '../../models/filters';

@Component({
  selector: 'app-cities',
  templateUrl: './cities.component.html',
  styleUrls: ['./cities.component.scss'],
})
export class CitiesComponent implements OnInit {
  @ViewChild('cityForm') cityForm: CityFormComponent;

  active = 1;

  cities: City[];
  collectionSize = 0;
  page = 1;
  pageSize = 10;

  filterValid = true;
  criteria: SearchCriterion[] = [];
  ordering: FieldSort[] = [{field: 'id', sort: 'DESC'}];

  constructor(
    private cityService: CityService,
  ) {
  }

  ngOnInit(): void {
    this.refreshCities();
  }

  deleteCity(city: City) {
    this.cityService.delete(city).subscribe(() => {
      // this.cities.splice(this.cities.findIndex(x => x.id === city.id), 1);
      // this.collectionSize -= 1;
      this.refreshCities();
    });
  }

  editCity(city: City) {
    this.cityForm.open(city);
  }

  refreshCities() {
    this.cityService.list({
      page: this.page - 1,
      pageSize: this.pageSize,
      criteria: this.criteria,
      ordering: this.ordering,
    }).subscribe(x => {
      this.cities = x.content;
      this.collectionSize = x.totalElements;
    });
  }

  citySaved({city, isNew}) {
    this.refreshCities();
  }

  filterUpdated($event: { valid: boolean; criteria: SearchCriterion[] }) {
    this.filterValid = $event.valid;
    this.criteria = $event.criteria;
  }

  applyFilters(button: HTMLButtonElement) {
    button.blur();
    this.refreshCities();
  }

  orderingUpdated(ordering: FieldSort[]) {
    this.ordering = ordering;
  }

  departed() {
    this.refreshCities();
  }
}
