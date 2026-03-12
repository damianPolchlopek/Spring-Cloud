package org.example.photoappapiacountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PhotoAppApiAcountManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoAppApiAcountManagementApplication.class, args);
    }

}
