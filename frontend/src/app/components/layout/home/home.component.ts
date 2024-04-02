import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  selectedFile: File | null = null;

  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout();
  }

}
