package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CollectionPanel extends JPanel {

        JLabel collectionPanelLabel;

        public CollectionPanel( int collectionDisplayWidth, int collectionDisplayHeight )
        {
            setPreferredSize(new Dimension(collectionDisplayWidth, collectionDisplayHeight ));
            collectionPanelLabel = new JLabel("Collection Panel");
            collectionPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
            collectionPanelLabel.setBorder(new EmptyBorder(DisplayEngine.BORDER_TOP, DisplayEngine.BORDER_LEFT, DisplayEngine.BORDER_BOTTOM, DisplayEngine.BORDER_RIGHT));
            collectionPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, DisplayEngine.FONT_SIZE ));
            add( collectionPanelLabel, BorderLayout.NORTH);
        }

        public JLabel getCollectionPanelLabel(){ return this.collectionPanelLabel; }
}
