package com.iamforyydev.rocketparkour.storage;

import com.iamforyydev.rocketparkour.user.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Storage
        implements StorageInterface {

    private final String table = "parkour_table";
    private final String hostname;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    private HikariDataSource hikariDataSource;

    public Storage(
            String hostname,
            String port,
            String database,
            String username,
            String password
    ) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        connect();
    }

    @Override
    public void connect() {
        this.hikariDataSource = new HikariDataSource();
        this.hikariDataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.hikariDataSource.addDataSourceProperty("serverName", hostname);
        this.hikariDataSource.addDataSourceProperty("port", port);
        this.hikariDataSource.addDataSourceProperty("databaseName", database);
        this.hikariDataSource.addDataSourceProperty("user", username);
        this.hikariDataSource.addDataSourceProperty("password", password);
    }

    @Override
    public void createTable(
    ) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{

            String query = "CREATE TABLE IF NOT EXISTS "+table+" (name VARCHAR(16), time INT(10));";
            connection = getHikariDataSource().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    @Override
    public void saveUser(
            final User user
    ) {
        if (user.getTime() == 0) {
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = getHikariDataSource().getConnection();
            if (isExits(user)) {
                String query = "UPDATE "+table+" SET time = ? WHERE name = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, user.getTime());
                preparedStatement.setString(2, user.getPlayer().getName());
                preparedStatement.execute();
            } else {

                String query = "INSERT INTO "+table+" (name, time) VALUES(?, ?);";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.getPlayer().getName());
                preparedStatement.setInt(2, user.getTime());
                preparedStatement.execute();

            }

        }catch (SQLException exception){
            throw new RuntimeException(exception);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    @Override
    public boolean isExits(
            final User user
    ) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getHikariDataSource().getConnection();

            String query = "SELECT * FROM "+table+" WHERE name = ?;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getPlayer().getName());
            resultSet = preparedStatement.executeQuery();
            return resultSet != null && resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public Map<String, Integer> getBestTime() {
        Map<String, Integer> stringIntegerMap = new HashMap<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = getHikariDataSource().getConnection();

            String query = "SELECT name, time FROM " + table;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet == null){
                return null;
            }


            while (resultSet.next()) {

                String name = resultSet.getString("name");
                Integer time = resultSet.getInt("time");

                stringIntegerMap.put(name, time);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }


        return stringIntegerMap;
    }

    @Override
    public boolean isConnected(
    ) {
        return hikariDataSource != null;
    }

    @Override
    public void close(
            final Connection connection,
            final PreparedStatement preparedStatement,
            final ResultSet resultSet
    ) {

        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void disconnect(
    ) {
        if (isConnected()) getHikariDataSource().close();
    }

    public HikariDataSource getHikariDataSource(
    ) {
        return hikariDataSource;
    }
}

