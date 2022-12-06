package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class BinRecoverItemButton extends Button {

    public BinRecoverItemButton(ItemStack itemStack) {
        super(itemStack);
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        if (shopPlayer.getPlayer().getInventory().firstEmpty() == -1) {
            shopPlayer.getPlayer().sendMessage(f("&cInventario cheio!"));
            return;
        }
        shopPlayer.getBank().getBinContent().remove(itemStack);
        PersonalShops.getInstance().getGuiManager().getPlayerGUI(shopPlayer).refresh();
        shopPlayer.getPlayer().getInventory().addItem(itemStack);


    }
}
