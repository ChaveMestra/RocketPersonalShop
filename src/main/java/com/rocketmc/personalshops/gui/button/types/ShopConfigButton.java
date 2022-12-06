package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.types.ShopGUI;
import com.rocketmc.personalshops.gui.types.ShopSetupGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.util.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class ShopConfigButton extends Button {

    public ShopGUI shopGUI;

    public ShopConfigButton(ShopGUI shopGUI) {
        super(ItemBuilder.build(f("&eConfigurar Loja"),
                386,
                (byte) 0,
                Arrays.asList(f("&7Clique para editar opcoes"), f("&7da sua lojinha :)")),
                false,
                1), shopGUI.getConfigSlot());
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        shopPlayer.getPlayer().closeInventory();
        if (shopPlayer.getShop() == null) return;
        PersonalShops.getInstance().getGuiManager().switchGUI(shopPlayer, new ShopSetupGUI(shopPlayer.getShop()));

    }
}
