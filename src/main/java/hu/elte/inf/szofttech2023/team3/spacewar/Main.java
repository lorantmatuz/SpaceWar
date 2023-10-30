package hu.elte.inf.szofttech2023.team3.spacewar;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.GameController;
import hu.elte.inf.szofttech2023.team3.spacewar.display.SwingBoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameStateRenderer;

public class Main {

    public static void main(String[] args) {
        Space space = new Space(20, 20);
        GameState state = new GameState(space);
        
        SwingBoardDisplay display = new SwingBoardDisplay(20, 20, 40, 40);
        JPanel boardPanel = display.getPanel();
        GameStateRenderer renderer = new GameStateRenderer(display);

        GameController controller = new GameController(state, renderer);
        
        JFrame frame = new JFrame();
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Space War");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        contentPanel.add(label, BorderLayout.NORTH);
        contentPanel.add(boardPanel, BorderLayout.CENTER);
        JButton button = new JButton("Regenerate space");
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
