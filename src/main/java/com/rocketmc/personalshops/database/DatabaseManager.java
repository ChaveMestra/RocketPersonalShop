package com.rocketmc.personalshops.database;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.util.EncodeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {
    private static final String SAVE = "UPDATE personal_shops SET bank=?,bin=? WHERE UUID=?";
    private static final String INSERT = "INSERT INTO personal_shops VALUES(?,?,?,?)";
    private static final String SELECT = "SELECT * FROM personal_shops";
    private static final String SELECT_SINGLE = "SELECT * FROM personal_shops WHERE UUID=?";

    public void insertShopPlayer(ShopPlayer shopPlayer) {
        Bukkit.getScheduler().runTaskAsynchronously(PersonalShops.getInstance(), new Runnable() {
            @Override
            public void run() {
                try (Connection connection = PersonalShops.getInstance().getHikari().getConnection();
                     PreparedStatement insert = connection.prepareStatement(INSERT)) {
                    insert.setString(1, shopPlayer.getPlayerUuid().toString());
                    insert.setString(2, shopPlayer.getPlayerName());
                    insert.setString(3, EncodeUtil.toBase64(shopPlayer.getBank().getBankInventory()));
                    insert.setString(4, EncodeUtil.toBase64(shopPlayer.getBank().getBinInventory()));
                    insert.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void loadShopPlayers() {
        Bukkit.getScheduler().runTaskAsynchronously(PersonalShops.getInstance(), new Runnable() {
            @Override
            public void run() {
                try (Connection connection = PersonalShops.getInstance().getHikari().getConnection();
                     PreparedStatement insert = connection.prepareStatement(SELECT)) {
                    ResultSet resultSet = insert.executeQuery();
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                        String playerName = resultSet.getString("name");
                        Inventory bankInventory = EncodeUtil.fromBase64(resultSet.getString("bank"));
                        Inventory binInventory = EncodeUtil.fromBase64(resultSet.getString("bin"));
                        PersonalShops.getInstance().getShopPlayerManager().createShopPlayer(uuid,
                                playerName,
                                bankInventory,
                                binInventory);
                    }

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadShopPlayer(UUID uuid, Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(PersonalShops.getInstance(), new Runnable() {
            @Override
            public void run() {
                try (Connection connection = PersonalShops.getInstance().getHikari().getConnection();
                     PreparedStatement insert = connection.prepareStatement(SELECT_SINGLE)) {
                    insert.setString(1, uuid.toString());
                    ResultSet resultSet = insert.executeQuery();
                    if (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                        String playerName = resultSet.getString("name");
                        Inventory bankInventory = EncodeUtil.fromBase64(resultSet.getString("bank"));
                        Inventory binInventory = EncodeUtil.fromBase64(resultSet.getString("bin"));
                        PersonalShops.getInstance().getShopPlayerManager().createShopPlayer(uuid,
                                playerName,
                                bankInventory,
                                binInventory);
                    } else {
                        PersonalShops.getInstance().getShopPlayerManager().createShopPlayer(player);
                    }

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void saveShopPlayer(ShopPlayer shopPlayer) {
        System.out.println("salvando shopplayer " + shopPlayer.getPlayerName());
        Bukkit.getScheduler().runTaskAsynchronously(PersonalShops.getInstance(), new Runnable() {
            @Override
            public void run() {
                try (Connection connection = PersonalShops.getInstance().getHikari().getConnection();
                     PreparedStatement insert = connection.prepareStatement(SAVE)) {
                    insert.setString(1, EncodeUtil.toBase64(shopPlayer.getBank().getBankInventory()));
                    insert.setString(2, EncodeUtil.toBase64(shopPlayer.getBank().getBinInventory()));
                    insert.setString(3, shopPlayer.getPlayerUuid().toString());
                    insert.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
