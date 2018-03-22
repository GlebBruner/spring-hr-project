import { Routes } from '@angular/router';

import { CountryComponent } from './country.component';
import { CountryDetailComponent } from './country-detail.component';
import { CountryPopupComponent } from './country-dialog.component';
import { CountryDeletePopupComponent } from './country-delete-dialog.component';

export const countryRoute: Routes = [
    {
        path: 'country',
        component: CountryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        }
    }, {
        path: 'country/:id',
        component: CountryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        }
    }
];

export const countryPopupRoute: Routes = [
    {
        path: 'country-new',
        component: CountryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        outlet: 'popup'
    },
    {
        path: 'country/:id/edit',
        component: CountryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        outlet: 'popup'
    },
    {
        path: 'country/:id/delete',
        component: CountryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Countries'
        },
        outlet: 'popup'
    }
];
