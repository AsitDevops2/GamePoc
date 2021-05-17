package com.game.service;

import java.util.Map;
import com.game.model.Player;

public interface GamePointsi {
	
	public void addPlayer(Player player);
	
	public void removePlayer(String playerName);
	
	public Map<String, Integer> playerList();
	
	public boolean playerExist(String playerName);
	
	public int myPoints(String playerName);
	
	public String startGame(String playerName);
	
	public Map<String, Integer> finalResult();

}
