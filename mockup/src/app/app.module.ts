import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SignupComponent } from './signup/signup.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome'
import { faCalendarPlus, faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { ViewReservationComponent } from './view-reservation/view-reservation.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DialogComponent } from './dialog/dialog.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatButtonModule } from '@angular/material/button'
import { MatCheckboxModule } from '@angular/material/checkbox'
import { MatDividerModule } from '@angular/material/divider'
import { MatCardModule } from '@angular/material/card'
import { MatRippleModule } from '@angular/material/core'

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    DashboardComponent,
    ViewReservationComponent,
    PageNotFoundComponent,
    DialogComponent
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
    MatRippleModule
  ],
  entryComponents: [
    DialogComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 

  constructor(library: FaIconLibrary){
    library.addIcons(faCalendarPlus, faCalendarAlt)
  }

}
