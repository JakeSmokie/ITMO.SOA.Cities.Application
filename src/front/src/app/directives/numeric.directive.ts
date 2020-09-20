import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appNumeric]',
})
export class NumericDirective {
  constructor(private el: ElementRef) {
  }

  @HostListener('keydown', ['$event'])
  onKeyDown(event: KeyboardEvent) {
    if ('.,'.split('').includes(event.key)) {
      event.preventDefault();
    }
  }

  @HostListener('paste', ['$event'])
  onPaste(event: ClipboardEvent) {
    const oldValue = this.el.nativeElement.value;

    for (let i = 0; i < event.clipboardData.items.length; i++) {
      const item = event.clipboardData.items[i];

      if (item.kind !== 'string' || item.type !== 'text/plain') {
        continue;
      }

      item.getAsString(data => {
        if (data.includes('.') || data.includes(',')) {
          this.el.nativeElement.value = oldValue;
        }
      });
    }
  }
}
