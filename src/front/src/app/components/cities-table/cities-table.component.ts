import { Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { City } from '../../models/city';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-cities-table',
  templateUrl: './cities-table.component.html',
  styleUrls: ['./cities-table.component.scss'],
})
export class CitiesTableComponent implements OnInit {
  @Input() cities: City[];
  @Input() collectionSize: number;

  @Output() editCityClick = new EventEmitter<City>();
  @Output() deleteCityClick = new EventEmitter<City>();

  @ViewChild('deleteModal') deleteModal: TemplateRef<any>;

  cityToDelete: City;

  constructor(
    private modalService: NgbModal,
  ) {
  }

  ngOnInit(): void {
  }

  editCity(city: City) {
    this.editCityClick.emit(city);
  }

  async deleteCity(city: City) {
    this.cityToDelete = city;

    try {
      await this.modalService.open(this.deleteModal).result;
      this.deleteCityClick.emit(city);
    } catch  {
    }
  }
}
