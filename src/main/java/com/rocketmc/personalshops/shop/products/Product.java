package com.rocketmc.personalshops.shop.products;

import com.rocketmc.personalshops.shop.Shop;
import com.rocketmc.personalshops.util.StringUtil;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.rocketmc.personalshops.util.StringUtil.f;

@Getter
public class Product {

    public Shop shop;
    public ItemStack itemStack;
    public int amount;
    public double price;


    public Product(Shop shop, ItemStack itemStack, double price) {
        this.shop = shop;
        this.itemStack = itemStack;
        this.amount = itemStack.getAmount();
        this.price = price;
    }

    public ItemStack toItemStack() {
        ItemStack productItemStack = itemStack.clone();
        productItemStack.setAmount(itemStack.getAmount());
        ItemMeta meta = productItemStack.getItemMeta();
        List<String> lore;
        if (meta.hasLore()) {
            lore = meta.getLore();
        } else {
            lore = new ArrayList<>();
        }
        lore.add(f("&e&lPRECO: &a" + StringUtil.format((long) price) + "&l$ &8/ &7unidade"));
        lore.add(f("&cClique para comprar"));
        lore.add(f("&7Shift+Botao Esquerdo para remover"));
        meta.setLore(lore);
        productItemStack.setItemMeta(meta);
        return productItemStack;
    }


    public int getRoundedPrice(int quantity) {
        return (int) (quantity * price);
    }


}
