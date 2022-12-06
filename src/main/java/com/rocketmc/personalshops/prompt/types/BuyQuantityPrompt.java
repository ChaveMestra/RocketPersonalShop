package com.rocketmc.personalshops.prompt.types;

import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.Prompt;
import com.rocketmc.personalshops.shop.products.Product;
import com.rocketmc.personalshops.util.StringUtil;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class BuyQuantityPrompt extends Prompt {

    private Product product;

    public BuyQuantityPrompt(ShopPlayer shopPlayer, Product product) {
        super(shopPlayer);
        this.product = product;
    }


    public void onStart() {
        shopPlayer.getPlayer().sendMessage(f("&7Digite a quantidade que deseja comprar"));
    }

    public void onCancel() {
        shopPlayer.getPlayer().sendMessage(f("&cCompra de produto cancelada"));
    }

    public void onFailure(int failureCode) {
        shopPlayer.getPlayer().sendMessage(f("&cQuantidade invalida!"));
    }

    public void onSuccess() {
        product.getShop().sellProduct(product, Integer.valueOf(response), shopPlayer);
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
