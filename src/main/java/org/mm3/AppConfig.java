package org.mm3;

import org.mm3.serialdata.SerialPortConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: nbt86yz
 * Date: 7/7/15
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
@ComponentScan(basePackages = "org.mm3.capture")
public class AppConfig {

    @Bean
    public SerialPortConfig getBean() {
        return new SerialPortConfig();
    }
}
