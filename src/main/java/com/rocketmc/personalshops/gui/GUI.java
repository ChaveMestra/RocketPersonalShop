package com.rocketmc.personalshops.gui;

import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Getter
@Setter
public abstract class GUI implements GUIMechanics {

    public ShopPlayer shopPlayer;
    public Inventory inventory;
    boolean allowClick;

    public GUI(ShopPlayer shopPlayer, boolean allowClick) {
        this.shopPlayer = shopPlayer;
        this.allowClick = allowClick;

    }

    public void refresh() {
        inventory.clear();
        getButtons().forEach(this::implementButton);
        for (HumanEntity humanEntity : inventory.getViewers()) {
            Player player = (Player) humanEntity;
            player.updateInventory();
        }
    }

    public void beforeCloseAction() {

    }


    public void open() {
        shopPlayer.getPlayer().openInventory(inventory);
    }


    public void implementButton(Button button) {
        inventory.setItem(button.getSlot(), button.getItemStack());
    }
}
