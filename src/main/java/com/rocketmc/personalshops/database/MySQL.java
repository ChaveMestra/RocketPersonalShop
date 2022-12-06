package com.rocketmc.personalshops.database;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.config.Config;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {


    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS personal_shops (UUID VARCHAR(64),name VARCHAR(64), " +
            "bank text, bin text)";


    public void initializeConnectionPool() {
        Config configuration = PersonalShops.getInstance().getConfiguration();
        PersonalShops.getInstance().getHikari().setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        PersonalShops.getInstance().getHikari().addDataSourceProperty("serverName", configuration.getConfig().getString("MYSQL.Host"));
        PersonalShops.getInstance().getHikari().addDataSourceProperty("port", 3306);
        PersonalShops.getInstance().getHikari().addDataSourceProperty("databaseName", configuration.getConfig().getString("MYSQL.Database"));
        PersonalShops.getInstance().getHikari().addDataSourceProperty("user", configuration.getConfig().getString("MYSQL.User"));
        PersonalShops.getInstance().getHikari().addDataSourceProperty("password", configuration.getConfig().getString("MYSQL.Password"));

        Bukkit.getScheduler().runTaskAsynchronously(PersonalShops.getInstance(), new Runnable() {
            @Override
            public void run() {
                try (Connection connection = PersonalShops.getInstance().getHikari().getConnection();
                     PreparedStatement statement = connection.prepareStatement(CREATE_TABLE)) {
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

