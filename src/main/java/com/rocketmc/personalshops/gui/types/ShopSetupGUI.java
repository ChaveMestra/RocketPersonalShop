package com.rocketmc.personalshops.gui.types;

import com.rocketmc.personalshops.gui.GUI;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.button.types.ChangeShopNameButton;
import com.rocketmc.personalshops.gui.button.types.ChangeShopStatusButton;
import com.rocketmc.personalshops.gui.button.types.OpenBinButton;
import com.rocketmc.personalshops.gui.button.types.RemoveShopButton;
import com.rocketmc.personalshops.shop.Shop;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class ShopSetupGUI extends GUI {


    public ShopSetupGUI(Shop shop) {
        super(shop.getShopPlayer(), false);
        build();
    }

    @Override
    public void build() {
        inventory = Bukkit.createInventory(null, getSize(), getTitle());
        getButtons().forEach(this::implementButton);
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public String getTitle() {
        return "Configurar Loja";
    }

    @Override
    public List<Button> getButtons() {
        return Arrays.asList(new ChangeShopNameButton(), new ChangeShopStatusButton(getShopPlayer().getShop())
                , new OpenBinButton(),
                new RemoveShopButton());
    }
}
