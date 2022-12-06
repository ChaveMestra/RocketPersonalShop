package com.rocketmc.personalshops.prompt.types;

import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.Prompt;
import com.rocketmc.personalshops.shop.products.Product;
import com.rocketmc.personalshops.util.StringUtil;
import org.bukkit.inventory.ItemStack;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class ProductPricePrompt extends Prompt {

    private ItemStack toBeAdded;

    public ProductPricePrompt(ShopPlayer shopPlayer, ItemStack toBeAdded) {
        super(shopPlayer);
        this.toBeAdded = toBeAdded;
    }


    public void onStart() {
        shopPlayer.getPlayer().sendMessage(f("&7Digite o preco por unidade deste produto"));
    }

    public void onCancel() {
        shopPlayer.getPlayer().sendMessage(f("&cRegistro de produto para a lojinha cancelado"));
    }

    public void onFailure(int failureCode) {
        shopPlayer.getPlayer().sendMessage(f("&cPreco para produto invalido! (Exemplo: 2.3 ou 2000)"));
    }

    public void onSuccess() {
        shopPlayer.getShop().registerProduct(new Product(shopPlayer.getShop(), toBeAdded, Double.valueOf(response)));
    }

    public void onReceiveResponse() {
        if (StringUtil.isDouble(response) && Double.valueOf(response) > 0) {
            onSuccess();
        } else {
            onFailure(0);
        }
        stop();
    }
}
