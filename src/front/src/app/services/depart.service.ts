import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const baseUrl = 'https://localhost:50101/cities/api/cities';

@Injectable({
  providedIn: 'root'
})
export class DepartService {
  constructor(private http: HttpClient) {
  }

  count(a: number, b: number, c: number): Observable<number> {
    return this.http.get<number>(`${baseUrl}/count/${a}/${b}/${c}`);
  }

  depart(a: number, b: number): Observable<unknown> {
    return this.http.post(`${baseUrl}/deport/${a}/${b}`, null, {responseType: "text"});
  }
}
