package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AttributePanel extends JPanel {
    JLabel attributePanelLabel;
    public AttributePanel( int attributePanelWidth, int attributePanelHeight )
    {
        setPreferredSize(new Dimension( attributePanelWidth, attributePanelHeight ));
        attributePanelLabel = new JLabel();
        attributePanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attributePanelLabel.setBorder(new EmptyBorder(SwingDisplayEngine.BORDER_TOP, SwingDisplayEngine.BORDER_LEFT, SwingDisplayEngine.BORDER_BOTTOM, SwingDisplayEngine.BORDER_RIGHT));
        attributePanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, SwingDisplayEngine.FONT_SIZE ));
        add( attributePanelLabel, BorderLayout.NORTH);
    };

    JLabel getAttributePanelLabel(){ return this.attributePanelLabel; };
}
