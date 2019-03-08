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

package com.wordpress.binarycoders.country.devtools.persistenceutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;

public class DataSourceRetrier {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceRetrier.class);

    private static final long TIME_BETWEEN_ATTEMPTS = 10_000;

    private DataSourceRetrier() {
    }

    /**
     * Block calling thread until a call to {@link DataSource#getConnection()} is successful <br>
     * Main use case is to wait on app startup for database to be present
     *
     * @param dataSource configured datasource
     * @param jdbcUrl    connection url
     */
    public static void waitForConnection(DataSource dataSource, String jdbcUrl) {
        waitForDataSource(dataSource, jdbcUrl, Integer.MAX_VALUE);
    }

    /**
     * Block calling thread until a call to {@link DataSource#getConnection()} is successful <br>
     * Main use case is to wait on app startup for database to be present
     *
     * @param dataSource  configured datasource
     * @param jdbcUrl     connection url
     * @param numAttempts maximum number of attempts
     */
    private static void waitForDataSource(DataSource dataSource, String jdbcUrl, int numAttempts) {
        int attempt = 0;

        while (attempt < numAttempts) {
            attempt++;

            try (final Connection connection = dataSource.getConnection()) {
                logger.info("Successfully connected to {}", jdbcUrl);

                return;
            } catch (Exception e) {
                try {
                    Thread.sleep(TIME_BETWEEN_ATTEMPTS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();

                    throw new RuntimeException(ie);

                }
                logger.warn("Failed to connect to {} attempt number {}. Reason: {} ", jdbcUrl, attempt, e.getMessage());
            }
        }

        throw new RuntimeException("Failed to connect to " + jdbcUrl + " after " + attempt + " attempts");
    }
}
