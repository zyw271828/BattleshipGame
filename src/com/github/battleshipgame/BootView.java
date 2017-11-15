package com.github.battleshipgame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BootView {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BootView window = new BootView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BootView() {
		initialize();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(0, 145, 234));
		frame.getContentPane().setBackground(new Color(0, 145, 234));
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		
		JLabel bootLabel = new JLabel("战舰游戏");
		bootLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bootLabel.setBounds(140, 100, 290, 80);
		bootLabel.setForeground(Color.WHITE);
		bootLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 70));
		frame.getContentPane().add(bootLabel);
		
		JLabel logoLabel = new JLabel();
		logoLabel.setBounds(0, 65, 150, 150);
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logoLabel.setIcon(new ImageIcon("resources/image/Logo.png"));
		frame.getContentPane().add(logoLabel);
		
		JLabel authorLabel = new JLabel();
		authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		authorLabel.setBounds(350, 200, 100, 100);
		authorLabel.setIcon(new ImageIcon("resources/image/ZYW.png"));
		frame.getContentPane().add(authorLabel);
		
		Timer timer = new Timer(true);
    	timer.schedule(new TimerTask() {
			@Override
			public void run() {
				frame.setVisible(false);
				frame.dispose();
				WindowView.main(new String[0]);
			}
		}, 3000);
	}
}
