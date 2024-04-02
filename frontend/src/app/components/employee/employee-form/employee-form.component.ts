import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { OperationStatus } from '../../../constants/status.enum';
import { Employee } from '../../../model/employee.model';
import { CrudService } from '../../../services/crud.service';
import { NotificationUtil } from '../../../utils/notification.util';
import { populateFormControl } from '../../../utils/object.util';

@Component({
  selector: 'app-employee-form',
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.scss']
})
export class EmployeeFormComponent {

  formGroup!: FormGroup;
  controls: any = {
    "employeeName": new FormControl('', []),
    "address": new FormControl('', []),
    "email": new FormControl('', []),
    "age": new FormControl('', []),
    "salary": new FormControl('', []),
  };
  submitted = false;
  endPoint = "employee";
  data: any = {}

  constructor(private formBuilder: FormBuilder, private service: CrudService, private noticeUtil: NotificationUtil) { }

  ngOnInit() {
    this.createForm();
    this.data = this.service.data;
    if (this.data.id) {
      populateFormControl(this.formGroup.controls, this.data);
    }
  }

  createForm() {
    this.formGroup = this.formBuilder.group(this.controls);
  }

  submitForm() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      return;
    }
    const values: Employee = { ...this.data, ...this.formGroup.value };
    this.service.save(values, this.endPoint).subscribe(response => {
      this.formGroup.reset();
      this.submitted = false;
      this.noticeUtil.showResponseMessage(response);
    },
      (error: Error) => {
        const res = { status: OperationStatus.FAILURE, message: "Server error! Please contact with support team." };
        this.noticeUtil.showResponseMessage(res);
        console.log(this.endPoint, error);
      });
  }

}
