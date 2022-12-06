package com.rocketmc.personalshops.bank;

import com.rocketmc.personalshops.PersonalShops;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class BankListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Inventory inv = p.getInventory();
        Action ac = e.getAction();

        if (p.getItemInHand() == null) return;
        if (!p.getItemInHand().hasItemMeta()) return;
        if (!p.getItemInHand().getItemMeta().hasDisplayName()) return;
        if (p.getItemInHand().getType() != Material.PAPER) return;
        if (!p.getItemInHand().getItemMeta().hasLore()) return;
        if (p.getItemInHand().getItemMeta().getDisplayName().contains("Cheque")) {
            double valor = PersonalShops.getInstance().getBankManager().getValorCheque(p.getItemInHand());
            //verificamos se o metodo retorna -1, o que significa que o lore nao tem Valor: x
            if (valor == -1) {
                return;
            }
            PersonalShops.getInstance().getBankManager().usarCheque(p, valor);
        }
    }
}
