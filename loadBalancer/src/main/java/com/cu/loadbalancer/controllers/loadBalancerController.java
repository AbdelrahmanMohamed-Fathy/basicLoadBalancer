package com.cu.loadbalancer.controllers;

import com.cu.loadbalancer.services.loadBalancerService;
import com.cu.loadbalancer.models.serverInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loadBalancerController {

    loadBalancerService service = new loadBalancerService();

    @Autowired
    public void setService(loadBalancerService service) {
        this.service = service;
    }

    @PostMapping("/register")
    ResponseEntity<String> registerNewServer(@RequestBody serverInfo newServerInfo) {
        if(service.registerServer(newServerInfo))
            return ResponseEntity.ok("Success");
        return ResponseEntity.badRequest().body("Failure");
    }

    @PostMapping("/check")
    boolean checkString(@RequestBody String task) {
        return service.assignTask(task);
    }
}
