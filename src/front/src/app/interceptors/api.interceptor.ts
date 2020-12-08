import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class APIInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (window.location.origin === 'http://localhost:50001') {
      req = req.clone({ url: `https://localhost:50001/city/${req.url}` });
    }

    return next.handle(req);
  }
}
