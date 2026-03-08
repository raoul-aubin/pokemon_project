import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[pkmnBorderCard]',
  standalone: true
})
export class BorderCardDirective {

  private initialColor = '#f5f5f5';
  private defaultColor = '#009688';

  @Input('pkmnBorderCard') borderColor?: string;

  constructor(private el: ElementRef) {
    this.setBorder(this.initialColor);
    this.setHeight(180);
  }

  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.setBorder(this.borderColor || this.defaultColor);
  }

  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.setBorder(this.initialColor);
  }

  private setBorder(color: string): void {
    this.el.nativeElement.style.border = `solid 4px ${color}`;
  }

  private setHeight(height: number): void {
    this.el.nativeElement.style.height = `${height}px`;
  }
}
