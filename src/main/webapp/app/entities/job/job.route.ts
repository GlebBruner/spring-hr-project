import { Routes } from '@angular/router';

import { JobComponent } from './job.component';
import { JobDetailComponent } from './job-detail.component';
import { JobPopupComponent } from './job-dialog.component';
import { JobDeletePopupComponent } from './job-delete-dialog.component';

export const jobRoute: Routes = [
    {
        path: 'job',
        component: JobComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        }
    }, {
        path: 'job/:id',
        component: JobDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        }
    }
];

export const jobPopupRoute: Routes = [
    {
        path: 'job-new',
        component: JobPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        outlet: 'popup'
    },
    {
        path: 'job/:id/edit',
        component: JobPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        outlet: 'popup'
    },
    {
        path: 'job/:id/delete',
        component: JobDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Jobs'
        },
        outlet: 'popup'
    }
];
