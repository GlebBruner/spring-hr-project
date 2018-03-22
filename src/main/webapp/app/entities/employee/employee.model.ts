import { BaseEntity } from './../../shared';

export class Employee implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public hireDate?: any,
        public salary?: number,
        public department?: BaseEntity,
        public jobs?: BaseEntity[],
        public manager?: BaseEntity,
    ) {
    }
}
