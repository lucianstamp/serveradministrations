package org.svadmin.app.Service;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.svadmin.app.Entity.Server;
import org.svadmin.app.Repository.ServerRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    private static final ConcurrentHashMap<Long, Session> serverSessionsMap = new ConcurrentHashMap<>();

    public void saveAndConnectToServer(Server server)  {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(server.getUsername(), server.getIp(), server.getPort());
            session.setPassword(server.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("Connected to " + server.getIp());
            serverRepository.save(server);
            serverSessionsMap.put(server.getId(), session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("invalid server");

        }
    }

    public void connectToServer(Server server) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(server.getUsername(), server.getIp(), server.getPort());
            session.setPassword(server.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            serverSessionsMap.put(server.getId(), session);
            System.out.println("Connected to " + server.getIp());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void disconnectFromServer(Long id) {
        try {
            Session session = serverSessionsMap.get(id);
            session.disconnect();
            serverSessionsMap.remove(id);
            System.out.println("Disconnected from server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendSSHcommandToServer(Long id, String command) {
        try {
            Server server = serverRepository.findById(id).get();
            Session session = serverSessionsMap.get(server.getId());

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

    public List<Server> getServers() {
        return serverRepository.findAll();
    }

    public List<Session> getServerSessions() {
        return new ArrayList<>(serverSessionsMap.values());
    }

    public List<Map<String, Object>> getServerSessionsTest() {
        List<Map<String, Object>> responseList = new ArrayList<>();


        for (Map.Entry<Long, Session> entry : serverSessionsMap.entrySet()) {
            Map<String, Object> connection = new HashMap<>();
            connection.put("id", entry.getKey());
            connection.put("host", entry.getValue().getHost());
            connection.put("user", entry.getValue().getUserName());
            responseList.add(connection);
        }

        return responseList;
    }

    public void deleteServer(Long id) {
        serverRepository.deleteById(id);
    }


}


