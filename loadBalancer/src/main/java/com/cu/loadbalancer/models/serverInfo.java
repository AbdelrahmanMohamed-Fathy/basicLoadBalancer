package com.cu.loadbalancer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class serverInfo {
    private InetAddress ip;
    private int port;
}
