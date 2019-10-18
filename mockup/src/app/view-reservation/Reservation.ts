export class Reservation{
	constructor(
		public date: string,
		public time: string,
		public room: string,
		public owner: string,
		public id: number
	){}
}