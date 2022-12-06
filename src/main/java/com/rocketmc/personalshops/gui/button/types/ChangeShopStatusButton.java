package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.shop.Shop;
import com.rocketmc.personalshops.util.ItemBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class ChangeShopStatusButton extends Button {

    public ChangeShopStatusButton(Shop shop) {
        super(ItemBuilder.build(f((shop.getShopStatus() == Shop.ShopStatus.CLOSED ? "&aAbrir loja" : "&cFechar loja")),
                131,
                (byte) 0,
                Arrays.asList(f("&7Clique aqui para alterar"), f("&7o status de sua loja")),
                (shop.getShopStatus() == Shop.ShopStatus.CLOSED),
                1), 0);
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        shopPlayer.getShop().switchStatus();
        PersonalShops.getInstance().getGuiManager().refreshPlayerGUI(shopPlayer);

    }
}
