package com.rocketmc.personalshops.prompt.types;


import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.Prompt;

import static com.rocketmc.personalshops.util.StringUtil.f;


public class ShopTitlePrompt extends Prompt {


    public ShopTitlePrompt(ShopPlayer shopPlayer) {
        super(shopPlayer);
    }


    public void onStart() {
        shopPlayer.getPlayer().closeInventory();
        shopPlayer.getPlayer().sendMessage(f("&7Digite o titulo da sua lojinha (VIP pode usar cor)"));
    }

    public void onCancel() {
        shopPlayer.getPlayer().sendMessage(f("&cAlteracao de titulo da lojinha cancelado!"));
    }

    public void onFailure(int failureCode) {
        shopPlayer.getPlayer().sendMessage(f("&cTitulo da lojinha invalido! Processo cancelado"));
    }

    public void onSuccess() {
        shopPlayer.getPlayer().sendMessage(f("&aTitulo da lojinha alterado com sucesso!"));
        shopPlayer.getShop().changeTitle(response);
    }

    public void onReceiveResponse() {
        if (response.length() >= 20) {
            onFailure(0);
        } else {
            onSuccess();
        }
        stop();
    }
}
