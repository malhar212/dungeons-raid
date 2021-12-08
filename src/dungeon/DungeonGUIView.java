package dungeon;


import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.DimensionUIResource;

public class DungeonGUIView extends JFrame implements DungeonView {

  private final JMenuBar menuBar;
  private final JMenu menu;
  private final JMenuItem newGame;
  private final JMenuItem restart;
  private final JMenuItem quit;

  public DungeonGUIView() {
    menuBar = new JMenuBar();
    menu = new JMenu("Menu");
    // create menu items
    newGame = new JMenuItem("New Game");
    restart = new JMenuItem("Restart");
    quit = new JMenuItem("Quit");
    // add menu items to menu
    menu.add(newGame);
    menu.add(restart);
    menu.add(quit);
    // add menu to menu bar
    menuBar.add(menu);
    // add menubar to frame
    setJMenuBar(menuBar);
    // set the size of the frame
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel parentPanel= new JPanel();
    parentPanel.setLayout(new BoxLayout(parentPanel,BoxLayout.Y_AXIS));
    JPanel infoPanel= new JPanel();
    infoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    //infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
    infoPanel.setBackground(Color.CYAN);
    infoPanel.setMinimumSize(new Dimension(500,200));
    infoPanel.setMaximumSize(new Dimension(800,200));
    JLabel locationDesc = new JLabel("Location desc");
    locationDesc.setHorizontalAlignment(JLabel.CENTER);
    JLabel playerDesc = new JLabel("Player desc");
    playerDesc.setHorizontalAlignment(JLabel.CENTER);
    JPanel messagesPanel = new JPanel();
    JLabel messages = new JLabel("Messages");
    messages.setHorizontalAlignment(JLabel.LEFT);
    messagesPanel.setMaximumSize(new Dimension(800,100));
    messagesPanel.add(messages);
    messagesPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    messagesPanel.setBackground(Color.PINK);
    JPanel dungeonPanel = new JPanel();
    dungeonPanel.setLayout(new GridLayout(2,2,0,0));
    dungeonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    dungeonPanel.setBackground(Color.orange);
    infoPanel.add(locationDesc);
    infoPanel.add(playerDesc);
    parentPanel.add(infoPanel);
    parentPanel.add(messagesPanel);
    parentPanel.add(dungeonPanel);
    add(parentPanel);
  }

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addClickListener(DungeonGUIController listener) {

  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {

  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }
}
