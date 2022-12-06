package com.rocketmc.personalshops.bank;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.button.types.BinRecoverItemButton;
import com.rocketmc.personalshops.player.ShopPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Bank {

    public ShopPlayer shopPlayer;
    public Inventory bankInventory;
    public List<ItemStack> binContent;

    public Bank(ShopPlayer shopPlayer) {
        this.shopPlayer = shopPlayer;
        bankInventory = Bukkit.createInventory(null, shopPlayer.getBankSlots(), "Armazem de " + shopPlayer.getPlayerName());
        binContent = new ArrayList<>();
    }

    public Inventory getBinInventory() {
        Inventory inventory = Bukkit.createInventory(null, shopPlayer.getShopSlots(), "Bin");
        binContent.stream().filter(Objects::nonNull).forEach(inventory::addItem);
        return inventory;
    }

    public List<Button> getBinItemsButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        for (ItemStack itemStack : binContent) {
            buttons.add(new BinRecoverItemButton(itemStack));
        }
        return buttons;
    }


    public void createCheque(double value) {
        PersonalShops.getInstance().getBankManager().criarCheque(shopPlayer.getPlayer(), value);
    }
}
