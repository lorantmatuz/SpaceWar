package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CollectionPanel extends JPanel {


        public CollectionPanel( int collectionDisplayWidth, int collectionDisplayHeight )
        {
            setPreferredSize(new Dimension(collectionDisplayWidth, collectionDisplayHeight ));
        }

        JLabel setCollectionPanelLabel()
        {
            JLabel label = new JLabel("Collection Panel");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(new EmptyBorder(DisplayEngine.BORDER_TOP, DisplayEngine.BORDER_LEFT, DisplayEngine.BORDER_BOTTOM, DisplayEngine.BORDER_RIGHT));
            label.setFont(new Font(Font.MONOSPACED, Font.BOLD, DisplayEngine.FONT_SIZE ));
            return label;
        }

}
