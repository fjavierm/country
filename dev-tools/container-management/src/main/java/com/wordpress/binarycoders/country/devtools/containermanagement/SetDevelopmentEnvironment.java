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

    private final EnvironmentConfig config;

    public SetDevelopmentEnvironment(final EnvironmentConfig config) {
        this.config = config;
    }

    public void manageContainers() throws Exception {
        final DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        prepareDb(dockerClient);
    }

    private void prepareDb(final DockerClient dockerClient) throws InterruptedException {
        prepareDbImage(dockerClient);
        prepareDbContainer(dockerClient);
    }

    private void prepareDbContainer(final DockerClient dockerClient) {
        final List<Container> containers = dockerClient.listContainersCmd().exec();

        if (containers.stream().noneMatch(c -> c.getImage().equals(config.getDbRepoTag()))) {
            logger.warn("DB container not running. Starting it...");

            startDbContainer(dockerClient);
        }

        logger.info("DB container is prepared to be used.");
    }

    private void startDbContainer(final DockerClient dockerClient) {
        final CreateContainerResponse container = dockerClient.createContainerCmd(config.getDbRepoTag())
                .withName(config.getDbContainerName())
                .withExposedPorts(config.getContainerPorts())
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();
    }

    private void prepareDbImage(final DockerClient dockerClient) throws InterruptedException {
        final List<Image> images = dockerClient.listImagesCmd().exec();

        if (images.stream().noneMatch(this::containsVersion)) {
            logger.warn("DB image not found. Pulling it...");

            pullDbImage(dockerClient);
        }

        logger.info("DB image is prepared to be used.");
    }

    private void pullDbImage(final DockerClient dockerClient) throws InterruptedException {
        dockerClient.pullImageCmd(config.getDb())
                .withTag(config.getDbTag())
                .exec(new PullImageResultCallback())
                .awaitCompletion(config.getDbDownloadTimeout(), TimeUnit.SECONDS);
    }

    private boolean containsVersion(final Image image) {
        return Arrays.asList(image.getRepoTags()).contains(config.getDbRepoTag());
    }

    @Override
    public void close() throws IOException {

    }
}
