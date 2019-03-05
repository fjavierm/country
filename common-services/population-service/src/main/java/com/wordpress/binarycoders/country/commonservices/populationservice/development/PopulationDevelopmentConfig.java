/*
 * Copyright 2019 Javier Marquez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wordpress.binarycoders.country.commonservices.populationservice.development;

import com.github.dockerjava.api.model.InternetProtocol;
import com.wordpress.binarycoders.country.devtools.containermanagement.ContainerConfig;
import com.wordpress.binarycoders.country.devtools.containermanagement.ContainerRunner;
import com.wordpress.binarycoders.country.devtools.containermanagement.EnvironmentConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class PopulationDevelopmentConfig {

    private final Environment environment;

    @Autowired
    public PopulationDevelopmentConfig(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public HikariDataSource dataSource() {
        new ContainerRunner(environment, generateConfiguration()).run();

        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    private EnvironmentConfig generateConfiguration() {
        return new EnvironmentConfig()
                .addConfig(databaseContainerConfiguration());
    }

    private ContainerConfig databaseContainerConfiguration() {
        final List<String> variables = new ArrayList<>();

        variables.add("POSTGRES_USER=country");
        variables.add("POSTGRES_PASSWORD=pa$$w0rd");

        return new ContainerConfig()
                .setRepository("postgres")
                .setTag("11.2")
                .setSuffix("db")
                .setEnvironment(variables)
                .addPort(5432, InternetProtocol.TCP);
    }
}
