import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  formData: any = {
    code: '',
    name: 'ssss',  // Set from asset
    voucherNo: '',
    voucherDate: new Date().toISOString().split('T')[0],
    voucherUserId: null,
    assetFixedTypeId: null,
    assetFixedId: null,
    assetFixedDecreaseReasonId: null,
    type: 1,
    invoiceNo: '',
    invoiceDate: '',
    decisionNo: '',
    decisionDate: '',
    decRev: null,         // Thu từ ghi giảm
    depAccPaid: null,     // Đã nộp TK TG
    depAccUnpaid: null,   // Chưa nộp TK TG
    assetProcCost: null,  // Chi phí xử lý TS
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
  isSubmitting = false;

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

  onAssetChange() {
    const selectedAsset = this.assets.find(a => a.assetFixedId === this.formData.assetFixedId);
    if (selectedAsset) {
      this.formData.name = selectedAsset.name || '';  // Set name từ asset
      this.formData.assetFixedTypeId = selectedAsset.assetFixedTypeId || null;
    } else {
      this.formData.name = '';  // Reset nếu không chọn
    }
  }

  onSubmit() {
    // Basic form validation (có thể mở rộng với ReactiveForms)
    if (!this.formData.code || !this.formData.assetFixedId || !this.formData.voucherUserId || !this.formData.assetFixedDecreaseReasonId || !this.formData.type) {
      alert('Vui lòng điền đầy đủ các trường bắt buộc (*)!');
      this.isSubmitting = false;
      return;
    }

    this.isSubmitting = true;
    let description = this.formData.description || '';
    this.formData.description = description.trim();

    // Convert dates to ISO
    const dateFields = ['voucherDate', 'invoiceDate', 'decisionDate'];
    dateFields.forEach(field => {
      if (this.formData[field]) {
        this.formData[field] = new Date(this.formData[field]).toISOString();
      } else {
        this.formData[field] = null;  // Set null nếu rỗng
      }
    });

    // Handle numeric fields (set to null if empty string, convert to Number for decimal support)
    const numericFields = ['decRev', 'depAccPaid', 'depAccUnpaid', 'assetProcCost'];
    numericFields.forEach(field => {
      const value = this.formData[field];
      if (value === '' || value == null || value === undefined) {
        this.formData[field] = null;
      } else {
        this.formData[field] = Number(value);  // Hỗ trợ decimal từ step="0.01"
      }
    });

    // Map voucherNo from code if not provided
    this.formData.voucherNo = this.formData.voucherNo || this.formData.code;

    this.formData.createById = this.formData.voucherUserId;
    this.formData.modifiedById = this.formData.voucherUserId;

    const payload = { ...this.formData };
    console.log('Submitting payload:', payload);  // Kiểm tra các trường số ở đây

    this.http.post(this.apiUrl, payload).subscribe({
      next: (response) => {
        console.log('Success response:', response);  // Log để check
        alert('Thêm mới thành công!');
        this.router.navigate(['/list']);
      },
      error: (err) => {
        console.error('Lỗi khi lưu:', err);
        const errorMessage = err.error?.errors ? err.error.errors.join(', ') : (err.error?.error || err.message);
        alert('Có lỗi xảy ra: ' + errorMessage);
        this.isSubmitting = false;
      },
      complete: () => {
        this.isSubmitting = false;  // Reset loading
      }
    });
  }

  onCancel() {
    this.router.navigate(['/list']);
  }
}
