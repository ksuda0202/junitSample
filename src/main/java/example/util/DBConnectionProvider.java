package main.java.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {
    private String url;
    private String rdbName;
    private String user;
    private String password;

    private DBConnectionProvider(Builder builder) {
        this.url = builder.url;
        this.rdbName = builder.rdbName;
        this.user = builder.user;
        this.password = builder.password;
    }

    public Connection getConnection() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(url + rdbName, user, password);

        return connection;
    }

    public static class Builder{
        private String url;
        private String rdbName;
        private String user;
        private String password;

        public Builder(){
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder rdbName(String rdbName) {
            this.rdbName = rdbName;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public DBConnectionProvider build() {
            return new DBConnectionProvider(this);
        }
    }
}
