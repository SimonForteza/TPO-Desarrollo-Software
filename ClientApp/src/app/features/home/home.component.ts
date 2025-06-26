import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  constructor(private router: Router) { }

  navigateToCreateUser() {
    this.router.navigate(['/create-user']);
  }

  navigateToCreateSport() {
    this.router.navigate(['/create-sport']);
  }

  navigateToCreateMatch() {
    this.router.navigate(['/create-match']);
  }

}


