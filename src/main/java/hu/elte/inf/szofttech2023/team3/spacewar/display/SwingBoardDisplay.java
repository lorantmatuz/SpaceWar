package hu.elte.inf.szofttech2023.team3.spacewar.display;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.function.BiConsumer;

import javax.swing.*;

import hu.elte.inf.szofttech2023.team3.spacewar.view.BoardEventType;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

public class SwingBoardDisplay extends JPanel implements Rectangular, BoardDisplay {

    private static final long serialVersionUID = 1L;
    
    private final int boardWidth;
    private final int boardHeight;
    private final int rowCount;
    private final int columnCount;
    private final int fieldWidth;
    private final int fieldHeight;
    
    private Displayable[][] content = new Displayable[0][0];
    private BiConsumer<BoardEventType, FieldPosition> boardListener;
    private FieldPosition previousFieldPosition = null;
    
    private String winnerName = null;
    private Color winnerColor = null;
    
    public SwingBoardDisplay(int rowCount, int columnCount) {

        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.fieldWidth = SwingDisplayEngine.FIELD_WIDTH;
        this.fieldHeight = SwingDisplayEngine.FIELD_HEIGHT;
        this.boardWidth = columnCount * fieldWidth;
        this.boardHeight = rowCount * fieldHeight;
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                    handleClick(e,fieldPositionOf(e));
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMove(fieldPositionOf(e));
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMove(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                handleMove(fieldPositionOf(e));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                handleMove(null);
            }
        };
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        drawBoard((Graphics2D) g, boardWidth, boardHeight);
    }

    private void handleClick(MouseEvent event, FieldPosition position) {
        if (boardListener == null) {
            return;
        }
        int button = event.getButton();
        if( button == MouseEvent.BUTTON1 )
        {
            boardListener.accept(BoardEventType.LEFT_CLICK, position);
        }
        else if( button == MouseEvent.BUTTON3 )
        {
            boardListener.accept(BoardEventType.RIGHT_CLICK, position);
        }
    }
    
    private void handleMove(FieldPosition position) {
        if (boardListener == null || Objects.equals(position, previousFieldPosition)) {
            return;
        }

        if (position != null) {
            boardListener.accept(BoardEventType.HOVER, position);
        } else {
            boardListener.accept(BoardEventType.OUT, null);
        }

        previousFieldPosition = position;
    }


    @Override
    public int getRowCount() { return rowCount; }

    @Override
    public int getColumnCount() { return columnCount; }

    @Override
    public int getWidth() { return this.boardWidth; }
    @Override
    public int getHeight() { return this.boardHeight; }

    public Displayable[][] getContent(){ return content; }
    public void setContent( Displayable[][] newContent ){ this.content = newContent; }


    private void drawBoard(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        int countOfRows = Math.min(rowCount, content.length);
        for (int row = 0; row < countOfRows; row++) {
            int countOfColumns = Math.min(columnCount, content[row].length);
            for (int column = 0; column < countOfColumns; column++) {
                drawDisplayable(row, column, g2d);
            }
        }
        drawWinnerIfNecessary(g2d, width, height);
    }

    private void drawWinnerIfNecessary(Graphics2D g2d, int width, int height) {
        if (winnerName == null) {
            return;
        }
        
        Color overlayColor = new Color(winnerColor.getRed(), winnerColor.getGreen(), winnerColor.getBlue(), 150);
        g2d.setColor(overlayColor);
        g2d.fillRect(width / 10, height / 10, 8 * width / 10, 8 * height / 10);

        int fontSize = height / 8;
        Font font = new Font(Font.MONOSPACED, Font.ITALIC | Font.BOLD, fontSize);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("Winner:", width / 5, 2 * height / 5);
        g2d.drawString(winnerName, width / 5, (2 * height / 5) + (8 * fontSize / 5));
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


    private FieldPosition fieldPositionOf(MouseEvent e) {
        Point point = e.getPoint();
        int x = (int) point.getX();
        int y = (int) point.getY();
        int row = y / fieldHeight;
        int column = x / fieldWidth;
        return FieldPosition.of(row, column);
    }
    
    @Override
    public void setWinner(String name, Color color) {
        winnerName = name;
        winnerColor = color;
    }

    @Override
    public void setBoardListener(BiConsumer<BoardEventType, FieldPosition> boardListener) {
        this.boardListener = boardListener;
    }
    
}
