import { Component } from '@angular/core';

@Component({
  selector: 'app-users-icon',
  standalone: true,
  template: `
    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
      <path d="M17 21V19C17 17.9391 16.5786 16.9217 15.8284 16.1716C15.0783 15.4214 14.0609 15 13 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21" stroke="currentColor" stroke-width="2" stroke-linejoin="round"/>
      <circle cx="9" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
      <path d="M23 21V19C23 18.1645 22.7155 17.3541 22.2094 16.6977C21.7033 16.0414 20.9999 15.5759 20.2 15.3726" stroke="currentColor" stroke-width="2" stroke-linejoin="round"/>
      <path d="M16 3.13C16.8003 3.33246 17.5037 3.79799 18.0098 4.45435C18.5159 5.11071 18.8004 5.92111 18.8004 6.75665C18.8004 7.59219 18.5159 8.40259 18.0098 9.05895C17.5037 9.71531 16.8003 10.1808 16 10.3833" stroke="currentColor" stroke-width="2" stroke-linejoin="round"/>
    </svg>
  `
})
export class UsersIconComponent {}