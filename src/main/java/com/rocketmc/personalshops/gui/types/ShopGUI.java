package com.rocketmc.personalshops.gui.types;

import com.rocketmc.personalshops.gui.GUI;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.button.types.ShopConfigButton;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.shop.Shop;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;

@Getter
public class ShopGUI extends GUI {

    public Shop shop;

    public ShopGUI(Shop shop, ShopPlayer shopPlayer) {
        super(shopPlayer, true);
        this.shop = shop;
        build();
    }


    @Override
    public void build() {
        inventory = Bukkit.createInventory(null, getSize(), getTitle());
        getButtons().forEach(this::implementButton);
    }


    public int getConfigSlot() {
        return inventory.getSize() - 1;
    }

    @Override
    public void implementButton(Button button) {
        if (button instanceof ShopConfigButton) {
            ShopConfigButton shopConfigButton = (ShopConfigButton) button;
            inventory.setItem(shopConfigButton.getSlot(), shopConfigButton.getItemStack());
        } else {
            inventory.setItem(inventory.firstEmpty(), button.getItemStack());
        }
    }

    @Override
    public int getSize() {
        return shop.getSlots();
    }

    @Override
    public String getTitle() {

        return "Loja de " + shop.getShopPlayer().getPlayerName();
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = shop.getShopProductsButtons();
        buttons.add(new ShopConfigButton(this));
        return buttons;
    }

}
