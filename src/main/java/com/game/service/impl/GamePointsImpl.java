package com.game.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.game.model.Player;
import com.game.service.GamePointsi;

@Service
public class GamePointsImpl implements GamePointsi{
	
	private Map<String, Integer> playersPoint=new HashMap<>();

	@Override
	public synchronized void addPlayer(Player player) {
		playersPoint.put(player.getName(), player.getPoints());
	}

	@Override
	public synchronized void removePlayer(String playerName) {
		playersPoint.remove(playerName);
	}

	@Override
	public Map<String, Integer> playerList() {
		return playersPoint;
	}

	@Override
	public synchronized boolean playerExist(String playerName) {
		return playersPoint.containsKey(playerName);
	}

	@Override
	public synchronized int myPoints(String playerName) {
		return playersPoint.get(playerName);
	}
	
	public synchronized String gainPoints(String hittingPlayer) {
		String hittedTo=generateRandomKey();
		
		while(hittedTo.equals(hittingPlayer))
			hittedTo=generateRandomKey();
		
		reducePoints(hittedTo);
		addPoints(hittingPlayer);
		return hittedTo;
	}
	
	public synchronized String addPoints(String playerName) {
		playersPoint.put(playerName, playersPoint.get(playerName)+10);
		return playerName;
	}
	
	public synchronized String reducePoints(String playerName) {
		playersPoint.put(playerName, playersPoint.get(playerName)-10);
		return playerName;
	}

	@Override
	public synchronized String startGame(String playerName) {
		String hittedTo=gainPoints(playerName);
		
		return "Player "+playerName+" hitted to player "+hittedTo;
	}
	
	public synchronized String generateRandomKey() {
		Set<String> keySet=playersPoint.keySet();
		List<String> keyList=new ArrayList<>(keySet);
		
		int randomIndex=new Random().nextInt(keyList.size());
		
		return keyList.get(randomIndex);
	}

	@Override
	public Map<String, Integer> finalResult() {
		Map<String, Integer> winners=new HashMap<>();
		
		if(playersPoint.isEmpty() || playersPoint.size()<=1)
			return winners;
		
		int maxScore=Collections.max(playersPoint.values());
		
		playersPoint.forEach((playerName,playerPoints) ->{
			if(playerPoints.equals(maxScore))
				winners.put(playerName, playerPoints);
		});
		
		return winners;
	}

}
