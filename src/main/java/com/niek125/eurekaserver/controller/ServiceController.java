package com.niek125.eurekaserver.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/server-instances")
public class ServiceController {
    private final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    private final PeerAwareInstanceRegistry registry;
    private int index;

    @Autowired
    public ServiceController(PeerAwareInstanceRegistry registry) {
        this.registry = registry;
        index = 0;
    }

    @RequestMapping("/getinstance/{service-name}")
    public String getInstance(@PathVariable("service-name") String service) {
        logger.info("getting instances for: " + service);
        List<InstanceInfo> instances = this.registry.getApplications().getRegisteredApplications(service).getInstancesAsIsFromEureka();
        index++;
        if (index >= instances.size()) {
            index = 0;
        }
        logger.info(String.format("picking %x out of %x", index + 1, instances.size()));
        String ip = instances.get(index).getIPAddr() + ":" + instances.get(index).getPort();
        logger.info("returning: " + ip);
        return ip;
    }

}
