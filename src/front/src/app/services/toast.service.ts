import { Injectable, TemplateRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor() { }

  toasts: any[] = [];

  show(header: string, textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ header, textOrTpl, ...options });
  }

  remove(toast) {
    this.toasts = this.toasts.filter(t => t !== toast);
  }
}
