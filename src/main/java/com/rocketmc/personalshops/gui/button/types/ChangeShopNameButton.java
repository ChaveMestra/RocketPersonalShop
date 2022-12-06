package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.types.ShopTitlePrompt;
import com.rocketmc.personalshops.util.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static com.rocketmc.personalshops.util.StringUtil.f;


public class ChangeShopNameButton extends Button {

    public ChangeShopNameButton() {
        super(ItemBuilder.build(f("&bAlterar Titulo"),
                421,
                (byte) 0,
                Arrays.asList(f("&7Clique aqui para alterar o"), f("&7titulo de sua lojinha")),
                false,
                1), 2);
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        PersonalShops.getInstance().getPromptManager().startPrompt(new ShopTitlePrompt(shopPlayer));

    }
}
