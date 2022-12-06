package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.types.CreateChequePrompt;
import com.rocketmc.personalshops.util.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class CreateChequeButton extends Button {

    public CreateChequeButton() {
        super(ItemBuilder.build(f("&aAssinar Cheque"),
                339,
                (byte) 0,
                Arrays.asList(f("&7Clique aqui para assinar um"), f("&7cheque com o valor desejado")),
                false,
                1), 2);
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        shopPlayer.getPlayer().closeInventory();
        PersonalShops.getInstance().getPromptManager().startPrompt(new CreateChequePrompt(shopPlayer));

    }
}
