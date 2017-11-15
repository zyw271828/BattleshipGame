package com.github.battleshipgame;

import java.io.*;
import java.util.ArrayList;

public class GameHelper {
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private int gridLength = GameController.gridLength;
	private int gridSize = gridLength * gridLength;
	private int[] grid = new int[gridSize];
	private int shipCount = 0;
	
	public GameHelper() {
		alphabet = alphabet.substring(0, gridLength);
	}

	public ArrayList<String> placeBattleship(int shipSize) {
		// 将战舰放置在方格内
		ArrayList<String> alphaCells = new ArrayList<String>();
		String temp = null;
		int[] coords = new int[shipSize];
		int attempts = 0;
		boolean success = false;
		int location = 0;

		shipCount++;
		int incr = 1;
		if ((shipCount % 2) == 1) {
			incr = gridLength;
		}

		while (!success & attempts++ < 200) {
			// 不断尝试放置战舰
			location = (int) (Math.random() * gridSize);
			int x = 0;
			success = true;
			while (success && x < shipSize) {
				if (grid[location] == 0) {
					// 未被占用
					coords[x++] = location;
					location += incr;
					if (location >= gridSize) {
						// 超出下边界
						success = false;
					}
					if (x > 0 & (location % gridLength == 0)) {
						// 超出右边界
						success = false;
					}
				} else {
					// 已被占用
					success = false;
				}
			}
		}
		
		// 将位置转换为字符串
		int x = 0;
		int row = 0;
		int column = 0;
		while (x < shipSize) {
			grid[coords[x]] = 1;
			row = (int) (coords[x] / gridLength);
			column = coords[x] % gridLength;
			temp = String.valueOf(alphabet.charAt(column));
			alphaCells.add(temp.concat(Integer.toString(row)));
			x++;
		}
		return alphaCells;
	}

	public String getUserInput(String prompt) {
		String inputLine = null;
		System.out.print(prompt);
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
			inputLine = is.readLine();
			if (inputLine.length() == 0)
				return null;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return inputLine.toLowerCase();
	}
}
