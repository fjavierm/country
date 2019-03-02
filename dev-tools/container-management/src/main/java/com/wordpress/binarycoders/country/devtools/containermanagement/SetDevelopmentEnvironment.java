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

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SetDevelopmentEnvironment implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(SetDevelopmentEnvironment.class);

    private static final String CONTAINER_STATE_RUNNING = "running";

    private final EnvironmentConfig environmentConfig;

    public SetDevelopmentEnvironment(final EnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }

    public void manageContainers() throws Exception {
        final DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        initializeContainers(dockerClient);
    }

    private void initializeContainers(final DockerClient dockerClient) throws InterruptedException {
        for (ContainerConfig config : environmentConfig.getContainerConfigs()) {
            prepareImage(dockerClient, config);
            prepareContainer(dockerClient, config);
        }
    }

    private void prepareContainer(final DockerClient dockerClient, final ContainerConfig config) {
        final String containerId;
        final List<Container> containersAll = dockerClient.listContainersCmd().withShowAll(true).exec();
        final Container container = containersAll.stream().filter(c -> c.getImage().equals(config.getRepositoryTag()))
                .findFirst().orElse(null);

        if (container == null || !isContainerRunning(container)) {
            logger.warn("Container not running. Starting it...");

            if (container == null) {
                containerId = createContainer(dockerClient, config);
            } else {
                containerId = container.getId();
            }

            dockerClient.startContainerCmd(containerId).exec();
        }

        logger.info("Container is prepared to be used.");
    }

    private boolean isContainerRunning(final Container container) {
        return CONTAINER_STATE_RUNNING.equals(container.getState());
    }

    private String createContainer(final DockerClient dockerClient, final ContainerConfig config) {
        final CreateContainerResponse container = dockerClient.createContainerCmd(config.getRepositoryTag())
                .withName(config.getContainerName())
                .withExposedPorts(config.getPorts())
                .exec();

        return container.getId();
    }

    private void prepareImage(final DockerClient dockerClient, final ContainerConfig config) throws InterruptedException {
        final List<Image> images = dockerClient.listImagesCmd().exec();

        if (images.stream().noneMatch(image -> containsVersion(image, config))) {
            logger.warn("Image not found. Pulling it...");

            pullImage(dockerClient, config);
        }

        logger.info("Image is prepared to be used.");
    }

    private void pullImage(final DockerClient dockerClient, final ContainerConfig config) throws InterruptedException {
        dockerClient.pullImageCmd(config.getRepository())
                .withTag(config.getTag())
                .exec(new PullImageResultCallback())
                .awaitCompletion(config.getDownloadTimeout(), TimeUnit.SECONDS);
    }

    private boolean containsVersion(final Image image, final ContainerConfig config) {
        return Arrays.asList(image.getRepoTags()).contains(config.getRepositoryTag());
    }

    @Override
    public void close() throws IOException {

    }
}
