package hu.elte.inf.szofttech2023.team3.spacewar.display;

import hu.elte.inf.szofttech2023.team3.spacewar.view.GameViewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DisplayEngine
{
    private final int FIELD_WIDTH = 50;
    private final int FIELD_HEIGHT = 50;

    private final int BORDER_TOP = 10;
    private final int BORDER_BOTTOM = 10;
    private final int BORDER_LEFT = 10;
    private final int BORDER_RIGHT = 10;
    private final int FONT_SIZE = 40;

    private GameViewer receiver;
    private SwingBoardDisplay boardDisplay;
    public DisplayEngine() {}

    private void setReceiver(GameViewer receiver )
    {
        this.receiver = receiver;
    }

    public SwingBoardDisplay getBoardDisplay() { return boardDisplay ; }

    public void init( GameViewer receiver , int rowCount , int columnCount )
    {
        this.setReceiver( receiver );

        boardDisplay = new SwingBoardDisplay( rowCount ,columnCount,FIELD_WIDTH ,FIELD_HEIGHT);

        JPanel boardPanel = boardDisplay.getPanel();

        JFrame frame = new JFrame();
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Space War");
        label.setBorder(new EmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT));
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE ));
        contentPanel.add(label, BorderLayout.NORTH);
        contentPanel.add(boardPanel, BorderLayout.CENTER);
        JButton button = new JButton("Shuffle");
        button.addActionListener(ev -> sendButtonAction());

        contentPanel.add(button, BorderLayout.SOUTH);
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void sendButtonAction()
    {
        this.receiver.transmitMessage();
    }

}
