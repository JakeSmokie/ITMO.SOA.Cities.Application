import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CityFormComponent } from './components/city-form/city-form.component';
import { CitiesComponent } from './components/cities/cities.component';
import { allIcons, NgxBootstrapIconsModule } from 'ngx-bootstrap-icons';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';
import { FormGroupComponent } from './components/form-group/form-group.component';
import { NumericDirective } from './directives/numeric.directive';
import { CitiesTableComponent } from './components/cities-table/cities-table.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { APIInterceptor } from './interceptors/api.interceptor';
import { CityService } from './services/city.service';
import { CityFilterComponent } from './components/city-filter/city-filter.component';
import { CitiesOrderingComponent } from './components/cities-ordering/cities-ordering.component';
import { ToastsComponent } from './components/toasts/toasts.component';
import { HttpErrorInterceptor } from './interceptors/http-error.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    CityFormComponent,
    CitiesComponent,
    FormGroupComponent,
    NumericDirective,
    CitiesTableComponent,
    CityFilterComponent,
    CitiesOrderingComponent,
    ToastsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    NgxBootstrapIconsModule.pick(allIcons),
    ReactiveFormsModule,
    NgBootstrapFormValidationModule.forRoot(),
    NgBootstrapFormValidationModule,
    FormsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: APIInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },
    CityService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
