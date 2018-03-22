import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Job } from './job.model';
import { JobService } from './job.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-job',
    templateUrl: './job.component.html'
})
export class JobComponent implements OnInit, OnDestroy {
jobs: Job[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jobService: JobService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    loadAll() {
        this.jobService.query().subscribe(
            (res: ResponseWrapper) => {
                this.jobs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.registerChangeInJobs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Job) {
        return item.id;
    }
    registerChangeInJobs() {
        this.eventSubscriber = this.eventManager.subscribe('jobListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
