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

package com.wordpress.binarycoders.country.devtools.containermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public abstract class ContainerRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ContainerRunner.class);

    private static final String DEV_PROFILE = "development";

    private final Environment environment;
    protected EnvironmentConfig config;

    public ContainerRunner(final Environment environment) {
        this.environment = environment;
        setConfig();
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        if (Arrays.asList(environment.getActiveProfiles()).contains(DEV_PROFILE)) {
            logger.info("Development environment selected.");

            try (final SetDevelopmentEnvironment dev = new SetDevelopmentEnvironment(config)) {
                dev.manageContainers();
            } catch (Exception ex) {
                logger.error("Problem setting development environment.", ex);
            }
        } else {
            logger.debug("This is not a development environment.");
        }
    }

    public abstract void setConfig();
}
