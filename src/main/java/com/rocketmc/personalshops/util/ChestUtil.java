package com.rocketmc.personalshops.util;

import com.rocketmc.personalshops.shop.Shop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Chest;

public class ChestUtil {

    public static boolean placeFacedChest(Shop shop, Location location) {
        Location secondChestLocation = location.clone().subtract(1, 0, 0);
        location.getBlock().setType(Material.CHEST);
        BlockState state = location.getBlock().getState();
        //sempre mudar dependendo do spawn
        Chest chest = new Chest(BlockFace.SOUTH);
        state.setData(chest);
        state.update();


//TODO ARRUMAR BRANCH
        secondChestLocation.getBlock().setType(Material.CHEST);
        BlockState state1 = secondChestLocation.getBlock().getState();
        Chest chest1 = new Chest(BlockFace.SOUTH);
        state1.setData(chest1);
        state1.update();

        shop.setChest1(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        shop.setChest2(new Location(secondChestLocation.getWorld(), secondChestLocation.getBlockX(), secondChestLocation.getBlockY(), secondChestLocation.getBlockZ()));
        return true;
    }

    public static boolean isBlocksEmpty(Location location) {
        location = location.clone().add(0, 1, 0);
        return (location.getBlock().getType() == Material.AIR &&
                location.clone().subtract(1, 0, 0).getBlock().getType() == Material.AIR
                && location.clone().add(1, 0, 0).getBlock().getType() == Material.AIR);

    }

}
