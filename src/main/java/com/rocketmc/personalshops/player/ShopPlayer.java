package com.rocketmc.personalshops.player;


import com.rocketmc.personalshops.bank.Bank;
import com.rocketmc.personalshops.shop.Shop;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class ShopPlayer {


    public UUID playerUuid;
    public String playerName;
    @Setter
    public Shop shop;
    @Setter
    public Bank bank;

    public ShopPlayer(UUID playerUuid, String playerName) {
        this.playerName = playerName;
        this.playerUuid = playerUuid;
        setBank(new Bank(this));

    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUuid);
    }

    public boolean hasShop() {
        return shop != null;
    }


    public int getShopPermissionCooldown() {
        if (getPlayer() == null) {
            System.out.println("tentei pegar cooldown de quem ta offline, qq pegou?");
            return 300;
        }
        //2 horas
        int minutes = 60;
        if (getPlayer().hasPermission("rocket.mvp+")) {

            minutes = 9999;
        } else if (getPlayer().hasPermission("rocket.mvp")) {
            // 5 horas
            minutes = 60 * 5;
        } else if (getPlayer().hasPermission("rocket.vip+")) {
            minutes = 60 * 4;
        } else if (getPlayer().hasPermission("rocket.vip")) {
            minutes = 60 * 3;

        }
        //retorno em segs
        return minutes * 60;
    }

    public int getBankSlots() {
        if (getPlayer() == null) {
            System.out.println("tentei pegar bank slots de quem ta offline, qq pegou?");
            return 9;
        }
        //todo add suporte pra nao multiplos de 9
        //1 row
        int slots = 9;
        if (getPlayer().hasPermission("rocket.mvp+")) {
            //5 rows (max)
            slots = 9 * 5;
        } else if (getPlayer().hasPermission("rocket.mvp")) {
            slots = 9 * 4;
        } else if (getPlayer().hasPermission("rocket.vip+")) {
            slots = 9 * 3;
        } else if (getPlayer().hasPermission("rocket.vip")) {
            slots = 9 * 2;
        }
        return slots;

    }

    public int getShopSlots() {

        if (getPlayer() == null) {
            System.out.println("tentei pegar shop slots de quem ta offline, qq pegou?");
            return 9;
        }
        //todo add suporte pra nao multiplos de 9
        //1 row
        //ja que sao iguais xd
        return getBankSlots();
    }

}
