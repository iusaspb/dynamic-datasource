package org.ysaspb.dynds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A test application with one controller {@link org.ysaspb.dynds.controller.ItemController}
 */
@SpringBootApplication
public class DynamicDataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceApplication.class, args);
    }
}
