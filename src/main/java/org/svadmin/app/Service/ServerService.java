package org.svadmin.app.Service;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svadmin.app.Entity.Server;
import org.svadmin.app.Repository.ServerRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    private final ConcurrentHashMap<Long, Session> activeSessions = new ConcurrentHashMap<>();


    public void saveAndConnectToServer(Server server, String user, String password) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, server.getIp(), server.getPort());
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("Connected to " + server.getIp());
            serverRepository.save(server);
            activeSessions.put(server.getId(), session);
            System.out.println(activeSessions.get(server.getId()).toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void connectToServer(Long id, String user, String password) throws JSchException {
        try {
            JSch jsch = new JSch();
            Server server = serverRepository.findById(id).orElse(null);
            Session session = jsch.getSession(user, server.getIp(), server.getPort());
            if(!activeSessions.containsKey(id)) {
                activeSessions.put(id, session);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendSSHcommandToServer(Long id, String command) {
        try {
            Server server = serverRepository.findById(id).get();
            Session session = activeSessions.get(server.getId());

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            while (!channel.isClosed()) {
                Thread.sleep(100);
            }
            String responseString = new String(responseStream.toByteArray());
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
