import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Country } from './country.model';
import { CountryService } from './country.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-country',
    templateUrl: './country.component.html'
})
export class CountryComponent implements OnInit, OnDestroy {
countries: Country[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private countryService: CountryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    loadAll() {
        this.countryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.countries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.registerChangeInCountries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Country) {
        return item.id;
    }
    registerChangeInCountries() {
        this.eventSubscriber = this.eventManager.subscribe('countryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
