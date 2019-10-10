import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appReserveList]'
})
export class ReserveListDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }

}
