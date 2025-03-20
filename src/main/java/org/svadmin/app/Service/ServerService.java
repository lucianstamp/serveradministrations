package org.svadmin.app.Service;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svadmin.app.Entity.Server;
import org.svadmin.app.Repository.ServerRepository;

@Service
public class ServerService {

    @Autowired private ServerRepository serverRepository;

    public void saveAndConnectToServer(Server server, String user, String password) {
        try{
            JSch jsch = new JSch();
            Session session = jsch.getSession(user,server.getIp(),server.getPort());
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("Connected to "+server.getIp());
            serverRepository.save(server);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
