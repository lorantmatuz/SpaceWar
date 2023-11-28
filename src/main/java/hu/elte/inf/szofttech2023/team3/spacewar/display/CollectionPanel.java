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
            label.setBorder(new EmptyBorder(SwingDisplayEngine.BORDER_TOP, SwingDisplayEngine.BORDER_LEFT, SwingDisplayEngine.BORDER_BOTTOM, SwingDisplayEngine.BORDER_RIGHT));
            label.setFont(new Font(Font.MONOSPACED, Font.BOLD, SwingDisplayEngine.FONT_SIZE ));
            return label;
        }

}
