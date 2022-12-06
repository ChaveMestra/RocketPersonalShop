package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.types.BankGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.util.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class OpenBankButton extends Button {


    public OpenBankButton() {
        super(ItemBuilder.build(f(("&eAbrir Armazem")),
                130,
                (byte) 0,
                Arrays.asList(f("&7Clique aqui para abrir seu armazem"),
                        f("&fVIPs tem tamanhos maiores &8(&e/cash&8)")),
                false,
                1), 0);
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        PersonalShops.getInstance().getGuiManager().switchGUI(shopPlayer, new BankGUI(shopPlayer));

    }
}
