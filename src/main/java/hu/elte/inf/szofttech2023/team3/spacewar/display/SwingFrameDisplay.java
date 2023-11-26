package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SwingFrameDisplay extends JFrame implements Rectangular {

    private final int frameWidth;
    private final int frameHeight;

    public SwingFrameDisplay(SwingBoardDisplay boardPanel, SwingObjectDisplay objectPanel, SwingTurnInfoDisplay turnInfoDisplay) {

        //frameWidth  = boardPanel.getWidth() + objectPanel.getWidth();
        frameWidth  = turnInfoDisplay.getWidth();
        frameHeight = boardPanel.getHeight() + turnInfoDisplay.getHeight();

        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Space War");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(
                new EmptyBorder(
                        DisplayEngine.BORDER_TOP,
                        DisplayEngine.BORDER_LEFT,
                        DisplayEngine.BORDER_BOTTOM,
                        DisplayEngine.BORDER_RIGHT
                )
        );
        titleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, DisplayEngine.FONT_SIZE ));

        JSplitPane splitPane = new JSplitPane();
        splitPane.setSize( frameWidth , boardPanel.getHeight()  );
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation( boardPanel.getWidth() );
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent( boardPanel );
        splitPane.setRightComponent( objectPanel );

        contentPanel.add( titleLabel     , BorderLayout.NORTH  );
        contentPanel.add( splitPane      , BorderLayout.CENTER );
        contentPanel.add( turnInfoDisplay, BorderLayout.SOUTH  );

        setContentPane(contentPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }
}
