import { Injectable } from '@angular/core';
import { City } from '../models/city';
import { HttpClient } from '@angular/common/http';
import { Pageable } from '../models/pageable';
import { map } from 'rxjs/operators';
import { CityInput } from '../models/city-input';
import { FieldSort, SearchCriterion } from '../models/filters';

export interface CityListQuery {
  page: number;
  pageSize: number;
  criteria: SearchCriterion[];
  ordering: FieldSort[];
}

const baseUrl = 'api/cities';

const convertDatesForCity = city => ({
  ...city,
  creationDate: city.creationDate && new Date(city.creationDate),
  governor: city.governor && {
    ...city.governor,
    birthday: city.governor.birthday && new Date(city.governor.birthday),
  },
});


@Injectable({
  providedIn: 'root',
})
export class CityService {
  constructor(private http: HttpClient) {
  }

  list({ page, pageSize, criteria, ordering }: CityListQuery) {
    const params: { [param: string]: string | string[] } = {
      page: page + '',
      size: pageSize + '',
      criteria: criteria.map(x => [x.field, x.value, x.operation].map(encodeURIComponent).join(',')),
      sort: ordering.map(x => [x.field, x.sort].map(encodeURIComponent).join(',')),
    };

    return this.http.get<Pageable<City>>(`${baseUrl}`, { params }).pipe(map(pageable => ({
      ...pageable,
      content: pageable.content.map(convertDatesForCity),
    })));
  }

  findById(id) {
    return this.http.get<City>(`${baseUrl}/${encodeURIComponent(id)}`);
  }

  save(city: CityInput) {
    if (city.id === 0) {
      return this.http.post<number>(`${baseUrl}`, city);
    } else {
      return this.http.put<number>(`${baseUrl}`, city);
    }
  }

  delete({ id }) {
    return this.http.delete(`${baseUrl}/${encodeURIComponent(id)}`);
  }
}
