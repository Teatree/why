package com.me.swampmonster.utils;

import com.badlogic.gdx.Game;
import com.me.swampmonster.screens.MenuScreen;
import com.me.swampmonster.screens.SlotMachineScreen;
import com.me.swampmonster.screens.SwampScreen;

public class ScreenContainer {

	public static Game game;
	public static SwampScreen SS = new SwampScreen(game);
	public static SlotMachineScreen SMS = new SlotMachineScreen(game);
	public static MenuScreen MS = new MenuScreen(game);
}
