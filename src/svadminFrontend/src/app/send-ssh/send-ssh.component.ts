import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {response} from 'express';


@Component({
  selector: 'app-send-ssh',
  standalone: false,
  templateUrl: './send-ssh.component.html',
  styleUrl: './send-ssh.component.css'
})
export class SendSSHComponent implements OnInit {

  serverId!: number
  command : string | undefined;
  responseRequest : string | undefined;

  constructor(private http: HttpClient, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.serverId = Number(this.route.snapshot.paramMap.get('id'));
    console.log(this.serverId);
  }

  sendCommand(){
    if (!this.command) {
      console.error('Command is required!');
      return;
    }

    const body = { id: this.serverId, command: this.command };

    this.http.post('http://localhost:8080/api/server/sendssh', body, {
      headers: { 'Content-Type': 'application/json' }
    }).subscribe({
      next: (response: any) => {
        console.log('Response:', response);
        this.responseRequest = response; // store the response in the response variable
      },
      error: (error) => {
        console.error('Error:', error);
        this.responseRequest = 'Error occurred: ' + error.message; // store error message if needed
      }
    });
  }

}
