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

import java.util.List;

public class EnvironmentConfig {

    private static final Long DEFAULT_TIMEOUT = 30L;

    private String db;
    private String dbTag;
    private List<ExposedPort> containerPorts;
    private Long dbDownloadTimeout = DEFAULT_TIMEOUT;

    public String getDbContainerName() {
        return String.format("%s-%s-db", db, dbTag);
    }

    public String getDbRepoTag() {
        return String.format("%s:%s", db, dbTag);
    }

    public String getDb() {
        return db;
    }

    public EnvironmentConfig setDb(final String db) {
        this.db = db;
        return this;
    }

    public String getDbTag() {
        return dbTag;
    }

    public EnvironmentConfig setDbTag(final String dbTag) {
        this.dbTag = dbTag;
        return this;
    }

    public List<ExposedPort> getContainerPorts() {
        return containerPorts;
    }

    public EnvironmentConfig setContainerPorts(final List<ExposedPort> containerPorts) {
        this.containerPorts = containerPorts;
        return this;
    }

    public Long getDbDownloadTimeout() {
        return dbDownloadTimeout;
    }

    public EnvironmentConfig setDbDownloadTimeout(final Long dbDownloadTimeout) {
        this.dbDownloadTimeout = dbDownloadTimeout;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final EnvironmentConfig config = (EnvironmentConfig) o;

        return new EqualsBuilder()
                .append(db, config.db)
                .append(dbTag, config.dbTag)
                .append(containerPorts, config.containerPorts)
                .append(dbDownloadTimeout, config.dbDownloadTimeout)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(db)
                .append(dbTag)
                .append(containerPorts)
                .append(dbDownloadTimeout)
                .toHashCode();
    }
}
