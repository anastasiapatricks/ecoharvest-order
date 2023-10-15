package com.ecoharvest.deliveryorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Simple class to start up the application.
 *
 * @SpringBootApplication adds:
 *  @Configuration
 *  @EnableAutoConfiguration
 *  @ComponentScan
 */
@SpringBootApplication
@EnableJpaRepositories("com.ecoharvest.deliveryorder.repository")
@EntityScan("com.ecoharvest.deliveryorder.domain")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
