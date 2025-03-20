package org.svadmin.app.Controller;

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
}
