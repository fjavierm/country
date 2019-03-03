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

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.InternetProtocol;
import com.github.dockerjava.api.model.Ports;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class ContainerConfig {

    private static final Long DEFAULT_TIMEOUT = 30L;

    private String repository;
    private String tag;
    private String suffix;
    private List<ExposedPort> exposedPorts;
    private Ports portBindings;
    private List<String> environment;
    private Long downloadTimeout;

    public ContainerConfig() {
        exposedPorts = new ArrayList<>();
        portBindings = new Ports();
        downloadTimeout = DEFAULT_TIMEOUT;
    }

    public ContainerConfig addPort(final int port, final InternetProtocol protocol) {
        final ExposedPort exposedPort = InternetProtocol.TCP == protocol ? ExposedPort.tcp(port) : ExposedPort.udp(port);

        exposedPorts.add(exposedPort);
        portBindings.bind(exposedPort, Ports.Binding.bindPort(port));

        return this;
    }

    public String getContainerName() {
        return String.format("%s-%s-%s", repository, tag, suffix);
    }

    public String getRepositoryTag() {
        return String.format("%s:%s", repository, tag);
    }

    public String getRepository() {
        return repository;
    }

    public ContainerConfig setRepository(String repository) {
        this.repository = repository;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public ContainerConfig setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public ContainerConfig setSuffix(final String suffix) {
        this.suffix = suffix;
        return this;
    }

    public List<ExposedPort> getExposedPorts() {
        return exposedPorts;
    }

    public ContainerConfig setExposedPorts(List<ExposedPort> exposedPorts) {
        this.exposedPorts = exposedPorts;
        return this;
    }

    public Ports getPortBindings() {
        return portBindings;
    }

    public ContainerConfig setPortBindings(final Ports portBindings) {
        this.portBindings = portBindings;
        return this;
    }

    public List<String> getEnvironment() {
        return environment;
    }

    public ContainerConfig setEnvironment(final List<String> environment) {
        this.environment = environment;
        return this;
    }

    public Long getDownloadTimeout() {
        return downloadTimeout;
    }

    public ContainerConfig setDownloadTimeout(Long downloadTimeout) {
        this.downloadTimeout = downloadTimeout;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final ContainerConfig config = (ContainerConfig) o;

        return new EqualsBuilder()
                .append(repository, config.repository)
                .append(tag, config.tag)
                .append(suffix, config.suffix)
                .append(exposedPorts, config.exposedPorts)
                .append(portBindings, config.portBindings)
                .append(environment, config.environment)
                .append(downloadTimeout, config.downloadTimeout)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(repository)
                .append(tag)
                .append(suffix)
                .append(exposedPorts)
                .append(portBindings)
                .append(environment)
                .append(downloadTimeout)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("repository", repository)
                .append("tag", tag)
                .append("suffix", suffix)
                .append("exposedPorts", exposedPorts)
                .append("portBindings", portBindings)
                .append("environment", environment)
                .append("downloadTimeout", downloadTimeout)
                .toString();
    }
}
