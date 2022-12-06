package com.rocketmc.personalshops.shop;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.Effect;
import org.bukkit.scheduler.BukkitRunnable;

public class ShopTask extends BukkitRunnable {

    @Override
    public void run() {
        for (ShopPlayer shopPlayer : PersonalShops.getInstance().getShopPlayerManager().getShopPlayerList()) {
            if (shopPlayer.hasShop()) {
                if (shopPlayer.getShop().hasEffect) {
                    shopPlayer.getShop().getChest1().getWorld().playEffect(shopPlayer.getShop().getChest1().clone().add(0, 2.28, 0.5), Effect.HAPPY_VILLAGER, 1);

                }
                if (shopPlayer.getShop().getSecondsWithoutProduct() < 300 &&
                        shopPlayer.getShop().getShopStatus() == Shop.ShopStatus.CLOSED) {
                    shopPlayer.getShop().setSecondsWithoutProduct(shopPlayer.getShop().getSecondsWithoutProduct() + 1);
                } else if (shopPlayer.getShop().getSecondsWithoutProduct() > 0 &&
                        shopPlayer.getShop().getShopStatus() == Shop.ShopStatus.OPEN) {
                    shopPlayer.getShop().setSecondsWithoutProduct(0);
                } else if (shopPlayer.getShop().getSecondsWithoutProduct() >= 300) {
                    shopPlayer.getShop().expireShop();
                }
                if (shopPlayer.getShop() != null && shopPlayer.getShop().expired()) {
                    shopPlayer.getShop().expireShop();
                }
            }
        }
    }
}
