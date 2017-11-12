package com.github.battleshipgame;

import java.util.ArrayList;

public class GameController {
	private GameHelper helper = new GameHelper();
	private ArrayList<Battleship> battleshipsList = new ArrayList<>();
	private String description = "游戏开始！\n请选择投弹地点";
	private int numberOfGuesses = 0;
	
	public static final int gridLength = 7;
	private final String[] NAMELIST = {"施佩伯爵海军上将号", "亚利桑那号",
			 							"俾斯麦号", "密苏里号", "大和号"};
	private final int[] SIZELIST = {2, 2, 3, 3, 4};

	
	public void setUpGame() {
		// 将 NAMELIST 中的战舰添加到 battleshipsList
		for (int i = 0; i < NAMELIST.length; i++) {
			Battleship bs = new Battleship(NAMELIST[i], SIZELIST[i]);
			battleshipsList.add(bs);
		}
		// 获取每个战舰的位置
		for (Battleship bs : battleshipsList) {
			bs.setLocationCells(helper.placeBattleship(bs.getSize()));
		}
	}
	
	public void startPlaying() {
		while(!battleshipsList.isEmpty()) {
			// 还有战舰未被击沉
			String userGuess = helper.getUserInput("请选择投弹地点: ");
			System.out.println(checkUserGuess(userGuess));
		}
		// 所有战舰均被击沉
		finishGame();
	}
	
	public String checkUserGuess(String userGuess) {
		numberOfGuesses++;
		String result = "Miss";
		// 检查每个战舰状态
		for (Battleship bs : battleshipsList) {
			result = bs.checkResult(userGuess);
			if (result.equals("Hit")) {
				// 被击中
				break;
			}
			if (result.equals("Kill")) {
				// 被击沉
				description = bs.getName() + " 已沉没";
				battleshipsList.remove(bs);
				break;
			}
		}
		return result;
	}
	
	public void finishGame() {
		description = "游戏结束\n共投弹 " + numberOfGuesses + " 次";
		System.out.println(description);
	}
	
	public ArrayList<Battleship> getBattleshipsList() {
		return battleshipsList;
	}
	
	public int getNumberOfGuesses() {
		// 获取玩家投弹次数
		return numberOfGuesses;
	}
	
	public int getGridLength() {
		// 获取游戏地图大小
		return gridLength;
	}
	
	public String getDescription() {
		// 获取当前的游戏描述信息
		return description;
	}
	
	public static void main(String[] args) {
		GameController game = new GameController();
		game.setUpGame();
		game.startPlaying();
	}
}
