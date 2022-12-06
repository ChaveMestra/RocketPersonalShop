package com.rocketmc.personalshops.shop;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.types.BankServicesGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.util.Debug;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null)
            return;

        if (e.getClickedBlock().getType() == Material.ENDER_CHEST && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            System.out.println(e.getAction().toString());
            e.setCancelled(true);
            System.out.println("ABRINDO O MALDITO BAU");
            ShopPlayer shopPlayer = PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(e.getPlayer());
            PersonalShops.getInstance().getGuiManager().startGUI(shopPlayer, new BankServicesGUI(shopPlayer));
            return;
        }

        if (e.getPlayer().isSneaking() && e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!PersonalShops.getInstance().getShopManager().canPlaceShop(e.getPlayer(), e.getClickedBlock()))
                return;
            e.setCancelled(true);
            PersonalShops.getInstance().getShopManager().createShop(e.getPlayer(), e.getClickedBlock());
            return;
        }

        if (e.getClickedBlock().getType() == Material.CHEST) {
            Shop shop = PersonalShops.getInstance().getShopManager().getShop(e.getClickedBlock().getLocation());

            if (shop != null) {
                e.setCancelled(true);
                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    return;
                }
                Debug.print("achei o shop do player " + shop.shopPlayer.getPlayerName());
                shop.open(PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(e.getPlayer()));
            } else {
                Debug.print("nao achei shop aki :=(");
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (PersonalShops.getInstance().getShopManager().getShop(e.getBlock().getLocation()) == null)
            return;
        e.setCancelled(true);
    }

}
