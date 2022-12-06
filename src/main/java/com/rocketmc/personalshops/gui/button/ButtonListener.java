package com.rocketmc.personalshops.gui.button;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.GUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ButtonListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null) return;

        if (e.getCurrentItem() == null) return;

        if (e.getCurrentItem().getType() == Material.AIR) return;

        ShopPlayer shopPlayer = PersonalShops.getInstance().getShopPlayerManager().getShopPlayer((Player) e.getWhoClicked());
        if (shopPlayer == null) return;

        GUI gui = PersonalShops.getInstance().getGuiManager().getPlayerGUI(shopPlayer);
        if (gui == null) return;


        Button button = gui.getButtons().stream().filter(button1 -> button1.getItemStack().isSimilar(e.getCurrentItem()))
                .findFirst()
                .orElse(null);
        if (button == null) return;

        e.setCancelled(true);
        button.onClick(shopPlayer, e);
    }
}
