import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NewTempCountryModule } from './country/country.module';
import { NewTempLocationModule } from './location/location.module';
import { NewTempDepartmentModule } from './department/department.module';
import { NewTempTaskModule } from './task/task.module';
import { NewTempEmployeeModule } from './employee/employee.module';
import { NewTempJobModule } from './job/job.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        NewTempCountryModule,
        NewTempLocationModule,
        NewTempDepartmentModule,
        NewTempTaskModule,
        NewTempEmployeeModule,
        NewTempJobModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewTempEntityModule {}
