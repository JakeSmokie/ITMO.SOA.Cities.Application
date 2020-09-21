import { Component, HostBinding, OnInit, TemplateRef } from '@angular/core';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-toasts',
  templateUrl: './toasts.component.html',
  styleUrls: ['./toasts.component.scss'],
  // tslint:disable-next-line:no-host-metadata-property
  // host: {'[class.ngb-toasts]': 'true'}
})
export class ToastsComponent implements OnInit {
  @HostBinding('class.ngb-toasts') binding = true;

  constructor(public toastService: ToastService) {
  }

  ngOnInit(): void {
  }

  isTemplate(toast) { return toast.textOrTpl instanceof TemplateRef; }
}
