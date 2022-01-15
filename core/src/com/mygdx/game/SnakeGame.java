package com.mygdx.game;

import com.badlogic.gdx.Game;

import screen.GameScreen;

public class SnakeGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}


