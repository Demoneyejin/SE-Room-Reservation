import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

	constructor() {}

	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

		const auth = sessionStorage.getItem('sessionkey');

		if (auth) {
			request = request.clone({
				setHeaders: {
					Authorization: `Basic ${auth}`
				}
			});
		}

		return next.handle(request);
	}

}