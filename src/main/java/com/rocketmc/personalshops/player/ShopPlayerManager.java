package com.rocketmc.personalshops.player;

import com.rocketmc.personalshops.PersonalShops;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ShopPlayerManager {

    public List<ShopPlayer> shopPlayerList;

    public ShopPlayerManager() {
        shopPlayerList = new ArrayList<>();
    }

    public void createShopPlayer(Player player) {
        String name = player.getName();
        UUID uuid = player.getUniqueId();
        ShopPlayer shopPlayer = new ShopPlayer(uuid, name);
        shopPlayerList.add(shopPlayer);
        PersonalShops.getInstance().getDatabaseManager().insertShopPlayer(shopPlayer);
    }

    public void createShopPlayer(UUID uuid, String playerName, Inventory bank, Inventory bin) {
        System.out.println("criando shopplayer " + playerName);
        ShopPlayer shopPlayer = new ShopPlayer(uuid, playerName);
        shopPlayerList.add(shopPlayer);

        //anti mudanca repentina do tamanho do banco

        if (shopPlayer.getBank().getBankInventory().getSize() < bank.getContents().length) {
            System.out.println("ta, tem mto conteudo nesse bau do " + shopPlayer.getPlayerName() + ".. adicionando 1 a 1");
            for (ItemStack itemStack : bank.getContents()) {
                if (itemStack != null) {
                    if (shopPlayer.getBank().getBankInventory().firstEmpty() != -1) {
                        shopPlayer.getBank().getBankInventory().addItem(itemStack);
                    } else {
                        shopPlayer.getPlayer().getInventory().addItem(itemStack);
                    }
                }
            }
        } else {
            shopPlayer.getBank().getBankInventory().setContents(bank.getContents());
        }
        //


        for (ItemStack itemStack : bin.getContents()) {
            if (itemStack != null) {
                shopPlayer.getBank().getBinContent().add(itemStack);
            }
        }
    }

    public ShopPlayer getShopPlayer(Player player) {
        for (ShopPlayer shopPlayer : shopPlayerList) {
            if (shopPlayer.playerUuid.equals(player.getUniqueId())) {
                return shopPlayer;
            }
        }
        System.out.println("nao achei shop player do " + player.getName());

        return null;
    }


}
