package com.asterlsker.housepit.rdbms.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.asterlsker.housepit"])
@EntityScan(basePackages = ["com.asterlsker.housepit"])
class DBConfig {
}