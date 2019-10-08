import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SignupComponent, SignupDialogComponent } from './signup/signup.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faCalendarPlus, faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { ViewReservationComponent, ReservationDialogComponent } from './view-reservation/view-reservation.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { MatRippleModule, MatNativeDateModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { MakeReservationComponent } from './make-reservation/make-reservation.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReserveListDirective } from './reserve-list.directive';
import { OpenRoomListComponent } from './open-room-list/open-room-list.component';
import { MatListModule } from '@angular/material/list';
import { RecaptchaModule } from 'ng-recaptcha';
import { ReactiveFormsModule, FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    DashboardComponent,
    ViewReservationComponent,
    PageNotFoundComponent,
    SignupDialogComponent,
    ReservationDialogComponent,
    ForgotPasswordComponent,
    ChangePasswordComponent,
    MakeReservationComponent,
    ReserveListDirective,
    OpenRoomListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FlexLayoutModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDividerModule,
    MatCardModule,
    MatRippleModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    MatToolbarModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatListModule,
    RecaptchaModule,
    ReactiveFormsModule,
    FormsModule
  ],
  entryComponents: [
    SignupDialogComponent,
    ReservationDialogComponent,
    OpenRoomListComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(library: FaIconLibrary) {
    library.addIcons(faCalendarPlus, faCalendarAlt);
  }

}
