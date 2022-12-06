package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.types.BinGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.util.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class OpenBinButton extends Button {


    public OpenBinButton() {
        super(ItemBuilder.build(f(("&eAbrir Deposito")),
                54,
                (byte) 0,
                Arrays.asList(f("&7Clique aqui para abrir o deposito"),
                        f("&fDeposito fica seus itens"),
                        f("&fnao vendidos ficarao armazenados"),
                        f("&fpara futura coleta.")),
                false,
                1), 4);
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        PersonalShops.getInstance().getGuiManager().switchGUI(shopPlayer, new BinGUI(shopPlayer));

    }
}
