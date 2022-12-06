package com.rocketmc.personalshops.gui.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.GUI;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;

import java.util.ArrayList;
import java.util.List;

public class BankGUI extends GUI {


    public BankGUI(ShopPlayer shopPlayer) {
        super(shopPlayer, true);
        build();
    }


    @Override
    public void build() {
        inventory = shopPlayer.getBank().getBankInventory();
        getButtons().forEach(this::implementButton);
    }

    @Override
    public void beforeCloseAction() {
        PersonalShops.getInstance().getDatabaseManager().saveShopPlayer(shopPlayer);
    }


    @Override
    public int getSize() {
        return shopPlayer.getBankSlots();
    }

    @Override
    public String getTitle() {
        return "Banco de " + shopPlayer.getPlayerName();
    }

    @Override
    public List<Button> getButtons() {
        return new ArrayList<>();
    }
}
