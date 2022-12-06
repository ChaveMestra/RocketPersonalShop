package com.rocketmc.personalshops.gui;

import com.rocketmc.personalshops.gui.button.Button;

import java.util.List;

public interface GUIMechanics {

    void build();

    int getSize();

    String getTitle();

    List<Button> getButtons();

}
