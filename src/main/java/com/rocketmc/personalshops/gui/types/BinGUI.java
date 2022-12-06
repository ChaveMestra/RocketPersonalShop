package com.rocketmc.personalshops.gui.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.GUI;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.Bukkit;

import java.util.List;

public class BinGUI extends GUI {


    public BinGUI(ShopPlayer shopPlayer) {
        super(shopPlayer, false);
        build();
    }

    @Override
    public void beforeCloseAction() {
        PersonalShops.getInstance().getDatabaseManager().saveShopPlayer(shopPlayer);
    }

    @Override
    public void build() {
        inventory = Bukkit.createInventory(null, getSize(), getTitle());
        getButtons().forEach(this::implementButton);
    }

    @Override
    public void implementButton(Button button) {
        inventory.setItem(inventory.firstEmpty(), button.getItemStack());
    }


    @Override
    public int getSize() {
        return shopPlayer.getShopSlots();
    }

    @Override
    public String getTitle() {
        return "Deposito";
    }

    @Override
    public List<Button> getButtons() {
        return shopPlayer.getBank().getBinItemsButtons();
    }
}
