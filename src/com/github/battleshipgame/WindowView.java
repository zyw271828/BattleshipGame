package com.github.battleshipgame;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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

public class WindowView {

	private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private static int gridLength = GameController.gridLength;
	
	private JFrame frame;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		alphabet = alphabet.substring(0, gridLength);
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
		initialize();
	}

	private void initialize() {		
		GameController game = new GameController();
		game.setUpGame();
		
		frame = new JFrame();
		frame.setTitle("战舰游戏");
		frame.setBounds(100, 100, gridLength * 50 + 230, gridLength * 50 + 90);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
//		// 显示启动画面
//		JLabel bootLabel = new JLabel("战舰游戏");
//		bootLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		bootLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//		bootLabel.setBackground(new Color(0, 57, 92));
//		bootLabel.setForeground(Color.white);
//		bootLabel.setFont(new Font("Dialog", Font.PLAIN, 70));
//		frame.getContentPane().add(bootLabel);
//		bootLabel.setVisible(true);
//		try {
//			Thread.currentThread();
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
//		bootLabel.setVisible(false);
		// 播放 Ocean_Sea.wav
		playSound("resources/audio/Ocean_Sea.wav");
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 30, gridLength * 50, gridLength * 50);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(gridLength, gridLength, 0, 0));
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 18));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(gridLength * 50 + 50, 30, 150, gridLength * 50);
		frame.getContentPane().add(textArea);
		textArea.setColumns(10);
		textArea.setText(game.getDescription());
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(textArea.getBounds());
		frame.getContentPane().add(scrollPane);

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
								playSound("resources/audio/Water_splashes.wav");
							} 
							if (result.equals("Hit")) {
								label.setIcon(new ImageIcon("resources/image/Fire.gif", "Wreckage"));
								textArea.append("\n" + game.getNumberOfGuesses() + ": 击中目标！");
								// 播放 Explosion.wav
								playSound("resources/audio/Explosion.wav");
							}
							if (result.equals("Kill")) {
								label.setIcon(new ImageIcon("resources/image/Fire.gif", "Wreckage"));
								textArea.append("\n" + game.getNumberOfGuesses() + ": 击沉目标！");
								textArea.append("\n" + game.getDescription());
								// 播放 Explosion.wav
								playSound("resources/audio/Explosion.wav");
							}
							if (game.getBattleshipsList().isEmpty()) {
								// 所有战舰均被击沉
								game.finishGame();
								textArea.append("\n" + game.getDescription());
								// 播放 Win.wav
								playSound("resources/audio/Win.wav");
								int dialogResult = JOptionPane.showConfirmDialog(panel, game.getDescription() + "\n是否重新开始？", "游戏结束", JOptionPane.YES_NO_OPTION);
								if (dialogResult == JOptionPane.YES_OPTION) {
									// 重新开始
									frame.setVisible(false);
									frame.dispose();
									try {
										WindowView window = new WindowView();
										window.frame.setVisible(true);
									} catch (Exception e2) {
										e2.printStackTrace();
									}
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
	public void playSound(String url) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
}
