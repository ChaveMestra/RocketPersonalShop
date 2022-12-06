package com.rocketmc.personalshops.shop;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.util.ChestUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class ShopManager {


    public ShopManager() {
        //inicia a task de cooldown para lojas
        new ShopTask().runTaskTimer(PersonalShops.getInstance(), 0, 20);
    }

    public void createShop(Player player, Block block) {
        ShopPlayer shopPlayer = PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(player);
        Shop shop = new Shop(shopPlayer, block);
        shopPlayer.setShop(shop);
        shop.placeShop();
        player.sendMessage(f("&aLojinha criada! Arraste itens para dentro dela para vender!"));
        player.sendMessage(f("&7Torne-se &aVIP &7e tenha uma loja muito maior!"));
    }


    public Shop getShop(Location location) {
        for (ShopPlayer shopPlayer : PersonalShops.getInstance().getShopPlayerManager().getShopPlayerList()) {
            if (shopPlayer.hasShop()) {
                if (shopPlayer.getShop().getChest1().clone().equals(location) ||
                        shopPlayer.getShop().getChest2().clone().equals(location)) {
                    return shopPlayer.getShop();
                }
            }
        }
        return null;
    }

    public boolean canPlaceShop(Player player, Block block) {
        ApplicableRegionSet applicableRegionSet = WorldGuardPlugin.inst().getRegionManager(block.getLocation().getWorld()).getApplicableRegions(block.getLocation().add(0, 1, 0));
        int priority = -1;
        String regionId = null;
        for (ProtectedRegion r : applicableRegionSet) {
            if (r.getPriority() >= priority) {
                priority = r.getPriority();
                regionId = r.getId();
            }


        }


        //se nao achar nenhuma regiao do worldguard (tem q ter)
        if (priority == -1 || regionId == null) {
            // player.sendMessage(f("&cVoce nao pode criar lojinhas aqui, apenas no spawn!"));
            return false;
        }
        //outro requisito: a regiao com maior prioridade ter shops no id

        if (!regionId.contains("shops")) {
            // player.sendMessage(f("&cVoce nao pode criar lojinhas aqui, apenas no spawn!"));
            return false;
        }
        if (!player.hasPermission("personalshops.create")) {
            player.sendMessage(f("&cVoce ainda nao pode criar lojinhas!"));
            return false;
        }

        if (!ChestUtil.isBlocksEmpty(block.getLocation())) {
            player.sendMessage(f("&cVoce nao pode criar lojinhas aqui!"));
            return false;
        }

        if (PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(player).getShop() != null) {
            player.sendMessage(f("&cParece que voce ja possui uma lojinha por ai.."));
            return false;
        }

        if (PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(player).getBank().getBinContent().size() > 0) {
            player.sendMessage(f("&cVa ate o enderchest e esvazie seu deposito antes disso!"));
            return false;
        }

        for (ShopPlayer shopPlayer : PersonalShops.getInstance().getShopPlayerManager().getShopPlayerList()) {
            if (shopPlayer.getShop() != null)
                if (shopPlayer.getShop().getChest1().getWorld().getName().equals(block.getLocation())) {
                    if (shopPlayer.getShop().getChest1().distance(block.getLocation()) <= 4) {
                        player.sendMessage(f("&cTem outra lojinha muito perto deste bloco."));
                        return false;
                    }
            }
        }
        return true;
    }

}
