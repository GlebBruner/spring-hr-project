import { Routes } from '@angular/router';

import { DepartmentComponent } from './department.component';
import { DepartmentDetailComponent } from './department-detail.component';
import { DepartmentPopupComponent } from './department-dialog.component';
import { DepartmentDeletePopupComponent } from './department-delete-dialog.component';

export const departmentRoute: Routes = [
    {
        path: 'department',
        component: DepartmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departments'
        }
    }, {
        path: 'department/:id',
        component: DepartmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departments'
        }
    }
];

export const departmentPopupRoute: Routes = [
    {
        path: 'department-new',
        component: DepartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departments'
        },
        outlet: 'popup'
    },
    {
        path: 'department/:id/edit',
        component: DepartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departments'
        },
        outlet: 'popup'
    },
    {
        path: 'department/:id/delete',
        component: DepartmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departments'
        },
        outlet: 'popup'
    }
];
