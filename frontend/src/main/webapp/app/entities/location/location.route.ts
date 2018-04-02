import { Routes } from '@angular/router';

import { LocationComponent } from './location.component';
import { LocationDetailComponent } from './location-detail.component';
import { LocationPopupComponent } from './location-dialog.component';
import { LocationDeletePopupComponent } from './location-delete-dialog.component';

export const locationRoute: Routes = [
    {
        path: 'location',
        component: LocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        }
    }, {
        path: 'location/:id',
        component: LocationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        }
    }
];

export const locationPopupRoute: Routes = [
    {
        path: 'location-new',
        component: LocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        outlet: 'popup'
    },
    {
        path: 'location/:id/edit',
        component: LocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        outlet: 'popup'
    },
    {
        path: 'location/:id/delete',
        component: LocationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        outlet: 'popup'
    }
];
