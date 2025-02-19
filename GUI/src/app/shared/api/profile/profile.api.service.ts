import {Injectable} from '@angular/core';
import {ApiService} from '../../services/api.service';

@Injectable()
export class ProfileApiService extends ApiService {
  private static mapUser(response: any) {
    return {
      id: response.id,
      name: response.name,
      phoneNumber: response.phoneNumber,
      email: response.email,
      prefCommunication: response.prefCommunication,
      cars: response.cars,
      driverRating: response.driverRating,
      passengerRating: response.passengerRating,
      roles: response.roles,
      photoUrl: response.photoUrl,
      mark: response.mark,
      points: response.points,
      drives: response.drives,
      pickUpPoint: response.pickUpPoint,
      numOfKm: response.numOfKm
    }
  }

  private static mapUsers(response: any) {
    response.forEach((user) => {
      user.km = user.drives.reduce((a, b) => a + (b.sumOfKm || 0), 0);
      user.points = user.points || 0;
      user.numberOfDrives = user.drives.length;
      let countOfPassengers = 0;
      user.drives.forEach((drive) => {
        countOfPassengers += drive.passengers.length;
      });
      user.passengers = countOfPassengers;
      return user;
    });
    return response;
  }

  private static simpleResponse(response: any) {
    return response;
  }

  getUser(id) {
    return super.get(`api/users/${id}`, ProfileApiService.mapUser);
  }

  getCurrentUser() {
    return super.get(`api/users/`, ProfileApiService.mapUser);
  }

  setUserCar(car) {
    return super.post(`api/car`, car, ProfileApiService.simpleResponse);
  }

  changeUserOptions(userOptions) {
    return super.put(`api/users`, userOptions);
  }

  deleteUserCar(id) {
    return super.delete(`api/car/${id}`, {id}); //rewrite url
  }

  getUsers() {
    return super.get(`api/users/admin`, ProfileApiService.mapUsers);
  }

  getFilteredUsers(startTime, finTime) {
  return super.get(`api/users/admin/startTime/${startTime}/finTime/${finTime}`, ProfileApiService.mapUsers);
  }

  logOut() {
    return super.post(`logout`);
  }
}
