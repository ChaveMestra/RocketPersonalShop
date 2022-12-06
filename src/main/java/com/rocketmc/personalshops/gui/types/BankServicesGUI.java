package com.rocketmc.personalshops.gui.types;

import com.rocketmc.personalshops.gui.GUI;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.button.types.CreateChequeButton;
import com.rocketmc.personalshops.gui.button.types.OpenBankButton;
import com.rocketmc.personalshops.gui.button.types.OpenBinButton;
import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class BankServicesGUI extends GUI {


    public BankServicesGUI(ShopPlayer shopPlayer) {
        super(shopPlayer, false);
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
        return "Banco da Cidade";
    }

    @Override
    public List<Button> getButtons() {
        return Arrays.asList(new OpenBinButton(), new OpenBankButton(), new CreateChequeButton());
    }
}
