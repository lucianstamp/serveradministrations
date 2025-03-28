import {Component, OnInit, viewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';


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
  error: string | undefined;

  constructor(private http:HttpClient,private router:Router) { }

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

    this.http.post('http://localhost:8080/api/server', this.newServer,
      { headers: { 'Content-Type': 'application/json' } }).subscribe(
      response => {
        console.log("Server added:", response);
        this.ngOnInit();
      }, error => {
        console.error("Error adding server:", error);
        this.error = 'Invalid Server credentials'
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

 onClickSENDSSH(serverId: number) {
    this.router.navigate(['/sendssh/'+serverId]);

 }

}
