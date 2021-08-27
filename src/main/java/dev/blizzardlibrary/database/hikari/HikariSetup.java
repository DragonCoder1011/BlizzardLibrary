package dev.blizzardlibrary.database.hikari;

import com.zaxxer.hikari.HikariDataSource;
import dev.blizzardlibrary.string.chat.MessageUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class HikariSetup {

    private static HikariDataSource dataSource;

    // Hikari Settings
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public HikariSetup init(int poolSize, String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        dataSource = new HikariDataSource();
        setDataSourceSettings(poolSize);
        MessageUtils.sendConsoleMessage("&b&lBlizzard&3&lLib &7made a connection");
        return this;
    }


    /**
     * Sets default settings for hikari
     */
    private void setDataSourceSettings(int poolSize) {
        dataSource.setMaximumPoolSize(poolSize);
        dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        dataSource.addDataSourceProperty("serverName", host);
        dataSource.addDataSourceProperty("port", port);
        dataSource.addDataSourceProperty("databaseName", database);
        dataSource.addDataSourceProperty("user", username);
        dataSource.addDataSourceProperty("password", password);
    }

/*    /**
     * Create a table
     *
     * @param tables Prepared Statement
     *
    private HikariSetup createTable(String... tables) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(Arrays.toString(tables))) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }*/


    public boolean isConnected() {
        return !dataSource.isClosed();
    }


    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
