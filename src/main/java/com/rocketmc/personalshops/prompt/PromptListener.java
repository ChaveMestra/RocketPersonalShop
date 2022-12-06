package com.rocketmc.personalshops.prompt;

import com.rocketmc.personalshops.PersonalShops;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class PromptListener implements Listener {

    @EventHandler
    public void onChatResponse(AsyncPlayerChatEvent e) {
        final Prompt prompt = PersonalShops.getInstance().getPromptManager().getPlayerPrompt(e.getPlayer());
        final String response = e.getMessage();
        if (prompt == null) return;
        e.setCancelled(true);

        //voltando pra sync
        new BukkitRunnable() {
            @Override
            public void run() {
                prompt.registerResponse(response);
            }
        }.runTask(PersonalShops.getInstance());

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        final Prompt prompt = PersonalShops.getInstance().getPromptManager().getPlayerPrompt((Player) e.getWhoClicked());
        if (prompt == null) return;
        e.setCancelled(true);
        e.getWhoClicked().closeInventory();
        e.getWhoClicked().sendMessage(f("&cDigite &fcancelar&c para sair do processo de chat antes!"));
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        final Prompt prompt = PersonalShops.getInstance().getPromptManager().getPlayerPrompt(e.getPlayer());
        if (prompt == null) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage(f("&cDigite &fcancelar&c para sair do processo de chat antes!"));
    }
}
