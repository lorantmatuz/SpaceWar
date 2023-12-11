package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

public class SwingFrameDisplay extends JFrame implements Rectangular {

    public SwingFrameDisplay(SwingBoardDisplay boardPanel, SwingObjectDisplay objectPanel, SwingTurnInfoDisplay turnInfoDisplay) {
        setTitle("Space War");
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Space War");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(
                new EmptyBorder(
                        SwingDisplayEngine.BORDER_TOP,
                        SwingDisplayEngine.BORDER_LEFT,
                        SwingDisplayEngine.BORDER_BOTTOM,
                        SwingDisplayEngine.BORDER_RIGHT
                )
        );
        titleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, SwingDisplayEngine.FONT_SIZE ));

        contentPanel.add(titleLabel, BorderLayout.PAGE_START);
        contentPanel.add(boardPanel, BorderLayout.CENTER);
        contentPanel.add(objectPanel, BorderLayout.LINE_END);
        contentPanel.add(turnInfoDisplay, BorderLayout.PAGE_END);

        setContentPane(contentPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

}
