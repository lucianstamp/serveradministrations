package org.svadmin.app.Controller;

import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svadmin.app.Entity.Server;
import org.svadmin.app.Repository.ServerRepository;
import org.svadmin.app.Service.ServerService;

@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    ServerService serverService;


    @PostMapping
    public ResponseEntity<?> createConnection(@RequestBody Server server, @RequestParam String user, @RequestParam String pass) {
        serverService.saveAndConnectToServer(server,user,pass);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connect")
    public ResponseEntity<?> connectToServer(@RequestParam Long id, @RequestParam String user, @RequestParam String pass) throws JSchException {
        serverService.connectToServer(id,user,pass);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/disconnect")
    public ResponseEntity<?> disconnectFromServer(@RequestParam Long id){
        serverService.disconnectFromServer(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendssh")
    public ResponseEntity<?> sendSSHcommandToServer(@RequestParam Long id, @RequestParam String command){
        serverService.sendSSHcommandToServer(id,command);
        return ResponseEntity.ok().build();


    }
}
