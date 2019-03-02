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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ContainerConfig {

    private static final Long DEFAULT_TIMEOUT = 30L;

    private String name;
    private String tag;
    private List<ExposedPort> ports;
    private Long downloadTimeout;

    public String getContainerName() {
        return String.format("%s-%s-db", name, tag);
    }

    public String getRepositoryTag() {
        return String.format("%s:%s", name, tag);
    }

    public String getName() {
        return name;
    }

    public ContainerConfig setName(String name) {
        this.name = name;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public ContainerConfig setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public List<ExposedPort> getPorts() {
        return ports;
    }

    public ContainerConfig setPorts(List<ExposedPort> ports) {
        this.ports = ports;
        return this;
    }

    public Long getDownloadTimeout() {
        return downloadTimeout == null ? DEFAULT_TIMEOUT : downloadTimeout;
    }

    public ContainerConfig setDownloadTimeout(Long downloadTimeout) {
        this.downloadTimeout = downloadTimeout;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ContainerConfig that = (ContainerConfig) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(tag, that.tag)
                .append(ports, that.ports)
                .append(downloadTimeout, that.downloadTimeout)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(tag)
                .append(ports)
                .append(downloadTimeout)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("tag", tag)
                .append("ports", ports)
                .append("downloadTimeout", downloadTimeout)
                .toString();
    }
}
