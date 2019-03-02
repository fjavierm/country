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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

public class EnvironmentConfig {

    private Set<ContainerConfig> containerConfigs;

    public EnvironmentConfig() {
        this.containerConfigs = new HashSet<>();
    }

    public EnvironmentConfig addConfig(final ContainerConfig config) {
        this.containerConfigs.add(config);
        return this;
    }

    public Set<ContainerConfig> getContainerConfigs() {
        return containerConfigs;
    }

    public EnvironmentConfig setContainerConfigs(Set<ContainerConfig> containerConfigs) {
        this.containerConfigs = containerConfigs;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final EnvironmentConfig that = (EnvironmentConfig) o;

        return new EqualsBuilder()
                .append(containerConfigs, that.containerConfigs)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(containerConfigs)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("containerConfigs", containerConfigs)
                .toString();
    }
}
