package com.rocketmc.personalshops.command;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.types.BankServicesGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("personalshop.cmd")) {
            ShopPlayer shopPlayer = PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(player);
            PersonalShops.getInstance().getGuiManager().startGUI(shopPlayer, new BankServicesGUI(shopPlayer));

            return true;
        }
        return false;
    }
}
