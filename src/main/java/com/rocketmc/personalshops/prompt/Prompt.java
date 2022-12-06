package com.rocketmc.personalshops.prompt;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.player.ShopPlayer;
import lombok.Getter;

@Getter
public abstract class Prompt implements PromptMechanics {

    public ShopPlayer shopPlayer;
    public String response = null;

    public Prompt(ShopPlayer shopPlayer) {
        this.shopPlayer = shopPlayer;
    }

    void registerResponse(String response) {
        this.response = response;
        onReceiveResponse();
    }


    public void stop() {
        PersonalShops.getInstance().getPromptManager().endPrompt(this);
    }

}
