package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingObjectDisplay extends JPanel implements Rectangular, MenuDisplay {

    private int objectDisplayWidth;
    private int objectDisplayHeight;

    private AttributePanel attributePanel;
    private CollectionPanel collectionPanel;
    private ActionPanel actionPanel;

    public SwingObjectDisplay( int rowCount , int columnCount ) {
        super(new BorderLayout());

        objectDisplayHeight = rowCount * DisplayEngine.FIELD_HEIGHT;
        objectDisplayWidth  = Math.min( columnCount * DisplayEngine.FIELD_WIDTH  , 500 );
        //setPreferredSize(new Dimension( objectDisplayWidth, objectDisplayHeight));
        setPreferredSize(new Dimension( objectDisplayWidth, objectDisplayHeight));

        // ATTRIBUTE PANEL //
        attributePanel = new AttributePanel(objectDisplayWidth, objectDisplayHeight/4);
        //attributePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        attributePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                handleMenuClick(e, attributePanel);
            }

        });


        // COLLECTION PANEL //
        collectionPanel = new CollectionPanel( objectDisplayWidth , objectDisplayHeight/2 );
        //progressPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        collectionPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                handleMenuClick(e, collectionPanel);
            }

        });

        // ACTION PANEL //
        actionPanel = new ActionPanel( objectDisplayWidth , objectDisplayHeight/4 );
        actionPanel.addMouseListener( new  MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                handleMenuClick(e, actionPanel);
            }
        });



        add(attributePanel, BorderLayout.NORTH );
        add(collectionPanel, BorderLayout.CENTER );
        add(actionPanel, BorderLayout.SOUTH );



    }
    @Override
    public void handleMenuClick(MouseEvent e, JPanel clickedOn ) {
        System.out.println("Menu is clicked!");
    }

    public AttributePanel getAttributePanel(){ return this.attributePanel; };
    public CollectionPanel getCollectionPanel(){ return this.collectionPanel; }
    public ActionPanel getActionPanel(){ return this.actionPanel; }
}
