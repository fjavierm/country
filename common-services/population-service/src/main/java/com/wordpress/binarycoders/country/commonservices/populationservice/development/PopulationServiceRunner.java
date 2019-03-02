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

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.InternetProtocol;
import com.wordpress.binarycoders.country.devtools.containermanagement.ContainerConfig;
import com.wordpress.binarycoders.country.devtools.containermanagement.ContainerRunner;
import com.wordpress.binarycoders.country.devtools.containermanagement.EnvironmentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PopulationServiceRunner extends ContainerRunner {

    @Autowired
    public PopulationServiceRunner(final Environment environment) {
        super(environment);
    }

    @Override
    public void setConfig() {
        final ContainerConfig dbConfig;
        final List<ExposedPort> exposedPorts = new ArrayList<>();

        exposedPorts.add(new ExposedPort(5432, InternetProtocol.TCP));

        dbConfig = new ContainerConfig()
                .setName("postgres")
                .setTag("11.2")
                .setPorts(exposedPorts);

        config = new EnvironmentConfig()
                .addConfig(dbConfig);
    }
}