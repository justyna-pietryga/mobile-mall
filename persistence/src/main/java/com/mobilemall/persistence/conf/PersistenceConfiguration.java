package com.mobilemall.persistence.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableJpaRepositories
public class PersistenceConfiguration {
}
