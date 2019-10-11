export class Card {
	constructor(public title: string,
				public icon: string,
				public reference?: string) {}

		public getRef() {
			return this.reference.length > 0 ? this.reference : 'pagenotfound';
	}
}
