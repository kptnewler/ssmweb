package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"dao", "service"})
@PropertySource("classpath:jdbc.properties")
public class MybatisConfig {

}
