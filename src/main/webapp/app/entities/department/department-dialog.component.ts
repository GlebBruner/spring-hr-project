import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Department } from './department.model';
import { DepartmentPopupService } from './department-popup.service';
import { DepartmentService } from './department.service';
import { Location, LocationService } from '../location';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-department-dialog',
    templateUrl: './department-dialog.component.html'
})
export class DepartmentDialogComponent implements OnInit {

    department: Department;
    isSaving: boolean;

    locations: Location[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private departmentService: DepartmentService,
        private locationService: LocationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService
            .query({filter: 'department-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.department.location || !this.department.location.id) {
                    this.locations = res.json;
                } else {
                    this.locationService
                        .find(this.department.location.id)
                        .subscribe((subRes: Location) => {
                            this.locations = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.department.id !== undefined) {
            this.subscribeToSaveResponse(
                this.departmentService.update(this.department));
        } else {
            this.subscribeToSaveResponse(
                this.departmentService.create(this.department));
        }
    }

    private subscribeToSaveResponse(result: Observable<Department>) {
        result.subscribe((res: Department) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Department) {
        this.eventManager.broadcast({ name: 'departmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-department-popup',
    template: ''
})
export class DepartmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private departmentPopupService: DepartmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.departmentPopupService
                    .open(DepartmentDialogComponent as Component, params['id']);
            } else {
                this.departmentPopupService
                    .open(DepartmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
