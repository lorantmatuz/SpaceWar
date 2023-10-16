package hu.elte.inf.szofttech2023.team3.spacewar;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.GameController;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.display.SwingBoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.model.view.GameStateRenderer;

public class Main {

	public static void main(String[] args) {
        SwingBoardDisplay display = new SwingBoardDisplay(10, 10, 50, 50);
        JPanel boardPanel = display.getPanel();
        GameStateRenderer renderer = new GameStateRenderer(display);
        GameState state = new GameState();
        GameController controller = new GameController(state, renderer);
        
		JFrame frame = new JFrame();
		JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Space War");
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        contentPanel.add(label, BorderLayout.NORTH);
        contentPanel.add(boardPanel, BorderLayout.CENTER);
        JButton button = new JButton("Shuffle");
        button.addActionListener(ev -> controller.shuffle());
        contentPanel.add(button, BorderLayout.SOUTH);
		frame.setContentPane(contentPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		controller.shuffle();
	}
	
}
