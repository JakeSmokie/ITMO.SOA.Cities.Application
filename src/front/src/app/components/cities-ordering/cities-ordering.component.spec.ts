import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitiesOrderingComponent } from './cities-ordering.component';

describe('CitiesOrderingComponent', () => {
  let component: CitiesOrderingComponent;
  let fixture: ComponentFixture<CitiesOrderingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitiesOrderingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CitiesOrderingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
