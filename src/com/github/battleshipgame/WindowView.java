package com.github.battleshipgame;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class WindowView {

	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private int gridLength = GameController.gridLength;
	private static boolean muted = false;
	
	private JFrame frame;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowView window = new WindowView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WindowView() {
		alphabet = alphabet.substring(0, gridLength);
		initialize();
		frame.setLocationRelativeTo(null);
	}

	private void initialize() {		
		GameController game = new GameController();
		game.setUpGame();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setTitle("战舰游戏");
		frame.setBounds(100, 100, gridLength * 50 + 230, gridLength * 50 + 90 + 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		// 播放 Ocean_Sea.wav
		playSound("resources/audio/Ocean_Sea.wav", true);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(30, 30, gridLength * 50, gridLength * 50);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(gridLength, gridLength, 0, 0));
		
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(gridLength * 50 + 50, 30, 150, gridLength * 50);
		frame.getContentPane().add(textArea);
		textArea.setColumns(10);
		textArea.setText(game.getDescription());
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(textArea.getBounds());
		frame.getContentPane().add(scrollPane);
		
		JButton difficultyButton = new JButton("难度选择");
		difficultyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 弹出窗口，设置战舰数量、地图大小
				new SettingView(frame);
			}
		});
		difficultyButton.setBackground(Color.WHITE);
		difficultyButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
		difficultyButton.setBounds(55, gridLength * 50 + 50, 120, 40);
		frame.getContentPane().add(difficultyButton);
		
		JButton replayButton = new JButton("重新开始");
		replayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 重新开始
				restart(frame);
			}
		});
		replayButton.setBackground(Color.WHITE);
		replayButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
		replayButton.setBounds(gridLength * 25 + 55, gridLength * 50 + 50, 120, 40);
		frame.getContentPane().add(replayButton);
		
		JButton muteButton = new JButton();
		if (!muted) {
			muteButton.setText("静音");
		} else {
			muteButton.setText("取消静音");
		}
		muteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!muted) {
					muteButton.setText("取消静音");
					muted = true;
				} else {
					muteButton.setText("静音");
					muted = false;
					// 重新播放 Ocean_Sea.wav
					playSound("resources/audio/Ocean_Sea.wav", true);
				}
			}
		});
		muteButton.setBackground(Color.WHITE);
		muteButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
		muteButton.setBounds(gridLength * 50 + 55, gridLength * 50 + 50, 120, 40);
		frame.getContentPane().add(muteButton);

		// 放置 JLabel
		for (int j = 0; j < alphabet.length(); j++) {
			for (int i = 0; i < alphabet.length(); i++) {
				JLabel label = new JLabel(alphabet.charAt(i) + Integer.toString(j));
				label.setIcon(new ImageIcon("resources/image/Cloud.gif", "Cloud"));
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (!((ImageIcon)label.getIcon()).getDescription().equals("Wreckage") &&
							!((ImageIcon)label.getIcon()).getDescription().equals("Sea")) {
							// 图标不是残骸或海面时
							String result = game.checkUserGuess(label.getText());
							if (result.equals("Miss")) {
								label.setIcon(new ImageIcon("resources/image/Sea.png", "Sea"));
								textArea.append("\n" + game.getNumberOfGuesses() + ": 未击中目标");
								// 播放 Water_splashes.wav
								playSound("resources/audio/Water_splashes.wav", false);
							} 
							if (result.equals("Hit")) {
								label.setIcon(new ImageIcon("resources/image/Fire.gif", "Wreckage"));
								textArea.append("\n" + game.getNumberOfGuesses() + ": 击中目标！");
								// 播放 Explosion.wav
								playSound("resources/audio/Explosion.wav", false);
							}
							if (result.equals("Kill")) {
								label.setIcon(new ImageIcon("resources/image/Fire.gif", "Wreckage"));
								textArea.append("\n" + game.getNumberOfGuesses() + ": 击沉目标！");
								textArea.append("\n" + game.getDescription());
								// 播放 Explosion.wav
								playSound("resources/audio/Explosion.wav", false);
							}
							if (game.getBattleshipsList().isEmpty()) {
								// 所有战舰均被击沉
								game.finishGame();
								textArea.append("\n" + game.getDescription());
								// 播放 Win.wav
								playSound("resources/audio/Win.wav", false);
								int dialogResult = JOptionPane.showConfirmDialog(panel, game.getDescription() + "\n是否重新开始？", "游戏结束", JOptionPane.YES_NO_OPTION);
								if (dialogResult == JOptionPane.YES_OPTION) {
									// 重新开始
									restart(frame);
								} else {
									// 退出游戏
									System.exit(0);
								}
							}
						}
						
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						// 显示准星
						if (((ImageIcon)label.getIcon()).getDescription().equals("Cloud")) {
							label.setIcon(new ImageIcon("resources/image/Cloud_pointer.gif", "CloudCursor"));
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
						// 消除准星
						if (((ImageIcon)label.getIcon()).getDescription().equals("CloudCursor")) {
							label.setIcon(new ImageIcon("resources/image/Cloud.gif", "Cloud"));
						}
					}
				});
				panel.add(label);
			}
		}
	}
	
	public void playSound(String url, boolean loop) {
		if (!muted) {
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url).getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        if (loop) {
		        	clip.loop(Clip.LOOP_CONTINUOUSLY);
		        	// 点击静音后终止背景音乐
		        	Timer timer = new Timer(true);
		        	timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							if (muted) {
								clip.stop();
							}
						}
					}, 1000, 1000);
				} else {
					clip.start();
				}
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}
	
	public static void restart(JFrame frame) {
		frame.setVisible(false);
		frame.dispose();
		try {
			WindowView window = new WindowView();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
