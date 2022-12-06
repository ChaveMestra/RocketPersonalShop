package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.util.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class RemoveShopButton extends Button {

    public RemoveShopButton() {
        super(ItemBuilder.build(f(("&cRemover Loja")),
                166,
                (byte) 0,
                Collections.singletonList(f("&7Clique para remover sua loja")),
                false,
                1), 8);
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        shopPlayer.getPlayer().closeInventory();
        shopPlayer.getShop().removeShop();
    }
}
