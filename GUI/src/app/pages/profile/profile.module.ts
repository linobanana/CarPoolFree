import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileComponent} from './profile.component';
import {UserModule} from '../../shared/components/user/user.module';
import {MatButtonModule, MatIconModule} from '@angular/material';
import {CarModule} from '../../shared/components/car/car.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CarFormModule} from '../../shared/components/car-form/car-form.module';
import {UserService} from '../../shared/components/user/user.service';
import {HeaderModule} from '../../shared/components/header/header.module';
import {ProfileMenuModule} from '../../shared/components/profile-menu/profile-menu.module';
import {FooterModule} from '../../shared/components/footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    UserModule,
    MatButtonModule,
    MatIconModule,
    CarModule,
    ReactiveFormsModule,
    FormsModule,
    CarFormModule,
    HeaderModule,
    ProfileMenuModule,
    FooterModule
  ],
  providers: [
    UserService
  ],
  declarations: [ProfileComponent],
  exports: [ProfileComponent]
})
export class ProfileModule {}
