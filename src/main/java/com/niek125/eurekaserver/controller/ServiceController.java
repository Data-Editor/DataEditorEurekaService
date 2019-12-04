package com.niek125.eurekaserver.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/server-instances")
public class ServiceController {
    @Autowired
    private PeerAwareInstanceRegistry registry;
    private int index;

    public ServiceController() {
        index = 0;
    }

    @RequestMapping("/getinstance/{service-name}")
    public String getInstance(@PathVariable("service-name") String service){
        try {
            List<InstanceInfo> instances = this.registry.getApplications().getRegisteredApplications(service).getInstancesAsIsFromEureka();
            index++;
            if (index >= instances.size()){
                index = 0;
            }
            return instances.get(index).getHomePageUrl();
        }catch (Exception e) {
        }
        return null;
    }

}
