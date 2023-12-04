package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SwingTurnInfoDisplay extends JPanel implements Rectangular, MenuDisplay {

    public static final int ROW_COUNT = 3;
    private int turnInfoDisplayWidth;
    private int turnInfoDisplayHeight;
    private final JButton shuffleButton;
    private final JLabel turnLabel;
    private final JLabel infoLabel;
    public SwingTurnInfoDisplay( int turnInfoDisplayWidth )
    {
        super(new GridLayout(3,1) );

        this.turnInfoDisplayWidth = turnInfoDisplayWidth;
        this.turnInfoDisplayHeight = ROW_COUNT * SwingDisplayEngine.FIELD_HEIGHT;

        setPreferredSize(new Dimension( turnInfoDisplayWidth, turnInfoDisplayHeight));

        infoLabel = new JLabel(" The game begins!");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setBorder(
                new EmptyBorder(
                        SwingDisplayEngine.BORDER_TOP,
                        SwingDisplayEngine.BORDER_LEFT,
                        SwingDisplayEngine.BORDER_BOTTOM,
                        SwingDisplayEngine.BORDER_RIGHT
                )
        );
        infoLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, SwingDisplayEngine.FONT_SIZE ));

        turnLabel = new JLabel(" Turn 1, Player: X");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setBorder(
                new EmptyBorder(
                        SwingDisplayEngine.BORDER_TOP,
                        SwingDisplayEngine.BORDER_LEFT,
                        SwingDisplayEngine.BORDER_BOTTOM,
                        SwingDisplayEngine.BORDER_RIGHT
                )
        );
        turnLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, SwingDisplayEngine.FONT_SIZE ));

        this.shuffleButton = new JButton("End Turn");

        add(infoLabel, BorderLayout.NORTH);
        add(turnLabel, BorderLayout.CENTER);
        add(shuffleButton, BorderLayout.SOUTH);
    }

    @Override
    public void handleMenuClick(MouseEvent e, JPanel clickedOn )
    {
        System.out.println("Info panel has been clicked!");
    }

    @Override
    public int getWidth(){ return this.turnInfoDisplayWidth; };
    @Override
    public int getHeight(){ return  this.turnInfoDisplayHeight; };

    public JButton getShuffleButton(){ return this.shuffleButton; };

    public JLabel getInfoLabel(){ return this.infoLabel; }
    public JLabel getTurnLabel(){ return this.turnLabel; }

}
