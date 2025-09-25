import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink, FormsModule],
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
  assetFixedDecreases: any[] = [];
  loading = true;
  error: string | null = null;
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;
  totalPages = 0;

  private apiUrl = 'http://localhost:8080/api/asset-fixed-decreases/paginated';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.error = null;
    console.log(`Fetching page ${this.currentPage}, size ${this.pageSize}`);
    this.http.get<any>(`${this.apiUrl}?page=${this.currentPage}&size=${this.pageSize}`).subscribe({
      next: (response) => {
        console.log('API response:', response);
        this.assetFixedDecreases = response.content || [];
        this.currentPage = response.currentPage || 0;
        this.pageSize = response.pageSize || 10;
        this.totalItems = response.totalItems || 0;
        this.totalPages = response.totalPages || 0;
        if (this.totalItems === 0) {
          this.totalPages = 0; // Ensure no pages if no records
        }
        console.log(`Processed: totalItems=${this.totalItems}, totalPages=${this.totalPages}`);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Lỗi khi tải dữ liệu: ' + (err.error?.error || err.message);
        this.loading = false;
        console.error('Fetch error:', err);
      }
    });
  }

  reload(): void {
    this.loadData();
  }

  changePageSize(size: number): void {
    if (size > 0 && size <= 100) {
      this.pageSize = size;
      this.currentPage = 0; // Reset to first page
      this.loadData();
    }
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadData();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadData();
    }
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadData();
    }
  }

  deleteAssetFixedDecrease(id: number): void {
    if (confirm('Bạn có chắc chắn muốn xóa bản ghi này?')) {
      this.http.delete(`${this.apiUrl}/${id}`).subscribe({
        next: () => {
          this.loadData(); // Refresh data after deletion
        },
        error: (err) => {
          this.error = 'Lỗi khi xóa bản ghi: ' + (err.error?.error || err.message);
        }
      });
    }
  }

  get pageNumbers(): number[] {
    console.log(`Generating page numbers: totalPages=${this.totalPages}`);
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  debugState(): void {
    console.log({
      totalItems: this.totalItems,
      totalPages: this.totalPages,
      pageSize: this.pageSize,
      currentPage: this.currentPage,
      assetFixedDecreases: this.assetFixedDecreases
    });
  }
}
