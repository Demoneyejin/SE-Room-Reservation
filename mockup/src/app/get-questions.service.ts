import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { SecQuestions } from './signup/SecQuestions';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GetQuestionsService {

  private url = '/assets/data/questions.json';

  constructor(private http: HttpClient) { }

  getQuestions(): Observable<SecQuestions[]>{
    return this.http.get<SecQuestions[]>(this.url)
                  .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(error.message || 'Server Error');
  }
}
