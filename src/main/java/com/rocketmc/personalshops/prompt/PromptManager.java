package com.rocketmc.personalshops.prompt;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PromptManager {

    private List<Prompt> activePrompts;

    public PromptManager() {
        activePrompts = new ArrayList<>();
    }

    public void startPrompt(Prompt prompt) {
        if (getPlayerPrompt(prompt.getShopPlayer().getPlayer()) != null) {
            endPrompt(getPlayerPrompt(prompt.getShopPlayer().getPlayer()));
        }
        activePrompts.add(prompt);
        prompt.onStart();
    }

    public void endPrompt(Prompt prompt) {
        activePrompts.remove(prompt);
    }

    public Prompt getPlayerPrompt(Player player) {
        for (Prompt prompt : activePrompts) {
            if (prompt.getShopPlayer().getPlayer().equals(player)) {
                return prompt;
            }
        }
        return null;
    }
}
