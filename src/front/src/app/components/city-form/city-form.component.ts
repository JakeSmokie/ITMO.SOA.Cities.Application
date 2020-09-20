import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { City } from '../../models/city';
import { CityService } from '../../services/city.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { governments } from '../../models/government';
import { CityInput } from '../../models/city-input';
import { mergeMap } from 'rxjs/operators';

@Component({
  selector: 'app-city-form',
  templateUrl: './city-form.component.html',
  styleUrls: ['./city-form.component.scss'],
})
export class CityFormComponent implements OnInit {
  governments = governments;

  isNew: boolean;
  shown = false;

  city: City;
  form: FormGroup;

  loading = false;

  @Output() citySaved = new EventEmitter<{ city: City, isNew: boolean }>();

  constructor(
    private cityService: CityService,
    private fb: FormBuilder,
  ) {
  }

  private static defaultCity() {
    return {
      id: 0,
      agglomeration: undefined,
      area: undefined,
      carCode: undefined,
      creationDate: undefined,
      government: undefined,
      metersAboveSeaLevel: undefined,
      name: undefined,
      population: undefined,
      coordinates: {
        x: undefined,
        y: undefined,
      },
      governor: {
        birthday: undefined,
        height: undefined,
      },
    } as City;
  }

  ngOnInit(): void {
  }

  open(city: City) {
    this.isNew = city === null;
    this.shown = true;

    if (!this.isNew) {
      this.city = city;
    } else {
      this.city = CityFormComponent.defaultCity();
    }

    this.setDetails(this.city);
  }

  setDetails(city: City) {
    const { agglomeration, area, carCode, government, metersAboveSeaLevel, name, population, coordinates: { x, y }, governor: { height, birthday } } = city;

    const birthDayStruct = {
      year: birthday && birthday.getFullYear(),
      month: birthday && birthday.getMonth() + 1,
      day: birthday && birthday.getDate(),
    } as NgbDateStruct;

    if (!this.form) {
      this.form = this.fb.group({
        name: [name, [Validators.required, Validators.pattern(/\S+/)]],
        area: [area, [Validators.required, Validators.min(0)]],
        population: [population, [Validators.required, Validators.min(0)]],
        metersAboveSeaLevel: [metersAboveSeaLevel],
        carCode: [carCode, [Validators.required, Validators.min(0), Validators.max(1000)]],
        agglomeration: [agglomeration],
        government: [government, [Validators.required, Validators.pattern(/\S+/)]],
        x: [x, [Validators.max(273)]],
        y: [y, Validators.required],
        height: [height, Validators.min(0)],
        birthday: [birthDayStruct],
      });
    } else {
      this.form.patchValue({
        name,
        area,
        population,
        metersAboveSeaLevel,
        carCode,
        agglomeration,
        government,
        x,
        y,
        height,
        birthday: birthDayStruct,
      });
    }

    this.form.markAllAsTouched();
  }

  save() {
    this.form.markAllAsTouched();

    if (!this.form.valid) {
      return;
    }

    this.loading = true;

    this.cityService.save(this.formToCityInput()).pipe(
      // mergeMap(id => this.cityService.findById(id || this.city.id)),
    ).subscribe(city => {
      this.citySaved.emit({ city: null, isNew: this.isNew });

      this.loading = false;
      this.shown = false;
    });
  }

  private formToCityInput(): CityInput {
    const { name, area, population, metersAboveSeaLevel, carCode, agglomeration, government, x, y, height, birthday } = this.form.value;

    const birthDayDate = birthday && new Date(
      (birthday as NgbDateStruct).year,
      (birthday as NgbDateStruct).month - 1,
      (birthday as NgbDateStruct).day + 1,
    ) || null;

    return {
      id: this.city.id,
      name,
      area,
      population,
      metersAboveSeaLevel,
      carCode,
      agglomeration,
      government,
      coordinates: {
        x: x || null,
        y,
      },
      governor: { height, birthday: birthDayDate },
    };
  }
}
