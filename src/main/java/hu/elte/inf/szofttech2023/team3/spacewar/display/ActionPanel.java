package hu.elte.inf.szofttech2023.team3.spacewar.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ActionPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    JLabel actionPanelLabel;
    JPanel contentPanel;

    private JButton moveAttackButton;
    private JButton buildBuildingButton;
    private JButton buildShipButton;
    private JButton transferButton;
    private JButton mergeFleetButton;
    private JButton createFleetButton;
    private JButton backButton;

    private ArrayList<JButton> buttons;
    
    public ActionPanel(int actionPanelWidth, int actionPanelHeight) {
        super(new GridLayout(2, 1));
        setPreferredSize(new Dimension( actionPanelWidth, actionPanelHeight ));
        //actionPanelLabel = new JLabel("Actions");
        actionPanelLabel = new JLabel();
        actionPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        actionPanelLabel.setBorder(new EmptyBorder( SwingDisplayEngine.BORDER_TOP, SwingDisplayEngine.BORDER_LEFT, SwingDisplayEngine.BORDER_BOTTOM, SwingDisplayEngine.BORDER_RIGHT));
        actionPanelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, SwingDisplayEngine.FONT_SIZE ));

        contentPanel = new JPanel( new GridLayout(3,3));

        buttons = new ArrayList<JButton>();

        moveAttackButton = new JButton("Move/Attack");
        buildBuildingButton = new JButton(("Build Building"));
        buildShipButton = new JButton("Build Ship");
        transferButton = new JButton("Transfer");
        mergeFleetButton = new JButton("Merge Fleet");
        createFleetButton = new JButton(("Create Fleet"));
        backButton = new JButton("Back");

        buttons.add( moveAttackButton );
        buttons.add(buildBuildingButton);
        buttons.add(buildShipButton);
        buttons.add(transferButton);
        buttons.add(mergeFleetButton);
        buttons.add(createFleetButton);
        buttons.add(backButton);
        moveAttackButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.print("MoveAttackButton is clicked! \n");
            }
        });
        buildBuildingButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.print("buildBuildingButton is clicked! \n");
            }
        });
        buildShipButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.print("buildShipButton is clicked! \n");
            }
        });
        transferButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.print("transferButton is clicked! \n");
            }
        });
        mergeFleetButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.print("mergeFleetButton is clicked! \n");
            }
        });
        createFleetButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.print("createFleetButton is clicked! \n");
            }
        });
        backButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.print("backButton is clicked! \n");
            }
        });

        moveAttackButton.setVisible(false);
        buildBuildingButton.setVisible(false);
        buildShipButton.setVisible(false);
        transferButton.setVisible(false);
        mergeFleetButton.setVisible(false);
        createFleetButton.setVisible(false);
        backButton.setVisible(false);

        contentPanel.add(moveAttackButton);
        contentPanel.add(buildBuildingButton);
        contentPanel.add(buildShipButton);
        contentPanel.add(transferButton);
        contentPanel.add(mergeFleetButton);
        contentPanel.add(createFleetButton);
        contentPanel.add(backButton);

        add( actionPanelLabel, BorderLayout.NORTH);
        add( contentPanel , BorderLayout.SOUTH );
    }

    JButton getMoveAttackButton(){ return this.moveAttackButton; }
    JButton getBuildBuildingButton(){ return this.buildBuildingButton; }
    JButton getBuildShipButton(){ return this.buildShipButton; }
    JButton getTransferButton(){ return this.transferButton; }
    JButton getMergeFleetButton(){ return  this.mergeFleetButton; }
    JButton getCreateFleetButton() { return  this.createFleetButton; }
    JButton getBackButton(){ return this.backButton; }

    ArrayList<JButton> getButtons(){ return this.buttons; }
    public JPanel getContentPanel(){ return this.contentPanel; }
    public JLabel getActionPanelLabel(){ return this.actionPanelLabel; }

}
