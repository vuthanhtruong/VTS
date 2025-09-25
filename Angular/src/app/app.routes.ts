import { Routes } from '@angular/router';
import { ListComponent } from './list.component';
import { CreateComponent } from './create.component';
import { UpdateComponent } from './update.component';

export const routes: Routes = [
  { path: 'list', component: ListComponent },
  { path: 'create', component: CreateComponent },
  { path: 'edit/:id', component: UpdateComponent },
  { path: 'view/:id', component: UpdateComponent }, // Assuming view uses UpdateComponent; adjust if separate
  { path: '', redirectTo: '/list', pathMatch: 'full' },
  { path: '**', redirectTo: '/list' }
];
