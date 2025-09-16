// app/create.component.ts
import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
    selector: 'app-create',
    standalone: true,
    imports: [CommonModule, HttpClientModule, FormsModule],
    templateUrl: './create.component.html',
    styleUrls: ['./create.component.css']
})
export class CreateComponent {
    formData: any = {
        code: '',
        name: '',
        voucherDate: '',
        voucherNo: '',
        invoiceNo: '',
        type: 1, // mặc định integer (1 = Thanh lý)
        isActive: true,
        description: '',
        assetFixed: { assetFixedId: 1 },
        assetFixedDecreaseReason: { assetFixedDecreaseReasonId: 1 },
        voucherUser: { employeeId: 1 },
        createBy: { employeeId: 1 },
        modifiedBy: { employeeId: 1 }
    };

    private apiUrl = 'http://localhost:8080/api/asset-fixed-decreases';

    constructor(private http: HttpClient, private router: Router) {}

    onSubmit() {
        this.http.post(this.apiUrl, this.formData).subscribe({
            next: () => {
                alert('Thêm mới thành công!');
                this.router.navigate(['/list']); // chuyển về trang danh sách
            },
            error: (err) => {
                console.error('Lỗi khi lưu:', err);
                alert('Có lỗi xảy ra: ' + err.message);
            }
        });
    }
}
