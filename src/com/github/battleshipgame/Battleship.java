package com.github.battleshipgame;

import java.util.ArrayList;

public class Battleship {
	private ArrayList<String> locationCells;
	private String name;
	private int size;
	
	public Battleship(String name, int size) {
		this.name = name;
		this.size = size;
	}
	
	public void setLocationCells(ArrayList<String> loc) {
		this.locationCells = loc;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public String checkResult(String userInput) {
		String result = "Miss";
		int index = locationCells.indexOf(userInput);
		if (index >= 0) {
			// 被击中
			locationCells.remove(index);
			if (locationCells.isEmpty()) {
				// 被击沉
				result = "Kill";
				System.out.println(name + " 已沉没");
			} else {
				result = "Hit";
			}
		}
		return result;
	}
}
