package dev.blizzardlibrary.database.hikari;

import com.google.common.collect.Lists;
import dev.blizzardlibrary.task.kronos.KronosChain;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

public class QueryCommands {

    private static QueryCommands instance = null;
    private QueryHandler queryHandler = QueryHandler.getInstance();

    public static QueryCommands getInstance() {
        if (instance == null) {
            instance = new QueryCommands();
        }

        return instance;
    }

    public void insertData(String columns, String values, String table) {
        queryHandler.update("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ")");
    }

    public void deleteData(String column, String logic_gate, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
            queryHandler.update("DELETE FROM " + table + " WHERE " + column + logic_gate + data + ";");
        }
    }


    public boolean exists(String column, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
            try {
                ResultSet resultSet = queryHandler.query("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";");
                if (resultSet.next()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void createTable(String table, String columns) {
        queryHandler.update("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ");");
    }

    public void deleteTable(String table) {
        queryHandler.update("DROP TABLE " + table + ";");
    }

    public boolean upsert(String selected, Object object, String column, String data, String table) {
        if (object != null)
            object = "'" + object + "'";
        if (data != null)
            data = "'" + data + "'";
        try (ResultSet rs = queryHandler.query("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";")) {
            if (rs.next()) {
                queryHandler.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + column + "=" + data + ";");
            } else {
                insertData(column + ", " + selected, data + ", " + object, table);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public void set(String selected, Object object, String column, String logic_gate, String data, String table) {
        if (object != null)
            object = "'" + object + "'";
        if (data != null)
            data = "'" + data + "'";
        queryHandler.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + column + logic_gate + data + ";");
    }

    public void set(String selected, Object object, String[] where_arguments, String table) {
        StringBuilder arguments = new StringBuilder();
        for (String argument : where_arguments)
            arguments.append(argument).append(" AND ");
        if (arguments.length() <= 5)
            return;
        arguments = new StringBuilder(arguments.substring(0, arguments.length() - 5));
        if (object != null)
            object = "'" + object + "'";
        queryHandler.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + arguments + ";");
    }

    public Object get(String selected, String[] where_arguments, String table) {
        StringBuilder arguments = new StringBuilder();
        for (String argument : where_arguments)
            arguments.append(argument).append(" AND ");
        if (arguments.length() <= 5)
            return Boolean.FALSE;
        arguments = new StringBuilder(arguments.substring(0, arguments.length() - 5));
        try (ResultSet rs = queryHandler.query("SELECT * FROM " + table + " WHERE " + arguments + ";")) {
            if (rs.next())
                return rs.getObject(selected);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object> listGet(String selected, String[] where_arguments, String table) {
        List<Object> array = Collections.synchronizedList(Lists.newArrayList());
        StringBuilder arguments = new StringBuilder();
        for (String argument : where_arguments)
            arguments.append(argument).append(" AND ");
        if (arguments.length() <= 5)
            return array;
        arguments = new StringBuilder(arguments.substring(0, arguments.length() - 5));
        try (ResultSet rs = queryHandler.query("SELECT * FROM " + table + " WHERE " + arguments + ";")) {
            while (rs.next()) {
                array.add(rs.getObject(selected));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public Object get(String selected, String column, String logic_gate, String data, String table) {
        if (data != null)
            data = "'" + data + "'";
        try (ResultSet rs = queryHandler.query("SELECT * FROM " + table
                + " WHERE " + column + logic_gate + data + ";")) {
            if (rs.next())
                return rs.getObject(selected);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object> listGet(String selected, String column, String logic_gate, String data, String table) {
        List<Object> array = Collections.synchronizedList(Lists.newArrayList());
        if (data != null)
            data = "'" + data + "'";
        try (ResultSet rs = queryHandler.query("SELECT * FROM " + table + " WHERE " + column + logic_gate + data + ";")) {
            while (rs.next())
                array.add(rs.getObject(selected));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public void callAsyncQuery(Runnable runnable, long delay) {
        KronosChain.createCompletedKronosChain().runAsyncDelayed(runnable, delay);
    }
}
