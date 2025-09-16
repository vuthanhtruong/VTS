// app/app.routes.ts
import { Routes } from '@angular/router';
import { ListComponent } from './list.component';
import { CreateComponent } from './create.component';

export const routes: Routes = [
    { path: 'list', component: ListComponent },
    { path: 'create', component: CreateComponent },
    { path: '', redirectTo: '/list', pathMatch: 'full' },
    { path: '**', redirectTo: '/list' }
];
