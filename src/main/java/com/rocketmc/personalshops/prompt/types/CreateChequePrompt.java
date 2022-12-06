package com.rocketmc.personalshops.prompt.types;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.Prompt;
import com.rocketmc.personalshops.util.StringUtil;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class CreateChequePrompt extends Prompt {


    public CreateChequePrompt(ShopPlayer shopPlayer) {
        super(shopPlayer);
    }


    public void onStart() {
        shopPlayer.getPlayer().sendMessage(f("&7Digite o valor do cheque a ser assinado"));
    }

    public void onCancel() {
        shopPlayer.getPlayer().sendMessage(f("&cAssinatura de cheque cancelada"));
    }

    public void onFailure(int failureCode) {
        if (failureCode == 0) {
            shopPlayer.getPlayer().sendMessage(f("&cValor para cheque invalido!"));
        } else {
            shopPlayer.getPlayer().sendMessage(f("&cSaldo insuficiente!"));
        }
    }

    public void onSuccess() {
        shopPlayer.getPlayer().sendMessage(f("&aCheque de valor $" + response + " assinado com sucesso!"));
        shopPlayer.getBank().createCheque(Double.valueOf(response));
    }

    public void onReceiveResponse() {
        if (StringUtil.isDouble(response) && Double.valueOf(response) > 0) {
            if (PersonalShops.getInstance().getEconomy().has(shopPlayer.getPlayer(), Double.valueOf(response))) {
                onSuccess();
            } else {
                onFailure(1);
            }
        } else {
            onFailure(0);
        }
        stop();
    }
}
