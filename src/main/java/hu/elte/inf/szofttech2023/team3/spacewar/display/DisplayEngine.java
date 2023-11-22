package hu.elte.inf.szofttech2023.team3.spacewar.display;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class DisplayEngine implements Applicable {

    public static final int FIELD_WIDTH = 40;
    public static final int FIELD_HEIGHT = 40;

    public static final int BORDER_TOP = 10;
    public static final int BORDER_BOTTOM = 10;
    public static final int BORDER_LEFT = 10;
    public static final int BORDER_RIGHT = 10;
    public static final int FONT_SIZE = 20;





    private final SwingBoardDisplay boardDisplay;
    private final SwingObjectDisplay objectDisplay;
    private final SwingFrameDisplay frameDisplay;
    private final SwingTurnInfoDisplay turnInfoDisplay;

    public DisplayEngine(int rowCount, int columnCount) {
        this.boardDisplay = new SwingBoardDisplay(rowCount, columnCount);
        this.objectDisplay = new SwingObjectDisplay(rowCount, columnCount);
        this.turnInfoDisplay = new SwingTurnInfoDisplay( boardDisplay.getWidth() + objectDisplay.getWidth() );
        this.frameDisplay = new SwingFrameDisplay(boardDisplay, objectDisplay, turnInfoDisplay);
    }


    @Override
    public void apply(Displayable[][] boardContent) {
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
    public void apply(String title, List<Map.Entry<String,Integer>> content)
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
    public void apply(List<String> titleList, List<Map.Entry< String,List<Integer> > > content)
    {
        JLabel panelLabel = objectDisplay.getCollectionPanel().getCollectionPanelLabel();
        String outText = new String("<html>");
        outText = outText + "<p>" ;
        for(String element : titleList ) {
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
    }

    @Override
    public void apply(List< Map.Entry<String, Runnable >> content )
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

    public SwingBoardDisplay getBoardDisplay() {
        return this.boardDisplay;
    }
    public JButton getShuffleButton(){ return this.turnInfoDisplay.getShuffleButton(); }


}

