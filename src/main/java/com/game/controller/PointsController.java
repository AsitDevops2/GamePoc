package com.game.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.model.Player;
import com.game.service.GamePointsi;

@RestController
@RequestMapping("/game")
public class PointsController {
	
	@Autowired
	private GamePointsi gamePoints;
	
	private static final String NOT_REGISTERED="Sorry you are not registered! Please register first";
	
	@PostMapping("/register")
	public String register(@RequestBody Player player) {
		if (gamePoints.playerExist(player.getName()))
			return "Player with this name already exist";
		
		gamePoints.addPlayer(player);
		return "You are registerd successfully! You have "+gamePoints.myPoints(player.getName())+ " points initially."
				+ "\nOther players present in game are"+gamePoints.playerList();
	}
	
	@GetMapping("/quit/{playerName}")
	public String quit(@PathVariable String playerName) {
		if(gamePoints.playerExist(playerName)) {
			gamePoints.removePlayer(playerName);
			return "Player "+playerName+" quit the game";
		}
		
		return NOT_REGISTERED+"\nOr may be you are already quitted the game!";
			
	}
	
	@GetMapping("/playerList")
	public Object playerList() {
		Map<String, Integer> playerList=gamePoints.playerList();
		if(playerList.isEmpty())
			return "No players added yet!";
		return playerList;
	}
	
	@GetMapping("/myPoints/{name}")
	public String myPoints(@PathVariable String name) {
		if(gamePoints.playerExist(name))
			return "Your Points are "+gamePoints.myPoints(name);
		
		return NOT_REGISTERED;
	}
	
	@GetMapping("/startGame/{playerName}")
	public String startGame(@PathVariable String playerName) {
		if(!gamePoints.playerExist(playerName))
			return NOT_REGISTERED;
		if(gamePoints.playerList().isEmpty() || gamePoints.playerList().size()<=1)
			return "Cannot start the game!\nMore than 1 players needed!";
		
		return gamePoints.startGame(playerName);
	}
	
	@GetMapping("/finalResult")
	public String finalResult() {
		Map<String, Integer> winners=gamePoints.finalResult();
		if(winners.isEmpty())
			return "More than one players needed to start the game";
		
		Map.Entry<String,Integer> entry=winners.entrySet().iterator().next();
		
		if(winners.size()==1)
			return "Player "+entry.getKey()+" wins the game by "+entry.getValue()+" points"
					+"\nFinal scores are\n"+gamePoints.playerList();
		
		return "The game is tie between following players \n"+winners;
	}

}
