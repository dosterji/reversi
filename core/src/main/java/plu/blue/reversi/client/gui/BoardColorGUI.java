package plu.blue.reversi.client.gui;

import plu.blue.reversi.client.model.ReversiGame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * BoardColorSettings Class
 * A GUI Window that displays
 * when a player chooses the Menu Option "Board Color Settings"
 * Its purpose is to allow the user to customize three pieces of the game:
 * 1. Player 1 game piece Color
 * 2. Player 2 game piece Color
 * 3. The Main Board Game Color
 */
public class BoardColorGUI extends JPanel{
    private JFrame ColorSettingsWindow;
    private JButton ApplyButton, ExitButton;
    private JLabel Player1Label, Player2Label, BoardLabel;
    private JRadioButton Player1RadioButton, Player2RadioButton, BoardRadioButton;
    private ColorSet ColorSet;
    private JPanel ButtonPanel, ColorsPanel, PlayerOneColorPanel,
            PlayerTwoColorPanel, BoardColorPanel, CustomizePanel,
            PlayerOneCustomColor, PlayerTwoCustomColor, BoardCustomColor;
    private ReversiGame ReversiGame;
    private BoardView boardView;

    /**
     * Constructor: BoardColorGUI
     */
    BoardColorGUI(ReversiGame game, BoardView bv) {
        JFrameSetup();
        JPanelSetup();
        JButtonSetup();
        JLabelSetup();
        JRadioButtonSetup();
        ColorSettingsWindow.setVisible(true);
        ReversiGame = game;
        boardView = bv;
    }

    /**
     * JFrame Setup: Initializes the Main Window of Color Customization.
     */
    private void JFrameSetup() {
        ColorSettingsWindow = new JFrame();
        ColorSettingsWindow.setSize(700, 250);
        ColorSettingsWindow.dispatchEvent(new WindowEvent(ColorSettingsWindow, WindowEvent.WINDOW_CLOSING));
        ColorSettingsWindow.setTitle("Color Settings");

    }

    /**
     * JPanel Setup: Creates multiple JPanels for Organization of all Color Customization Content
     */
    private void JPanelSetup() {
        //Panel for displaying all colors
        ColorsPanel = new JPanel();
        ColorsPanel.setOpaque(false);
        ColorsPanel.setBackground(Color.DARK_GRAY);
        ColorsPanel.setBorder(LineBorder.createBlackLineBorder());
        ColorSettingsWindow.add(ColorsPanel, BorderLayout.NORTH);
        ColorDisplaySetup();

        //Panel that contains all customization contents (Player 1, Player 2, Board)
        CustomizePanel = new JPanel(new GridLayout());
        ColorSettingsWindow.add(CustomizePanel, BorderLayout.CENTER);


        //Panel for customizing Player 1
        PlayerOneColorPanel = new JPanel();
        PlayerOneColorPanel.setOpaque(true);
        PlayerOneColorPanel.setBackground(Color.WHITE);
        PlayerOneColorPanel.setBorder(LineBorder.createBlackLineBorder());
        CustomizePanel.add(PlayerOneColorPanel, BorderLayout.PAGE_START);
        //The custom displayed color for Player 1
        PlayerOneCustomColor = new JPanel();
        PlayerOneCustomColor.setOpaque(true);
        //PlayerOneCustomColor.setBackground(Color.WHITE);
        PlayerOneCustomColor.setPreferredSize(new Dimension(30, 30));
        PlayerOneCustomColor.setBorder(LineBorder.createBlackLineBorder());
        PlayerOneColorPanel.add(PlayerOneCustomColor);



        //Panel for customizing Player 2
        PlayerTwoColorPanel = new JPanel();
        PlayerTwoColorPanel.setBackground(Color.WHITE);
        PlayerTwoColorPanel.setBorder(LineBorder.createBlackLineBorder());
        CustomizePanel.add(PlayerTwoColorPanel, BorderLayout.CENTER);
        //The Custom displayed color for Player 2
        PlayerTwoCustomColor = new JPanel();
        PlayerTwoCustomColor.setOpaque(true);
        //PlayerTwoCustomColor.setBackground(Color.BLACK);
        PlayerTwoCustomColor.setPreferredSize(new Dimension(30, 30));
        PlayerTwoCustomColor.setBorder(LineBorder.createBlackLineBorder());
        PlayerTwoColorPanel.add(PlayerTwoCustomColor);

        //Panel for customizing Board
        BoardColorPanel = new JPanel();
        BoardColorPanel.setBackground(Color.WHITE);
        BoardColorPanel.setBorder(LineBorder.createBlackLineBorder());
        CustomizePanel.add(BoardColorPanel, BorderLayout.PAGE_END);
        //The custom displayed color for Board
        BoardCustomColor = new JPanel();
        BoardCustomColor.setOpaque(true);
        //BoardCustomColor.setBackground(Color.GREEN);
        BoardCustomColor.setPreferredSize(new Dimension(30, 30));
        BoardCustomColor.setBorder(LineBorder.createBlackLineBorder());
        BoardColorPanel.add(BoardCustomColor);


        ButtonPanel = new JPanel();
        ColorSettingsWindow.add(ButtonPanel, BorderLayout.SOUTH);
    }

