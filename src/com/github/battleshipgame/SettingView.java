package com.github.battleshipgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SettingView {

	private JFrame frame;

	public SettingView(JFrame parentFrame) {
		initialize(parentFrame);
		frame.setVisible(true);
		frame.toFront();
		frame.repaint();
		frame.setLocationRelativeTo(null);
	}

	private void initialize(JFrame parentFrame) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setType(Type.UTILITY);
		frame.setAlwaysOnTop(true);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setBackground(Color.WHITE);
		frame.setTitle("难度选择");
		frame.setBounds(100, 100, 440, 229);
		frame.setResizable(false);
		
		JLabel gridLengthLabel = new JLabel("地图大小");
		gridLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gridLengthLabel.setBounds(20, 20, 91, 50);
		gridLengthLabel.setBackground(Color.WHITE);
		gridLengthLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 22));
		frame.getContentPane().add(gridLengthLabel);
		
		JLabel numberOfShipsLabel = new JLabel("战舰数量");
		numberOfShipsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfShipsLabel.setBounds(20, 80, 91, 50);
		numberOfShipsLabel.setBackground(Color.WHITE);
		numberOfShipsLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 22));
		frame.getContentPane().add(numberOfShipsLabel);
		
		// 设置地图大小
		JSlider gridLengthSlider = new JSlider();
		gridLengthSlider.setMajorTickSpacing(1);
		gridLengthSlider.setPaintTicks(true);
		gridLengthSlider.setPaintLabels(true);
		gridLengthSlider.setMinimum(5);
		gridLengthSlider.setMaximum(15);
		gridLengthSlider.setBackground(Color.WHITE);
		gridLengthSlider.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		gridLengthSlider.setBounds(120, 30, 300, 60);
		gridLengthSlider.setValue(GameController.gridLength);
		frame.getContentPane().add(gridLengthSlider);
		
		// 设置战舰数量
		JSlider numberOfShipsSlider = new JSlider();
		numberOfShipsSlider.setMajorTickSpacing(1);
		numberOfShipsSlider.setPaintTicks(true);
		numberOfShipsSlider.setPaintLabels(true);
		numberOfShipsSlider.setMinimum(1);
		numberOfShipsSlider.setMaximum(5);
		numberOfShipsSlider.setBackground(Color.WHITE);
		numberOfShipsSlider.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		numberOfShipsSlider.setBounds(120, 90, 300, 60);
		numberOfShipsSlider.setValue(GameController.numberOfShips);
		frame.getContentPane().add(numberOfShipsSlider);
		
		JButton confirmButton = new JButton("确认");
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameController.gridLength = gridLengthSlider.getValue();
				GameController.numberOfShips = numberOfShipsSlider.getValue();
				frame.setVisible(false);
				frame.dispose();
				WindowView.restart(parentFrame);
			}
		});
		confirmButton.setBackground(Color.WHITE);
		confirmButton.setBounds(80, 158, 100, 30);
		confirmButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		frame.getContentPane().add(confirmButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setBounds(260, 158, 100, 30);
		cancelButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		frame.getContentPane().add(cancelButton);
	}
}
