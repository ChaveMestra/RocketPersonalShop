package com.rocketmc.personalshops.command;

import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.types.BankServicesGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                if (args[0].equals("openbank") && Bukkit.getPlayer(args[1]) != null) {
                    Player player = Bukkit.getPlayer(args[1]);
                    ShopPlayer shopPlayer = PersonalShops.getInstance().getShopPlayerManager().getShopPlayer(player);
                    PersonalShops.getInstance().getGuiManager().startGUI(
                            shopPlayer,
                            new BankServicesGUI(shopPlayer)
                    );
                }
            }
        }


        return false;
    }
}
