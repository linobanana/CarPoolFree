import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TripComponent} from './trip.component';
import {MatButtonModule, MatExpansionModule, MatIconModule, MatListModule} from '@angular/material';
import {RatingModule} from "../rating/rating.module";

@NgModule({
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    RatingModule,
    MatExpansionModule,
  ],
  providers: [],
  declarations: [TripComponent],
  exports: [TripComponent]
})
export class TripModule {}
