export class card{
	constructor(
		public title: string,
		public icon: string,
		public reference?: string){}

		/**
		 * getRef
		 */
		public getRef() {
			return this.reference.length > 0 ? this.reference : "pagenotfound";
		}
		
	
}