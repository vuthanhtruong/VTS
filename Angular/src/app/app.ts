// app/app.ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],        // 👈 thêm dòng này
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent {}
