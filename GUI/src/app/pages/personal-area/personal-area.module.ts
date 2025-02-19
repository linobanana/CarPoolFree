import {NgModule} from '@angular/core';
import {PersonalAreaComponent} from './personal-area.component';
import {MyBookingsModule} from './my-bookings/my-bookings.module';
import {MyProposedModule} from './my-proposed/my-proposed.module';
import {ProfileModule} from './profile/profile.module';
import {ProfileMenuModule} from '../../shared/components/profile-menu/profile-menu.module';
import {RouterModule, Routes} from "@angular/router";
import {MyBookingsRoutes} from "./my-bookings/my-bookings.routes";
import {MyProposedRoutes} from "./my-proposed/my-proposed.routes";
import {ProfileRoutes} from "./profile/profile.routes";
import {ChatRoutes} from "./chat/chat.routes";
import {ChatModule} from "./chat/chat.module";
import {AdminRoutes} from './admin-tab/admin.routes';
import {AdminModule} from './admin-tab/admin.module';
import {DetailsOfRouteRoutes} from "./details-of-route/details-of-route.routes";
import {DetailsOfRouteModule} from "./details-of-route/details-of-route.module";

const personalAreaRotes: Routes = [
  {
    path: 'personalarea',
    component: PersonalAreaComponent,
    children: [
      {
        path: '',
        children: [
          ...MyBookingsRoutes,
          ...MyProposedRoutes,
          ...ProfileRoutes,
          ...AdminRoutes,
          ...ChatRoutes,
          ...DetailsOfRouteRoutes
        ]
      }
    ]
  }
];
@NgModule({
  imports: [
    MyBookingsModule,
    MyProposedModule,
    ProfileModule,
    ProfileMenuModule,
    AdminModule,
    ChatModule,
    RouterModule.forChild(personalAreaRotes),
    DetailsOfRouteModule
  ],
  declarations: [
    PersonalAreaComponent,
  ],
  exports: [
    PersonalAreaComponent,
    RouterModule
  ]
})

export class PersonalAreaModule {}
