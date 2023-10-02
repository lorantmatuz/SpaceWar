package hu.elte.inf.szofttech2023.team3.spacewar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JLabel label = new JLabel("Space War!");
		label.setBorder(new EmptyBorder(50, 50, 50, 50));
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
		frame.getContentPane().add(label);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
}
