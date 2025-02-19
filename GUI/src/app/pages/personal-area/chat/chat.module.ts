import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ChatComponent} from "./chat.component";
import {ReactiveFormsModule} from "@angular/forms";
import {SocketService} from "../../../shared/api/chat/socket.service";
import {MatButtonModule, MatCardModule, MatIconModule} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule
  ],
  providers: [
    SocketService
  ],
  declarations: [ChatComponent],
  exports: [ChatComponent]
})
export class ChatModule {}
