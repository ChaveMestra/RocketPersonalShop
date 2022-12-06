package com.rocketmc.personalshops.gui.button.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.types.BuyQuantityPrompt;
import com.rocketmc.personalshops.shop.Shop;
import com.rocketmc.personalshops.shop.products.Product;
import lombok.Getter;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import static com.rocketmc.personalshops.util.StringUtil.f;

@Getter
public class ShopProductButton extends Button {

    public Product product;

    public ShopProductButton(Product product) {
        super(product.toItemStack());
        this.product = product;
    }


    @Override
    public void onClick(ShopPlayer shopPlayer, InventoryClickEvent event) {
        if (event.getClick() == ClickType.SHIFT_LEFT && shopPlayer.getShop() != null && shopPlayer.getShop().equals(product.shop)) {
            if (shopPlayer.getPlayer().getInventory().firstEmpty() == -1) {
                shopPlayer.getPlayer().sendMessage(f("&cInventario cheio!"));
                return;

            }
            shopPlayer.getShop().unregisterProduct(product);
            shopPlayer.getPlayer().sendMessage(f("&cVoce desregistrou o item da lojinha!"));
            return;
        }
        shopPlayer.getPlayer().closeInventory();
        if (product.getShop().getShopStatus() == Shop.ShopStatus.CLOSED) {
            shopPlayer.getPlayer().sendMessage(f("&cEsta lojinha esta fechada por enquanto!"));
            return;
        }
        PersonalShops.getInstance().getPromptManager().startPrompt(new BuyQuantityPrompt(shopPlayer, product));

    }
}
