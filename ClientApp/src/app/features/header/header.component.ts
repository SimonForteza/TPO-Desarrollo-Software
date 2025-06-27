import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NotificationsComponent } from '../notifications/notifications.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, NotificationsComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  
  constructor(private router: Router) {}

  navigateToCreateUser(): void {
    this.router.navigate(['/create-user']);
  }
}