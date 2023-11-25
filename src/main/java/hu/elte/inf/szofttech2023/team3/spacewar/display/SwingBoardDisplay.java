package hu.elte.inf.szofttech2023.team3.spacewar.display;

import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class SwingBoardDisplay implements BoardDisplay {

    private static final int BORDER_TOP = 10;
    private static final int BORDER_BOTTOM = 10;
    private static final int BORDER_LEFT = 10;
    private static final int BORDER_RIGHT = 10;
    private static final int FONT_SIZE = 40;

    private final int rowCount;
    private final int columnCount;
    private final int fieldHeight;
    private final int fieldWidth;
    private final JPanel boardPanel;
    private final JFrame frame;
    private final JButton shuffleButton;
    private final GameState gameState;

    private Displayable[][] content = new Displayable[0][0];
    private GameActionListener actionListener; // Hozzáadott GameActionListener

    public SwingBoardDisplay(int rowCount, int columnCount, int fieldWidth, int fieldHeight, GameState gameState) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.gameState = gameState;

        this.boardPanel = createAndInitBoardPanel(rowCount * fieldHeight, columnCount * fieldWidth);
        this.shuffleButton = new JButton("Shuffle");
        this.frame = createAndInitFrame(this.boardPanel, this.shuffleButton);


    }
    private void initMouseListener() {
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e);
            }
        });
    }
    
    private JPanel createAndInitBoardPanel(int panelWidth, int panelHeight) {
        JPanel unsetBoardPanel = new JPanel() {
            
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                drawBoard(g, panelWidth, panelHeight);
            }

        };
        unsetBoardPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        unsetBoardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        unsetBoardPanel.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e);
            }
            
        });
        return unsetBoardPanel;
    }

    private JFrame createAndInitFrame(JPanel boardPanel, JButton shuffleButton) {
        JFrame unsetFrame = new JFrame();
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Space War");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT));
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE ));
        contentPanel.add(label, BorderLayout.NORTH);
        contentPanel.add(boardPanel, BorderLayout.CENTER);
        
        contentPanel.add(shuffleButton, BorderLayout.SOUTH);
        
        unsetFrame.setContentPane(contentPanel);
        unsetFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        unsetFrame.pack();
        unsetFrame.setLocationRelativeTo(null);
        
        return unsetFrame;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public void apply(Displayable[][] content, Runnable shuffleAction) {
        if (!frame.isVisible()) {
            frame.setVisible(true);
        }
        this.content = new Displayable[content.length][];
        for (int i = 0; i < content.length; i++) {
            this.content[i] = Arrays.copyOf(content[i], content[i].length);
        }
        boardPanel.repaint();
        Arrays.stream(shuffleButton.getActionListeners()).forEach(shuffleButton::removeActionListener);
        shuffleButton.addActionListener(e -> shuffleAction.run());
    }

    private void drawBoard(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        int countOfRows = Math.min(rowCount, content.length);
        for (int row = 0; row < countOfRows; row++) {
            int countOfColumns = Math.min(columnCount, content[row].length);
            for (int column = 0; column < countOfColumns; column++) {
                drawDisplayable(row, column, g2d);
            }
        }
    }

    private void drawDisplayable(int row, int column, Graphics2D g2d) {
        Displayable item = content[row][column];
        if (item == null) {
            return;
        }
        
        Image image = item.getImage(fieldWidth, fieldHeight);
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int pad = 5;
        int left = column * fieldHeight;
        int top = row * fieldWidth;

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(image, left + pad, top + pad, left + fieldWidth - (2 * pad), top + fieldHeight - (2 * pad), 0, 0, imageWidth, imageHeight, null);
    }
    public void setActionListener(GameActionListener listener) {
        this.actionListener = listener;
    }
    private void handleClick(MouseEvent e) {
        Point point = e.getPoint();
        int x = (int) point.getX();
        int y = (int) point.getY();
        int row = y / fieldHeight;
        int column = x / fieldWidth;

        if (row >= rowCount || column >= columnCount) {
            handleEmptySpaceClick(row, column);
            return;
        }

        if (content.length <= row || content[row].length <= column || content[row][column] == null) {
            handleEmptySpaceClick(row, column);
            return;
        }

        Displayable field = content[row][column];
        if (field != null) {
            field.getAction().run();
        }
    }
    private void handleEmptySpaceClick(int row, int column) {
        if (actionListener != null && gameState != null) {
            FieldPosition position = FieldPosition.of(row, column);
            actionListener.actionPerformed(position, gameState);
        }
    }

}
