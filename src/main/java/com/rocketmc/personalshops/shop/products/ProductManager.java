package com.rocketmc.personalshops.shop.products;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.rocketmc.personalshops.util.StringUtil.decimalFormat;
import static com.rocketmc.personalshops.util.StringUtil.f;

public class ProductManager {


    public ItemStack buildDefaultItemStack(ItemStack itemStack, double price) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore;
        if (meta.hasLore()) {
            lore = meta.getLore();
        } else {
            lore = new ArrayList<>();
        }
        lore.add(f("&e&lPRECO: &a" + decimalFormat.format(price) + "&l$ &8/ &7unidade"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
