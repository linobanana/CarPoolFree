import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Car, User} from '../../../shared/types/common';
import {OPTIONS} from '../../../shared/mocks/user.mocks';
import {clone} from 'ramda';
import {UserService} from '../../../shared/components/user/user.service';

@Component({
  selector: 'app-profile-component',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user$: Observable<User>;
  options$: BehaviorSubject<string[]> = new BehaviorSubject(null);

  constructor(private userService: UserService) {
    this.user$ = this.userService.userSubject;
  }
  ngOnInit(): void {
    setTimeout(() => {
      this.options$.next(clone(OPTIONS));
    }, 200);
  }

  onAddCar(car: Car) {
    this.userService.addCar(car);

  }

  onDeleteCar(car: Car) {
    this.userService.deleteCar(car);
  }
}
