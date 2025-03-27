import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  servers : any = [];
  connections: any = [];
  newServer = {ip:'',username:'',password:'',port:''};

  constructor(private http:HttpClient) {}

 ngOnInit() {
    this.http.get('http://localhost:8080/api/server/getAllServers',{headers: {'Content-Type': 'application/json'}}).subscribe(
      response => {
        console.log(response);
        this.servers = response;
      }
    )
   this.http.get('http://localhost:8080/api/server/getAllServersSessions', {headers: {'Content-Type': 'application/json'}}).subscribe(
     response => {
       console.log(response);
       this.connections = response;

     }
   )
 }
  onSubmitAddServer() {
    console.log("Form Data before submission:", this.newServer); // Debugging

    this.http.post('http://localhost:8080/api/server', this.newServer,
      { headers: { 'Content-Type': 'application/json' } }).subscribe(
      response => {
        console.log("Server added:", response);
        this.ngOnInit(); // Refresh list
      }, error => {
        console.error("Error adding server:", error);  // Log errors
      }
    );
  }

  onSubmitConnect(serverId: number) {
    this.http.post(`http://localhost:8080/api/server/connect/${serverId}`, {headers: { 'Content-Type': 'application/json' }})
 }
 onSubmitDelete(serverId: number) {
    this.http.delete(`http://localhost:8080/api/server/delete/${serverId}`).subscribe(
      response => {
        console.log(response);
        this.ngOnInit()
      }
    )
 }
 onSubmitDisconnect(serverId: Number) {
    this.http.put(`http://localhost:8080/api/server/disconnect/${serverId}`,{}).subscribe(
      response => {
      console.log(response);
      this.ngOnInit()
      }
    )
 }

}
