import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loader',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="spinner-border text-primary" role="status">
      <span class="visually-hidden">Chargement en cours...</span>
    </div>
  `
})
export class LoaderComponent {}
