package com.rocketmc.personalshops.gui;


import com.rocketmc.personalshops.player.ShopPlayer;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class GUIManager {
    private List<GUI> activeGUIs;


    public GUIManager() {
        activeGUIs = new ArrayList<>();
    }

    public void startGUI(ShopPlayer shopPlayer, GUI gui) {
        System.out.println("debug 1");
        if (getPlayerGUI(shopPlayer) != null) return;
        System.out.println("debug 2");
        activeGUIs.add(gui);
        gui.open();
    }

    public void endPlayerGUI(ShopPlayer shopPlayer) {
        System.out.println("removendo gui do shopplayer " + shopPlayer.getPlayerName());
        GUI gui = getPlayerGUI(shopPlayer);
        activeGUIs.remove(gui);
    }

    public GUI getPlayerGUI(ShopPlayer shopPlayer) {
        for (GUI gui : activeGUIs) {
            if (gui.getShopPlayer().equals(shopPlayer)) {
                return gui;
            }
        }
        return null;
    }

    public void switchGUI(ShopPlayer shopPlayer, GUI nextGUI) {
        shopPlayer.getPlayer().closeInventory();
        startGUI(shopPlayer, nextGUI);
    }


    public void refreshPlayerGUI(ShopPlayer shopPlayer) {
        if (getPlayerGUI(shopPlayer) == null) return;
        getPlayerGUI(shopPlayer).refresh();

    }

    public void refreshGUIs() {
        activeGUIs.forEach(GUI::refresh);
    }
}
