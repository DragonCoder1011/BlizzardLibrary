package dev.blizzardlibrary.database.hikari;

import dev.blizzardlibrary.task.kronos.KronosChain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryHandler {

    private static QueryHandler instance = null;

    public static QueryHandler getInstance() {
        if (instance == null) {
            instance = new QueryHandler();
        }

        return instance;
    }

    public boolean update(String queryCommand) {
        if (queryCommand == null) {
            return false;
        }

        try (Connection connection = HikariSetup.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(queryCommand)) {
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public ResultSet query(String command) {
        if (command == null) {
            return null;
        }

        ResultSet set = null;
        try (Connection connection = HikariSetup.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
                set = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return set;
    }
}
