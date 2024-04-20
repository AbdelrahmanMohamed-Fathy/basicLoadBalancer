package com.cu.loadbalancer.services;

import com.cu.loadbalancer.models.serverInfo;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
public class loadBalancerService {
    private List<Socket> serverList = new ArrayList<>();
    private int Next = 0;

    private void incrementNext() {
        Next = (Next+1)%(serverList.size());
    }

    public boolean registerServer(serverInfo newServerInfo) {
        try {
            // Create a new socket connection with the server
            Socket serverSocket = new Socket(newServerInfo.getIp(), newServerInfo.getPort());
            serverList.add(serverSocket);
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean assignTask(String task) {
        if (serverList.isEmpty()) {
            System.out.println("Empty serverList.\n");
            return false;
        }
        try {
            Socket assignee = serverList.get(Next);
            incrementNext();
            System.out.println("Task assigned to server: " + assignee.getInetAddress() + ":" + assignee.getPort());
            PrintWriter out = new PrintWriter(assignee.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(assignee.getInputStream()));
            out.println(task);
            String result = in.readLine();
            in.close();
            out.close();
            return result.equals("true");
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}