package com.example.bookstore.config;

import org.testcontainers.containers.MySQLContainer;

public class CustomMySqlContainer {
    private static final String DB_VERSION = "mysql:5.7.24";
    public static MySQLContainer mySQLContainer;

    static {
        mySQLContainer = new MySQLContainer(DB_VERSION)
                .withDatabaseName("testdb")
                .withUsername("sa")
                .withPassword("sa");
        mySQLContainer.start();
    }



}
