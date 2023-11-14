package hu.elte.inf.szofttech2023.team3.spacewar.display;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

    private final JPanel objectPanel;
    private final int objectPanelWidth = 500;
    private final JFrame frame;
    private final JButton shuffleButton;
    
    private Displayable[][] content = new Displayable[0][0];
    
    public SwingBoardDisplay(int rowCount, int columnCount, int fieldWidth, int fieldHeight) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.boardPanel = createAndInitBoardPanel(columnCount * fieldWidth, rowCount * fieldHeight);
        //this.objectPanel =  createAndInitObjectPanel( (int)Math.floor((double)columnCount/3.0) * fieldWidth , rowCount * fieldHeight );
        this.objectPanel =  createAndInitObjectPanel( objectPanelWidth , rowCount * fieldHeight );
        this.shuffleButton = new JButton("Shuffle");
        this.frame = createAndInitFrame(this.boardPanel, this.objectPanel, this.shuffleButton);
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
                handleBoardClick(e);
            }
            
        });
        return unsetBoardPanel;
    }

    private JPanel createAndInitObjectPanel(int panelWidth, int panelHeight) {
        //JPanel unsetObjectPanel = new JPanel() {};
        JPanel unsetObjectPanel = new JPanel(new GridLayout(3,1) ) {};
        unsetObjectPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        //unsetObjectPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        unsetObjectPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                handlePanelClick(e);
            }

        });

        /*
        JLabel label = new JLabel("Object panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT));
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE ));
        unsetObjectPanel.add(label, BorderLayout.NORTH);
         */

        // ATTRIBUTE PANEL //
        JPanel attributePanel = new JPanel() {};
        attributePanel.setPreferredSize(new Dimension(panelWidth, panelHeight/4 ));
        //attributePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        attributePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                handlePanelClick(e);
            }

        });

        JLabel attribPanelLabel = new JLabel("Attribute Panel");
        attribPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attribPanelLabel.setBorder(new EmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT));
        attribPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE ));
        attributePanel.add( attribPanelLabel, BorderLayout.NORTH);

        // PROGRESS PANEL //
        JPanel progressPanel = new JPanel() {};
        progressPanel.setPreferredSize(new Dimension(panelWidth, panelHeight/2 ));
        //progressPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        progressPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                handlePanelClick(e);
            }

        });

        JLabel progressPanelLabel = new JLabel("Progress Panel");
        progressPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressPanelLabel.setBorder(new EmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT));
        progressPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE ));
        progressPanel.add( progressPanelLabel, BorderLayout.NORTH);

        // ACTION PANEL //
        JPanel actionPanel = new JPanel() {};
        actionPanel.setPreferredSize(new Dimension(panelWidth, panelHeight/4 ));
        //actionPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        progressPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                handlePanelClick(e);
            }

        });

        JLabel actionPanelLabel = new JLabel("Action Panel");
        actionPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        actionPanelLabel.setBorder(new EmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT));
        actionPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE ));
        actionPanel.add( actionPanelLabel, BorderLayout.NORTH);

        /*
        JSplitPane splitObjectPanelBottom = new JSplitPane();
        //splitPane.setSize( columnCount * fieldWidth + (int)Math.floor((double)columnCount/3.0) * fieldWidth , rowCount*fieldHeight  );
        splitObjectPanelBottom.setSize( columnCount * fieldWidth + objectPanelWidth , rowCount*fieldHeight*2/4  );
        splitObjectPanelBottom.setDividerSize(0);
        splitObjectPanelBottom.setDividerLocation( columnCount * fieldWidth * 2 / 4 );
        splitObjectPanelBottom.setOrientation(JSplitPane.VERTICAL_SPLIT );
        splitObjectPanelBottom.setTopComponent( progressPanel );
        splitObjectPanelBottom.setBottomComponent( actionPanel );

        JSplitPane splitObjectPanelTop = new JSplitPane();
        //splitPane.setSize( columnCount * fieldWidth + (int)Math.floor((double)columnCount/3.0) * fieldWidth , rowCount*fieldHeight  );
        splitObjectPanelTop.setSize( columnCount * fieldWidth + objectPanelWidth , rowCount*fieldHeight  );
        splitObjectPanelTop.setDividerSize(0);
        splitObjectPanelTop.setDividerLocation( columnCount * fieldWidth / 4 );
        splitObjectPanelTop.setOrientation(JSplitPane.VERTICAL_SPLIT );
        splitObjectPanelTop.setTopComponent( attributePanel );
        splitObjectPanelTop.setBottomComponent( splitObjectPanelBottom );

        unsetObjectPanel.add( splitObjectPanelTop );
        */

        unsetObjectPanel.add(attributePanel);
        unsetObjectPanel.add(progressPanel);
        unsetObjectPanel.add(actionPanel);

        return unsetObjectPanel;
    }
    private JFrame createAndInitFrame(JPanel boardPanel, JPanel objectPanel, JButton shuffleButton) {
        JFrame unsetFrame = new JFrame();
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Space War");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT));
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE ));
        contentPanel.add(label, BorderLayout.NORTH);
        //contentPanel.add(boardPanel, BorderLayout.CENTER);
        //contentPanel.add(objectPanel, BorderLayout.EAST );
        contentPanel.add(shuffleButton, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane();
        //splitPane.setSize( columnCount * fieldWidth + (int)Math.floor((double)columnCount/3.0) * fieldWidth , rowCount*fieldHeight  );
        splitPane.setSize( columnCount * fieldWidth + objectPanelWidth , rowCount*fieldHeight  );
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation( columnCount * fieldWidth );
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent( boardPanel );
        splitPane.setRightComponent( objectPanel );

        contentPanel.add( splitPane );

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
    
    private void handleBoardClick(MouseEvent e) {
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

    private void handlePanelClick(MouseEvent e) {
        System.out.println("Panel is clicked!");
    }
}
