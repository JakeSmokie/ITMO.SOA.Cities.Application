package ru.itmo.jakesmokie.soa.lab01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.geniustree.springdata.jpa.repository.support.JpaSpecificationExecutorWithProjectionImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
@EnableEurekaClient
@RibbonClient(name = "first", configuration = SampleRibbonConfiguration.class)
@RestController
public class Lab01Application {
    @Autowired Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(Lab01Application.class, args);
    }

    @GetMapping("/info")
    public String info() {
        return environment.getProperty("server.port");
    }
}
