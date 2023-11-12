package hu.elte.inf.szofttech2023.team3.spacewar.display;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JPanel;

public class SwingBoardDisplay implements BoardDisplay {

    private final int rowCount;
    
    private final int columnCount;
    
    private final int fieldHeight;
    
    private final int fieldWidth;
    
    private final JPanel panel;
    
    private Displayable[][] content = new Displayable[0][0];
    
    public SwingBoardDisplay(int rowCount, int columnCount, int fieldWidth, int fieldHeight) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        int panelHeight = rowCount * fieldHeight;
        int panelWidth = columnCount * fieldWidth;
        this.panel = new JPanel() {
            
            private static final long serialVersionUID = 1L;

            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, panelWidth, panelHeight);
                int countOfRows = Math.min(rowCount, content.length);
                for (int row = 0; row < countOfRows; row++) {
                    int countOfColumns = Math.min(columnCount, content[row].length);
                    for (int column = 0; column < countOfColumns; column++) {
                        drawDisplayable(row, column, g2d);
                    }
                }
            }

        };
        this.panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.panel.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int x = (int) point.getX();
                int y = (int) point.getY();
                int row = y / fieldHeight;
                int column = x / fieldWidth;
                if (content.length < row) {
                    return;
                }
                
                Displayable[] contentRow = content[row];
                if (contentRow.length < column) {
                    return;
                }
                
                Displayable field = contentRow[column];
                if (field == null) {
                    return;
                }
                
                field.getAction().run();
            }
            
        });
    }

    private void drawDisplayable(int row, int column, Graphics2D g2d) {
        Displayable item = content[row][column];
        if (item == null) {
            return;
        }
        
        Image image = item.getImage(fieldWidth, fieldHeight);
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int left = column * fieldHeight;
        int top = row * fieldWidth;
        g2d.drawImage(image, left, top, left + fieldWidth, top + fieldHeight, 0, 0, imageWidth, imageHeight, panel);
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
    public void apply(Displayable[][] content) {
        this.content = new Displayable[content.length][];
        for (int i = 0; i < content.length; i++) {
            this.content[i] = Arrays.copyOf(content[i], content[i].length);
        }
        panel.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }
    
}
