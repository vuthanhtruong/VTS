import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NgOptimizedImage } from '@angular/common';

@Component({
  selector: 'app-update',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, NgOptimizedImage],
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {
  formData: any = {
    code: '',
    name: '',
    voucherNo: '',
    voucherDate: '',
    voucherUserId: null,
    assetFixedTypeId: null,
    assetFixedId: null,
    assetFixedDecreaseReasonId: null,
    type: 1,
    invoiceNo: '',
    invoiceDate: '',
    decisionNo: '',
    decisionDate: '',
    decRev: null,
    depAccPaid: null,
    depAccUnpaid: null,
    assetProcCost: null,
    isActive: true,
    description: '',
    createById: null,
    modifiedById: null
  };

  assets: any[] = [];
  reasons: any[] = [];
  employees: any[] = [];
  isLoadingAssets = true;
  isLoadingReasons = true;
  isLoadingEmployees = true;
  error: string | null = null;
  id: number | null = null;

  private apiUrl = 'http://localhost:8080/api/asset-fixed-decreases';
  private assetApiUrl = 'http://localhost:8080/api/asset-fixed';
  private reasonApiUrl = 'http://localhost:8080/api/asset-fixed-decrease-reasons';
  private employeeApiUrl = 'http://localhost:8080/api/employees';

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    // Get ID from route
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (!this.id) {
      this.error = 'Không tìm thấy ID bản ghi!';
      return;
    }

    // Fetch record by ID
    this.http.get<any>(`${this.apiUrl}/${this.id}`).subscribe({
      next: (data) => {
        this.formData = {
          ...data,
          voucherDate: data.voucherDate ? new Date(data.voucherDate).toISOString().split('T')[0] : '',
          invoiceDate: data.invoiceDate ? new Date(data.invoiceDate).toISOString().split('T')[0] : '',
          decisionDate: data.decisionDate ? new Date(data.decisionDate).toISOString().split('T')[0] : ''
        };
        console.log('Fetched record:', this.formData);
      },
      error: (err) => {
        this.error = 'Lỗi khi tải bản ghi: ' + (err.error?.error || err.message);
        console.error('Fetch error:', err);
      }
    });

    // Fetch dropdown data
    this.http.get<any[]>(this.assetApiUrl).subscribe({
      next: (data) => {
        this.assets = data;
        this.isLoadingAssets = false;
      },
      error: (err) => {
        this.error = 'Không thể tải danh sách tài sản: ' + (err.error?.error || err.message);
        this.isLoadingAssets = false;
      }
    });

    this.http.get<any[]>(this.reasonApiUrl).subscribe({
      next: (data) => {
        this.reasons = data;
        this.isLoadingReasons = false;
      },
      error: (err) => {
        this.error = 'Không thể tải danh sách lý do giảm: ' + (err.error?.error || err.message);
        this.isLoadingReasons = false;
      }
    });

    this.http.get<any[]>(this.employeeApiUrl).subscribe({
      next: (data) => {
        this.employees = data;
        this.isLoadingEmployees = false;
      },
      error: (err) => {
        this.error = 'Không thể tải danh sách người lập phiếu: ' + (err.error?.error || err.message);
        this.isLoadingEmployees = false;
      }
    });
  }

  onSubmit() {
    let description = this.formData.description || '';
    if (this.formData.decisionNo) {
      description += ` Số QĐ: ${this.formData.decisionNo}`;
    }
    if (this.formData.decisionDate) {
      description += ` Ngày QĐ: ${this.formData.decisionDate}`;
    }
    this.formData.description = description.trim();

    if (this.formData.voucherDate) {
      this.formData.voucherDate = new Date(this.formData.voucherDate).toISOString();
    }
    if (this.formData.invoiceDate) {
      this.formData.invoiceDate = new Date(this.formData.invoiceDate).toISOString();
    }
    if (this.formData.decisionDate) {
      this.formData.decisionDate = new Date(this.formData.decisionDate).toISOString();
    }

    this.formData.modifiedById = this.formData.voucherUserId;

    const payload = { ...this.formData };
    delete payload.decisionNo;
    delete payload.decisionDate;
    console.log('Submitting payload:', payload);

    this.http.put(`${this.apiUrl}/${this.id}`, payload).subscribe({
      next: () => {
        alert('Cập nhật thành công!');
        this.router.navigate(['/list']);
      },
      error: (err) => {
        console.error('Lỗi khi cập nhật:', err);
        const errorMessage = err.error ? (typeof err.error === 'string' ? err.error : JSON.stringify(err.error)) : err.message;
        alert('Có lỗi xảy ra: ' + errorMessage);
      }
    });
  }

  onCancel() {
    this.router.navigate(['/list']);
  }
}
