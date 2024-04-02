import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppResponse } from 'src/app/dto/response.dto';
import { Employee } from 'src/app/model/employee.model';
import { CrudService } from 'src/app/services/crud.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss'],
})
export class EmployeeListComponent {
  displayedColumns: string[] = [
    'employeeName',
    'address',
    'email',
    'age',
    'salary',
    'actions',
  ];
  dataSource: Employee[] = [];

  constructor(private service: CrudService, private router: Router) {}

  ngOnInit(): void {
    this.service.getList('employee').subscribe((res: AppResponse) => {
      this.dataSource = res.data.content;
    });
  }

  approve(index: number) {
    let id = this.dataSource[index].id as number;
    this.service.approve(id, 'employee').subscribe(() => {
      this.dataSource[index].approved = true;
      this.dataSource = [...this.dataSource];
    });
  }
}
