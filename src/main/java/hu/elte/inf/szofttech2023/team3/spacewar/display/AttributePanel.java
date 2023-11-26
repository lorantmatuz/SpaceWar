package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AttributePanel extends JPanel {
    JLabel attributePanelLabel;
    public AttributePanel( int attributePanelWidth, int attributePanelHeight )
    {
        setPreferredSize(new Dimension( attributePanelWidth, attributePanelHeight ));
        attributePanelLabel = new JLabel("Attribute Panel");
        attributePanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attributePanelLabel.setBorder(new EmptyBorder(DisplayEngine.BORDER_TOP, DisplayEngine.BORDER_LEFT, DisplayEngine.BORDER_BOTTOM, DisplayEngine.BORDER_RIGHT));
        attributePanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, DisplayEngine.FONT_SIZE ));
        add( attributePanelLabel, BorderLayout.NORTH);
    };

    JLabel getAttributePanelLabel(){ return this.attributePanelLabel; };
}
