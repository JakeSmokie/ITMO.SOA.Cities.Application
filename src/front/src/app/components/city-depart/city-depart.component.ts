import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {DepartService} from "../../services/depart.service";
import {catchError} from "rxjs/operators";
import {of} from "rxjs";

@Component({
  selector: 'app-city-depart',
  templateUrl: './city-depart.component.html',
  styleUrls: ['./city-depart.component.scss']
})
export class CityDepartComponent implements OnInit {
  firstId: string;
  secondId: string;
  thirdId: string;

  population: number;

  departFirstId: string;
  departSecondId: string;
  departSuccess: boolean;

  @Output() departed = new EventEmitter();

  constructor(
    private departService: DepartService
  ) {
  }

  ngOnInit(): void {
  }

  countDisabled() {
    return !+this.firstId || !+this.secondId || !+this.thirdId
      || +this.firstId < 0 || +this.secondId < 0 || +this.thirdId < 0;
  }

  count() {
    this.departService.count(+this.firstId, +this.secondId, +this.thirdId)
      .pipe(catchError(() => of(-1)))
      .subscribe(value => {
        this.population = value;
      });
  }

  deportDisabled() {
    return !+this.departFirstId || !+this.departSecondId
      || +this.departFirstId < 0 || +this.departSecondId < 0;
  }

  deport() {
    this.departService.depart(+this.departFirstId, +this.departSecondId)
      .subscribe(
        () => {
          this.departSuccess = true;
          this.departed.emit();
        },
        () => this.departSuccess = false
      );
  }
}
