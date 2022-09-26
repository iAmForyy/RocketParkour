package com.iamforyydev.rocketparkour.storage;

import com.iamforyydev.rocketparkour.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public interface StorageInterface {

    void connect();
    void createTable();
    boolean isConnected();

    /* USER METHODS */

    void saveUser(User user);
    boolean isExits(User user);

    /* ============ */

    Map<String, Integer> getBestTime();
    void close(
            final Connection connection,
            final PreparedStatement preparedStatement,
            final ResultSet resultSet
    );
    void disconnect();

}
