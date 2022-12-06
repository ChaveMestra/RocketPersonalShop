package com.rocketmc.personalshops.gui.button;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public abstract class Button implements ButtonMechanics {

    public ItemStack itemStack;
    int slot;

    public Button(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}
