import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NgOptimizedImage } from '@angular/common';

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, NgOptimizedImage],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  formData: any = {
    code: '',
    name: '',
    voucherNo: '',
    voucherDate: new Date().toISOString().split('T')[0], // Default to 2025-09-25
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

  private apiUrl = 'http://localhost:8080/api/asset-fixed-decreases';
  private assetApiUrl = 'http://localhost:8080/api/asset-fixed';
  private reasonApiUrl = 'http://localhost:8080/api/asset-fixed-decrease-reasons';
  private employeeApiUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.http.get<any[]>(this.assetApiUrl).subscribe({
      next: (data) => {
        this.assets = data;
        this.isLoadingAssets = false;
      },
      error: (err) => {
        console.error('Failed to fetch assets:', err);
        this.isLoadingAssets = false;
        alert('Không thể tải danh sách tài sản!');
      }
    });

    this.http.get<any[]>(this.reasonApiUrl).subscribe({
      next: (data) => {
        this.reasons = data;
        this.isLoadingReasons = false;
      },
      error: (err) => {
        console.error('Failed to fetch reasons:', err);
        this.isLoadingReasons = false;
        alert('Không thể tải danh sách lý do giảm!');
      }
    });

    this.http.get<any[]>(this.employeeApiUrl).subscribe({
      next: (data) => {
        this.employees = data;
        this.isLoadingEmployees = false;
      },
      error: (err) => {
        console.error('Failed to fetch employees:', err);
        this.isLoadingEmployees = false;
        alert('Không thể tải danh sách người lập phiếu!');
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

    this.formData.createById = this.formData.voucherUserId;
    this.formData.modifiedById = this.formData.voucherUserId;

    const payload = { ...this.formData };
    delete payload.decisionNo;
    delete payload.decisionDate;
    console.log('Submitting payload:', payload);

    this.http.post(this.apiUrl, payload).subscribe({
      next: () => {
        alert('Thêm mới thành công!');
        this.router.navigate(['/list']);
      },
      error: (err) => {
        console.error('Lỗi khi lưu:', err);
        const errorMessage = err.error ? (typeof err.error === 'string' ? err.error : JSON.stringify(err.error)) : err.message;
        alert('Có lỗi xảy ra: ' + errorMessage);
      }
    });
  }

  onCancel() {
    this.router.navigate(['/list']);
  }
}
