import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    NewTempSharedLibsModule,
    NewTempSharedCommonModule
} from './';

@NgModule({
    imports: [
        NewTempSharedLibsModule,
        NewTempSharedCommonModule
    ],
    declarations: [
    ],
    providers: [
        DatePipe
    ],
    entryComponents: [],
    exports: [
        NewTempSharedCommonModule,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class NewTempSharedModule {}