    /**
     * JButton Setup: Creates Two(2) Buttons: Apply(Updates Customization Colors) Exit(Closes the Color Customization Window)
     */
    private void JButtonSetup() {
        ApplyButton = new JButton("Apply");
        ApplyButtonListener();
        ExitButton = new JButton("Exit");
        ExitButtonListener();
        ButtonPanel.add(ApplyButton, BorderLayout.SOUTH);
        ButtonPanel.add(ExitButton, BorderLayout.SOUTH);
    }

    /**
     * JLabel Setup: Creates three(3) JLabels to Title each customization Content.
     */
    private void JLabelSetup() {
        Player1Label = new JLabel("Customize Player 1");
        PlayerOneColorPanel.add(Player1Label);
        Player2Label = new JLabel("Customize Player 2");
        PlayerTwoColorPanel.add(Player2Label);
        BoardLabel = new JLabel("Customize Board Color");
        BoardColorPanel.add(BoardLabel);
    }

    /**
     * JRadioButton Setup: Creates three(3) RadioButtons (Player 1, Player 2, Board)
     */
    private void JRadioButtonSetup() {
        Player1RadioButton = new JRadioButton();
        PlayerOneColorPanel.add(Player1RadioButton);
        Player2RadioButton = new JRadioButton();
        PlayerTwoColorPanel.add(Player2RadioButton);
        BoardRadioButton = new JRadioButton();
        BoardColorPanel.add(BoardRadioButton);
        RadioButtonListener();
    }

    /**
     * ColorDisplay Setup: Creates a panel of colors for the player to choose from.
     */
    private void ColorDisplaySetup() {
        JPanel[] colordisplay = new JPanel[ColorSet.Length()];
        int index = 0;
        for (plu.blue.reversi.client.gui.ColorSet c : ColorSet.values()) {
            System.out.println("Index: " + index + " " + c.GetColor().toString());
            colordisplay[index] = new JPanel(new GridBagLayout());
            colordisplay[index].setOpaque(true);
            colordisplay[index].setPreferredSize(new Dimension(30, 30));
            colordisplay[index].setBorder(LineBorder.createGrayLineBorder());
            colordisplay[index].setBackground(c.GetColor());

            int finalIndex = index;
            colordisplay[index].addMouseListener(new MouseAdapter() {

                /**
                 * If the mouse clicks on a particular color, it checks to see which Radio Button is checked,
                 * and Sets the background of that respective custom color with the color they clicked on.
                 * @param e - Mouse Event
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    //This displays the RGB values for the background clicked for that panel
                    System.out.println(colordisplay[finalIndex].getBackground().toString());
                    if(BoardRadioButton.isSelected()) {
                        BoardCustomColor.setBackground(colordisplay[finalIndex].getBackground());
                        //Set the Color of the Board Here.
                    }
                    else if(Player1RadioButton.isSelected()) {
                        PlayerOneCustomColor.setBackground(colordisplay[finalIndex].getBackground());
                    }
                    else if(Player2RadioButton.isSelected()) {
                        PlayerTwoCustomColor.setBackground(colordisplay[finalIndex].getBackground());
                    }


                }
            });
            ColorsPanel.add(colordisplay[index]);

            index++;

        }

    }

    /**
     * Listener Method to setup the "Apply" Button.
     */
    public void ApplyButtonListener() {
        ApplyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Where the Information of colors gets updated.
                //If all colors are different, then update the board and pieces
                if(!PlayerOneCustomColor.getBackground().equals(PlayerTwoCustomColor.getBackground())
                        && !PlayerOneCustomColor.getBackground().equals(BoardCustomColor.getBackground())
                        && !PlayerTwoCustomColor.getBackground().equals(BoardCustomColor.getBackground())) {
                    ReversiGame.getP1().setPieceColor(PlayerOneCustomColor.getBackground());
                    boardView.getPlayerInfoPanel().setPlayer1Color(PlayerOneCustomColor.getBackground());
                    ReversiGame.getP2().setPieceColor(PlayerTwoCustomColor.getBackground());
                    boardView.getPlayerInfoPanel().setPlayer2Color(PlayerTwoCustomColor.getBackground());
                    boardView.setBoardColor(BoardCustomColor.getBackground());
                }
                else {//else prompt user to make sure all colors are different.
                    JOptionPane.showMessageDialog(ColorSettingsWindow, "Please Make sure all colors are different.");
                }



            }
        });
    }

    /**
     * Exit Listener Method to setup the "Exit" Button.
     */
    private void ExitButtonListener() {
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColorSettingsWindow.dispose();
            }
        });
    }

    /**
     * RadioButton Listener Method to disable all other radio buttons if the respective button is clicked.
     */
    private void RadioButtonListener() {
        Player1RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Player2RadioButton.setSelected(false);
                BoardRadioButton.setSelected(false);
            }
        });

        Player2RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Player1RadioButton.setSelected(false);
                BoardRadioButton.setSelected(false);
            }
        });

        BoardRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Player1RadioButton.setSelected(false);
                Player2RadioButton.setSelected(false);
            }
        });
    }

}
