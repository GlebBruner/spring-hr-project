import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Department } from './department.model';
import { DepartmentService } from './department.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-department',
    templateUrl: './department.component.html'
})
export class DepartmentComponent implements OnInit, OnDestroy {
departments: Department[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private departmentService: DepartmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
    ) {
    }

    loadAll() {
        this.departmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.departments = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.registerChangeInDepartments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Department) {
        return item.id;
    }
    registerChangeInDepartments() {
        this.eventSubscriber = this.eventManager.subscribe('departmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
