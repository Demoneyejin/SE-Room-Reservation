import { Roles } from './Roles';

export class Reservation {
	constructor(
		public date: string,
		public time: string,
		public room: string,
		public owner: string,
		public resID: string,
		public roles: Roles[]
	){}
}