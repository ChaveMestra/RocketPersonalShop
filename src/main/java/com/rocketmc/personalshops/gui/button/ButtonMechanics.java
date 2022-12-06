package com.rocketmc.personalshops.gui.button;

import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ButtonMechanics {

    void onClick(ShopPlayer shopPlayer, InventoryClickEvent event);
}
