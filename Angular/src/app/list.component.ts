import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-list',
    standalone: true,
    imports: [CommonModule, HttpClientModule, RouterLink],
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
    assetFixedDecreases: any[] = [];   // 👈 thêm dòng này
    loading = true;
    error: string | null = null;

    private apiUrl = 'http://localhost:8080/api/asset-fixed-decreases';

    constructor(private http: HttpClient) {}

    ngOnInit(): void {
        this.loadData();
    }

    loadData(): void {
        this.loading = true;
        this.http.get<any[]>(this.apiUrl).subscribe({
            next: (data) => {
                this.assetFixedDecreases = data;
                this.loading = false;
            },
            error: (err) => {
                this.error = 'Lỗi khi tải dữ liệu: ' + err.message;
                this.loading = false;
            }
        });
    }

    reload(): void {       // 👈 thêm hàm reload để dùng trong template
        this.loadData();
    }
}
