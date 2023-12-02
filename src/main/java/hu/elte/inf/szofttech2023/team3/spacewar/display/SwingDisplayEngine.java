package hu.elte.inf.szofttech2023.team3.spacewar.display;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class SwingDisplayEngine implements DisplayEngine {

    public static final int FIELD_WIDTH = 35;
    public static final int FIELD_HEIGHT = 35;

    public static final int BORDER_TOP = 10;
    public static final int BORDER_BOTTOM = 10;
    public static final int BORDER_LEFT = 10;
    public static final int BORDER_RIGHT = 10;
    public static final int FONT_SIZE = 15;

    private final SwingBoardDisplay boardDisplay;
    private final SwingObjectDisplay objectDisplay;
    private final SwingFrameDisplay frameDisplay;
    private final SwingTurnInfoDisplay turnInfoDisplay;

    public SwingDisplayEngine(int rowCount, int columnCount) {
        this.boardDisplay = new SwingBoardDisplay(rowCount, columnCount);
        this.objectDisplay = new SwingObjectDisplay(rowCount, columnCount);
        this.turnInfoDisplay = new SwingTurnInfoDisplay( boardDisplay.getPreferredSize().width + objectDisplay.getPreferredSize().width );
        this.frameDisplay = new SwingFrameDisplay(boardDisplay, objectDisplay, turnInfoDisplay);
    }

    @Override
    public void applyBoard(Displayable[][] boardContent) {
        if (!this.frameDisplay.isVisible()) {
            frameDisplay.setVisible(true);
        }
        Displayable[][] newBoardContent = new Displayable[boardContent.length][];
        for (int i = 0; i < boardContent.length; i++) {
            newBoardContent[i] = Arrays.copyOf(boardContent[i], boardContent[i].length);
        }
        this.boardDisplay.setContent(newBoardContent);
        this.boardDisplay.repaint();
    }

    @Override
    public void applyObjectInfo(String title, List<Map.Entry<String,Integer>> content)
    {
        JLabel panelLabel = objectDisplay.getAttributePanel().getAttributePanelLabel();
        String outText = new String("<html>");
        outText = outText + "<p>" + title + "</p>";
        for ( Map.Entry<String,Integer> set : content )
        {
            outText = outText + "<p>" + set.getKey() + ":" + set.getValue().toString() + "</p>";
        }
        outText = outText + "</html>";
        panelLabel.setText( outText );
    }
    @Override
    public void applyObjectItemsInfo(Boolean erase, String title, List<String> header, List<Map.Entry< String,List<Integer> > > content)
    {
        CollectionPanel collectionPanel = objectDisplay.getCollectionPanel();
        if( erase )
        {
            collectionPanel.removeAll();
            collectionPanel.revalidate();
            collectionPanel.repaint();
        }
        JLabel panelLabel = collectionPanel.setCollectionPanelLabel();
        String outText = new String("<html>");
        outText = outText + "<p>" + title + "</p>";
        outText = outText + "<p>" ;
        for(String element : header ) {
            outText = outText + element + ",";
        }
        outText = outText + "</p> <br>";
        for ( Map.Entry<String,List<Integer>> set : content )
        {
            outText = outText + "<p>" + set.getKey() + ",";
            for(Integer element : set.getValue() )
            {
                outText = outText + element.toString() + ",";
            }
            outText = outText + "</p>";
        }
        outText = outText + "</html>";
        panelLabel.setText( outText );
        collectionPanel.add( panelLabel );
    }

    @Override
    public void applyObjectActionPalette(List< Map.Entry<String, Runnable >> content )
    {
        JPanel actionPanel = objectDisplay.getActionPanel().getContentPanel();
        actionPanel.removeAll();
        actionPanel.revalidate();
        actionPanel.repaint();
        for ( Map.Entry<String, Runnable> entry : content )
        {
            JButton requiredButton = new JButton( entry.getKey() );
            Runnable action = entry.getValue();
            requiredButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e) {
                    action.run();
                }
            } );
            actionPanel.add( requiredButton );
        }
    }

    @Override
    public SwingBoardDisplay getBoardDisplay() {
        return this.boardDisplay;
    }
    public JButton getShuffleButton(){ return this.turnInfoDisplay.getShuffleButton(); }

    @Override
    public void setInfoLabel( String info ){
        JLabel infoLabel = this.turnInfoDisplay.getInfoLabel();
        infoLabel.setText( info );
    }
    @Override
    public void setTurnLabel( int turnNumber, String player ){
        JLabel turnLabel = this.turnInfoDisplay.getTurnLabel();
        String turnInfo = "Turn: " + turnNumber + ", Player: " + player;
        turnLabel.setText( turnInfo );
    }

}

