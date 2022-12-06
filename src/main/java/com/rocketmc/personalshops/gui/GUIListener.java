package com.rocketmc.personalshops.gui;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.types.ShopGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.prompt.types.ProductPricePrompt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        ShopPlayer shopPlayer = PersonalShops.getInstance().getShopPlayerManager().getShopPlayer((Player) e.getPlayer());
        if (shopPlayer == null) return;
        GUI gui = PersonalShops.getInstance().getGuiManager().getPlayerGUI(shopPlayer);
        if (gui == null) return;
        gui.beforeCloseAction();
        PersonalShops.getInstance().getGuiManager().endPlayerGUI(shopPlayer);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        ShopPlayer shopPlayer = PersonalShops.getInstance().getShopPlayerManager().getShopPlayer((Player) e.getWhoClicked());
        if (shopPlayer == null) return;
        GUI gui = PersonalShops.getInstance().getGuiManager().getPlayerGUI(shopPlayer);
        if (gui == null) return;

        if (gui instanceof ShopGUI) {
            ShopGUI shopGUI = (ShopGUI) gui;
            if (shopGUI.getShop().getShopPlayer().getPlayerUuid().equals(shopPlayer.getPlayerUuid())) {
                if (e.getAction() == InventoryAction.PLACE_ALL && e.getClickedInventory().equals(shopGUI.getInventory())) {
                    ItemStack itemStack = e.getCursor();
                    PersonalShops.getInstance().getPromptManager().startPrompt(new ProductPricePrompt(shopPlayer, itemStack));

                    e.setCancelled(true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            e.setCursor(new ItemStack(Material.AIR));
                            e.getWhoClicked().closeInventory();
                            e.getWhoClicked().getInventory().addItem(itemStack);
                        }
                    }.runTaskLater(PersonalShops.getInstance(), 1L);
                } else if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && !e.getClickedInventory().equals(shopGUI.getInventory())) {
                    PersonalShops.getInstance().getPromptManager().startPrompt(new ProductPricePrompt(shopPlayer, e.getCurrentItem()));
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();

                }
            } else {
                e.setCancelled(true);
            }
        } else {
            if (!gui.allowClick) {
                e.setCancelled(true);
            }
        }
    }


}
