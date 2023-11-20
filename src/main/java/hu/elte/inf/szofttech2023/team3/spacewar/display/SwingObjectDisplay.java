package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingMenuDisplay extends JPanel implements MenuDisplay {


    public SwingMenuDisplay(int panelWidth, int panelHeight) {
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
        attribPanelLabel.setBorder(new EmptyBorder(DisplayEngine.BORDER_TOP, DisplayEngine.BORDER_LEFT, DisplayEngine.BORDER_BOTTOM, DisplayEngine.BORDER_RIGHT));
        attribPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, DisplayEngine.FONT_SIZE ));
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
        progressPanelLabel.setBorder(new EmptyBorder(DisplayEngine.BORDER_TOP, DisplayEngine.BORDER_LEFT, DisplayEngine.BORDER_BOTTOM, DisplayEngine.BORDER_RIGHT));
        progressPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, DisplayEngine.FONT_SIZE ));
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
        actionPanelLabel.setBorder(new EmptyBorder( DisplayEngine.BORDER_TOP, DisplayEngine.BORDER_LEFT, DisplayEngine.BORDER_BOTTOM, DisplayEngine.BORDER_RIGHT));
        actionPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, DisplayEngine.FONT_SIZE ));
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
}
