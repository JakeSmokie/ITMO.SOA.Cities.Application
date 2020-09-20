import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class APIInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (window.location.origin === 'http://localhost:4200') {
      req = req.clone({ url: `http://localhost:8080/${req.url}` });
    }

    return next.handle(req);
  }
}
