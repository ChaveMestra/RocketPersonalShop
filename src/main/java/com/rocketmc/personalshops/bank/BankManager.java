package com.rocketmc.personalshops.bank;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.rocketmc.personalshops.util.StringUtil.f;

public class BankManager {
//
//    public Bank loadBank(String dbQuery) {
//        return new Bank();
//    }

    public void criarCheque(Player p, double valor) {
        if (!PersonalShops.getInstance().getEconomy().has(p, valor)) {
            p.sendMessage("§cSaldo insuficiente");
            return;
        }
        if (p.getInventory().firstEmpty() == -1) {
            p.sendMessage(f("&cInventário cheio!"));
            return;
        }
        Inventory inv = p.getInventory();
        ArrayList<String> lore = new ArrayList<>();
        ItemStack cheque = new ItemStack(Material.PAPER);
        ItemMeta cmeta = cheque.getItemMeta();
        cmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Cheque"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Valor: &a") + valor);
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Assinado por: &f") + p.getName());
        cmeta.setLore(lore);
        cheque.setItemMeta(cmeta);
        PersonalShops.getInstance().getEconomy().withdrawPlayer(p, valor);
        inv.addItem(cheque);
    }

    public double getValorCheque(ItemStack itemStack) {
        List<String> lore = itemStack.getItemMeta().getLore();
        double valor = -1;
        for (String linha : lore) {
            if (linha.contains("Valor: ")) {
                valor = Double.parseDouble(ChatColor.stripColor(linha).replace("Valor: ", ""));
            }
        }
        return valor;
    }

    public void usarCheque(Player player, double valor) {
        if (player.getItemInHand().getAmount() == 1) {
            player.setItemInHand(new ItemStack(Material.AIR, 1));
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        PersonalShops.getInstance().getEconomy().depositPlayer(player, valor);
        player.sendMessage(f("&a&l+ &a" + StringUtil.decimalFormat.format(valor) + "&l$"));
    }


}
