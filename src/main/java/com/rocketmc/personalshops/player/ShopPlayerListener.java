package com.rocketmc.personalshops.player;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.prompt.Prompt;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ShopPlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(e.getPlayer()) == null) {
            System.out.println("criando shop player ");
            PersonalShops.getInstance().getDatabaseManager().loadShopPlayer(e.getPlayer().getUniqueId(), e.getPlayer());
            //PersonalShops.getInstance().getShopPlayerManager().createShopPlayer(e.getPlayer());
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Prompt prompt = PersonalShops.getInstance().getPromptManager().getPlayerPrompt(e.getPlayer());
        if (prompt != null) {
            PersonalShops.getInstance().getPromptManager().endPrompt(prompt);
        }
        if (PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(e.getPlayer()).getShop() != null)
        PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(e.getPlayer()).getShop().removeShop();
    }
}
