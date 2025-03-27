package org.svadmin.app.Controller;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.svadmin.app.Entity.Server;
import org.svadmin.app.Repository.ServerRepository;
import org.svadmin.app.Service.ServerService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    ServerService serverService;


    @PostMapping
    public ResponseEntity<?> createConnection(@RequestBody Server server) {
        serverService.saveAndConnectToServer(server);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connect")
    public ResponseEntity<?> connectToServer(@RequestBody Server server) throws JSchException {
        serverService.connectToServer(server);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/disconnect/{id}")
    public ResponseEntity<?> disconnectFromServer(@PathVariable Long id) {
        serverService.disconnectFromServer(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendssh")
    public ResponseEntity<?> sendSSHcommandToServer(@RequestParam Long id, @RequestParam String command) {
        serverService.sendSSHcommandToServer(id, command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllServers")
    public List<Server> getAllServers() {
        return serverService.getServers();
    }

    @GetMapping("/getAllServersSessions")
    public List<Map<String, Object>> getAllServersSessions() {
        return serverService.getServerSessionsTest();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteServer(@PathVariable Long id) {
        serverService.deleteServer(id);
        return ResponseEntity.noContent().build();
    }

}
