package dungeon;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import randomizer.GameRandomizer;


/**
 * Represents a Graphical user interface view for Dungeon: display the game board and provide
 * visual interface for users.
 */
public class DungeonGraphicalView extends JFrame implements DungeonView {

  private final JMenuBar menuBar;
  private final JMenu menu;
  private final JMenuItem newGame;
  private final JMenuItem restartSameDungeon;
  private final JMenuItem quit;
  private final JPanel parentPanel;
  private final JPanel dungeonPanel;
  private final JTextArea messages;
  private final JDialog newGameDialog;
  private final JLabel locationArrows;
  private final JLabel locationDiamond;
  private final JLabel locationSapphire;
  private final JLabel locationRuby;
  private final JLabel playerArrows;
  private final JLabel playerDiamond;
  private final JLabel playerSapphire;
  private final JLabel playerRuby;
  private final JLabel locationDesc;
  private final JMenuItem restartSameConfig;
  private final JMenuItem help;
  private final JScrollPane pane;
  private DungeonGraphicalController listener;
  private ReadOnlyDungeon dungeon;

  /**
   * Creates a new instance of graphical user interface for the Dungeon game.
   */
  public DungeonGraphicalView() {
    menuBar = new JMenuBar();
    menu = new JMenu("Menu");
    this.listener = null;
    newGameDialog = new JDialog(this);
    // create menu items
    newGame = new JMenuItem(MenuItems.NEW_GAME.getText());
    restartSameDungeon = new JMenuItem(MenuItems.RESTART.getText());
    restartSameConfig = new JMenuItem(MenuItems.RESTART_CONFIG.getText());
    help = new JMenuItem(MenuItems.HELP.getText());
    quit = new JMenuItem(MenuItems.QUIT.getText());
    parentPanel = new JPanel();
    parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
    locationArrows = new JLabel("0",
            getImageIcon("/arrow-white.png"), JLabel.CENTER);
    locationDiamond = new JLabel("0",
            getImageIcon("/diamond.png"), JLabel.CENTER);
    locationSapphire = new JLabel("0",
            getImageIcon("/emerald.png"), JLabel.CENTER);
    locationRuby = new JLabel("0",
            getImageIcon("/ruby.png"), JLabel.CENTER);
    playerArrows = new JLabel("0",
            getImageIcon("/arrow-white.png"), JLabel.CENTER);
    playerDiamond = new JLabel("0",
            getImageIcon("/diamond.png"), JLabel.CENTER);
    playerSapphire = new JLabel("0",
            getImageIcon("/emerald.png"), JLabel.CENTER);
    playerRuby = new JLabel("0",
            getImageIcon("/ruby.png"), JLabel.CENTER);
    locationDesc = new JLabel("Location:", getResizedImageIcon("/color-cells/NSEW.png",
            30, 30),
            JLabel.CENTER);
    messages = new JTextArea();
    dungeonPanel = new JPanel();
    pane = new JScrollPane(dungeonPanel);
    setUpUserInterface();
  }

  /**
   * Set up the controller to handle click, menu and key events in this view.
   *
   * @param listener the controller.
   * @throws IllegalArgumentException if provided listener is null.
   */
  @Override
  public void addListener(DungeonGraphicalController listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Please provide a valid listener");
    }
    this.listener = listener;
    dungeonPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        try {
          CustomDungeonGridLabel clickedLocation =
                  (CustomDungeonGridLabel) dungeonPanel.getComponentAt(e.getPoint());
          Move move = convertClickToMove(clickedLocation.getXCoord(), clickedLocation.getYCoord());
          listener.handleCellClick(move);
        } catch (ClassCastException | IllegalArgumentException exception) {
          //Do nothing
        }
      }
    });
    dungeonPanel.addKeyListener(listener);
    newGame.addActionListener(listener);
    restartSameDungeon.addActionListener(listener);
    restartSameConfig.addActionListener(listener);
    help.addActionListener(listener);
    quit.addActionListener(listener);
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    renderDungeon();
    boolean gameOver = dungeon.isGameOver();
    if (gameOver) {
      StringBuilder appendable = new StringBuilder();
      if (dungeon.isPlayerDead()) {
        appendable.append("Game has ended!");
        appendable.append(
                "\nYOU LOSE!!!!\nYou were killed. You died a gruesome death at the hands of the "
                        + "Otyugh");
      } else if (dungeon.playerVisitedEnd()) {
        appendable.append("YOU WIN!!!!\nYou have escaped the mines of Moria");
      }
      showMessage(appendable.toString());
    }
    dungeonPanel.requestFocusInWindow();
  }

  /**
   * Displays the provided message on the message panel.
   *
   * @param message the message to be shown.
   * @throws IllegalArgumentException if the provided string message is null.
   */
  @Override
  public void showMessage(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    messages.setText(message);
    dungeonPanel.requestFocusInWindow();
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
    acquireDungeonConfig();
  }

  /**
   * Set the source model of game state for this view.
   *
   * @param dungeon the source model that represents game state.
   * @throws IllegalArgumentException if provided model is null.
   */
  @Override
  public void setSource(ReadOnlyDungeon dungeon) throws IllegalArgumentException {
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon model cannot be null");
    }
    this.dungeon = dungeon;
  }

  /**
   * Show popup to acquire configuration for new game.
   */
  @Override
  public void acquireDungeonConfig() {
    JSpinner rows = new JSpinner(new SpinnerNumberModel(5, 5, Integer.MAX_VALUE, 1));
    JSpinner columns = new JSpinner(new SpinnerNumberModel(5, 5, Integer.MAX_VALUE, 1));
    JSpinner numberOfMonsters = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    Dimension dimension = new Dimension(100, 15);
    numberOfMonsters.setPreferredSize(dimension);
    JSpinner interconnectivity = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    rows.setPreferredSize(dimension);
    JSpinner treasureAndArrowPercentage = new JSpinner(new SpinnerNumberModel(0, 0,
            Integer.MAX_VALUE, 1));
    columns.setPreferredSize(dimension);
    interconnectivity.setPreferredSize(dimension);
    treasureAndArrowPercentage.setPreferredSize(dimension);
    JLabel wrappedLabel = new JLabel("Dungeon wrapped:");
    JCheckBox wrapped = new JCheckBox();
    JPanel newGamePanel = new JPanel();
    newGamePanel.setLayout(new GridLayout(7, 2, 10, 10));
    newGamePanel.add(new JLabel("Rows:"));
    newGamePanel.add(rows);
    newGamePanel.add(new JLabel("Columns:"));
    newGamePanel.add(columns);
    newGamePanel.add(wrappedLabel);
    newGamePanel.add(wrapped);
    newGamePanel.add(new JLabel("Interconnectivity:"));
    newGamePanel.add(interconnectivity);

    newGamePanel.add(new JLabel("Treasure and Arrow Percentage:"));
    newGamePanel.add(treasureAndArrowPercentage);

    newGamePanel.add(new JLabel("Number of monsters:"));
    newGamePanel.add(numberOfMonsters);

    JButton button = new JButton("New Game");
    newGamePanel.add(button);
    JButton cancelButton = new JButton("Cancel");
    newGamePanel.add(cancelButton);
    cancelButton.addActionListener(e -> {
      newGameDialog.dispose();
    });
    newGameDialog.setLocationRelativeTo(parentPanel);
    button.addActionListener(e -> {
      try {
        listener.setGameConfig((Integer) rows.getValue(), (Integer) columns.getValue(),
                wrapped.isSelected(),
                (Integer) interconnectivity.getValue(),
                (Integer) treasureAndArrowPercentage.getValue(),
                (Integer) numberOfMonsters.getValue(), new GameRandomizer());
        newGameDialog.dispose();
      } catch (IllegalArgumentException iae) {
        messages.setText(iae.getMessage());
      }
    });
    newGameDialog.setTitle("New Game");
    newGameDialog.setVisible(true);
    newGameDialog.setSize(300, 300);
    newGameDialog.setLocation(200, 200);
    newGameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    newGameDialog.add(newGamePanel);
  }

  /**
   * Show popup to acquire arrow distance for shooting action.
   *
   * @return user entered integer value.
   * @throws NumberFormatException if user enters non-Integer value.
   */
  @Override
  public int acquireArrowDistance() throws NumberFormatException {
    return Integer.parseInt(JOptionPane.showInputDialog(parentPanel, "Enter Arrow Distance",
            1));
  }

  /**
   * Shows the help message for using this game.
   */
  @Override
  public void showHelp() {
    JOptionPane.showMessageDialog(this, "You "
            + "can use arrow keys or "
            + "mouse for navigation, 'A' to pickup arrows, 'T' to pickup treasure, 'Ctrl "
            + "+ arrow key' to shoot in that direction");
  }

  private void setUpUserInterface() {
    // add menu items to menu
    menu.add(newGame);
    menu.add(restartSameDungeon);
    menu.add(restartSameConfig);
    menu.add(help);
    menu.add(quit);
    // add menu to menu bar
    menuBar.add(menu);
    // add menubar to frame
    setJMenuBar(menuBar);
    // set the size of the frame
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createInfoPanel();
    createMessagesPanel();
    dungeonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    dungeonPanel.setBackground(Color.ORANGE);
    dungeonPanel.setFocusable(true);
    pane.setInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, new InputMap());
    parentPanel.add(pane);
    this.add(parentPanel);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            | UnsupportedLookAndFeelException e) {
      //Do Nothing
    }
  }

  private void createMessagesPanel() {
    messages.setMaximumSize(new Dimension(800, 100));
    messages.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
    messages.setEditable(false);
    messages.setBackground(Color.PINK);
    JPanel messagesPanel = new JPanel();
    messagesPanel.setMaximumSize(new Dimension(800, 200));
    messagesPanel.add(messages);
    messagesPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    messagesPanel.setBackground(Color.PINK);
    parentPanel.add(messagesPanel);
  }

  private ImageIcon getImageIcon(String path) {
    if (path == null) {
      return null;
    }
    try {
      return new ImageIcon(ImageIO.read(Objects.requireNonNull(
              getClass().getResourceAsStream(path))));
    } catch (IOException exception) {
      return null;
    }
  }

  private ImageIcon getResizedImageIcon(String path, int sizeX, int sizeY) {
    if (path == null) {
      return null;
    }
    try {
      BufferedImage overlay = ImageIO.read(Objects.requireNonNull(
              getClass().getResourceAsStream(path)));
      Image tmp = overlay.getScaledInstance(sizeX, sizeY, Image.SCALE_SMOOTH);
      return new ImageIcon(tmp);
    } catch (IOException exception) {
      return null;
    }
  }

  private void createInfoPanel() {
    JPanel infoPanel = new JPanel();
    infoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setBackground(Color.CYAN);
    infoPanel.setPreferredSize(new Dimension(800, 100));
    infoPanel.setMinimumSize(new Dimension(800, 100));
    infoPanel.setMaximumSize(new Dimension(800, 100));
    JPanel locationDescPanel = new JPanel(new GridLayout(1, 5));
    locationDesc.setHorizontalAlignment(JLabel.CENTER);
    locationDescPanel.add(locationDesc);
    locationDescPanel.add(locationArrows);
    locationDescPanel.add(locationDiamond);
    locationDescPanel.add(locationSapphire);
    locationDescPanel.add(locationRuby);
    JPanel playerDescPanel = new JPanel(new GridLayout(1, 5));
    JLabel playerDesc = new JLabel("Player:", getResizedImageIcon(
            "/player.png", 30, 30),
            JLabel.CENTER);
    playerDesc.setHorizontalAlignment(JLabel.CENTER);
    playerDescPanel.add(playerDesc);
    playerDescPanel.add(playerArrows);
    playerDescPanel.add(playerDiamond);
    playerDescPanel.add(playerSapphire);
    playerDescPanel.add(playerRuby);
    locationDescPanel.setPreferredSize(new Dimension(800, 50));
    playerDescPanel.setPreferredSize(new Dimension(800, 50));
    locationDescPanel.setMaximumSize(new Dimension(800, 50));
    playerDescPanel.setMaximumSize(new Dimension(800, 50));
    infoPanel.add(locationDescPanel);
    infoPanel.add(playerDescPanel);
    parentPanel.add(infoPanel);
  }

  private void renderDungeon() {
    dungeonPanel.removeAll();
    try {
      updatePlayerDescription();
      Rectangle rectangle = visualizeDungeon(dungeon);
      dungeonPanel.scrollRectToVisible(rectangle);
    } catch (IOException | IllegalArgumentException e) {
      //Do nothing
    }
    parentPanel.repaint();
  }

  private BufferedImage overlay(BufferedImage starting, String path, int offset) throws
          IOException, IllegalArgumentException {
    if (starting == null) {
      throw new IllegalArgumentException("Starting image cannot be null");
    }
    if (path == null) {
      return starting;
    }
    BufferedImage overlay = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream(path)));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }

  private BufferedImage overlay(BufferedImage starting, String path, int offsetX, int offsetY,
                                int sizeX, int sizeY) throws
          IOException, IllegalArgumentException {
    if (starting == null) {
      throw new IllegalArgumentException("Starting image cannot be null");
    }
    if (path == null) {
      return starting;
    }
    BufferedImage overlay = ImageIO.read(Objects.requireNonNull(
            getClass().getResourceAsStream(path)));
    Image tmp = overlay.getScaledInstance(sizeX, sizeY, Image.SCALE_SMOOTH);
    int w = starting.getWidth();
    int h = starting.getHeight();
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(tmp, offsetX, offsetY, null);
    return combined;
  }

  private Rectangle visualizeDungeon(ReadOnlyDungeon dungeon) throws IOException,
          IllegalArgumentException {
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon cannot be null");
    }
    Location playerCurrentLocation = dungeon.getPlayerCurrentLocation();
    updateLocationDescription(playerCurrentLocation);
    dungeonPanel.setLayout(new GridBagLayout());
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.fill = GridBagConstraints.NONE;
    int row = -1;
    int column;
    Rectangle bounds = null;
    List<List<Location>> maze = dungeon.getMaze();
    for (List<Location> list : maze) {
      row++;
      column = -1;
      for (Location locationNode : list) {
        column++;
        CustomDungeonGridLabel label = new CustomDungeonGridLabel();
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        if (locationNode.isExplored()) {
          Set<Move> nextMoves = locationNode.getNextMoves();
          StringBuilder fileName = new StringBuilder("/color-cells/");
          for (Move move : nextMoves) {
            fileName.append(move.getShortForm());
          }
          fileName.append(".png");
          image = overlay(image, fileName.toString(), 0);
          if (compareLocations(playerCurrentLocation, locationNode)) {
            image = overlay(image, "/player.png", 16, 16, 30, 30);
          }
          if (locationNode.hasArrows()) {
            image = overlay(image, "/arrow-white.png", 5, 30, 20, 2);
          }
          if (locationNode.hasTreasure()) {
            Map<Treasure, Integer> treasure = locationNode.getTreasure();
            for (Treasure treasureItem : treasure.keySet()) {
              StringBuilder treasureFileName = new StringBuilder("/");
              int xOffset = 10;
              switch (treasureItem) {
                case DIAMONDS:
                  treasureFileName.append("diamond");
                  break;
                case SAPPHIRES:
                  treasureFileName.append("emerald");
                  xOffset = 25;
                  break;
                case RUBIES:
                  treasureFileName.append("ruby");
                  xOffset = 40;
                  break;
                default:
                  //Do nothing
                  break;
              }
              treasureFileName.append(".png");
              image = overlay(image, treasureFileName.toString(), xOffset, 5, 15, 15);
            }
          }
          if (locationNode.hasMonster()) {
            image = overlay(image, "/otyugh.png", 0);
          } else {
            switch (dungeon.getSmell(locationNode)) {
              case LESS:
                image = overlay(image, "/stench01.png", 0);
                break;
              case MORE:
                image = overlay(image, "/stench02.png", 0);
                break;
              default:
                break;
            }
          }
        } else {
          image = overlay(image, "/blank.png", 0);
        }
        label.setIcon(new ImageIcon(image));
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setSize(64, 64);
        label.setBackground(Color.CYAN);
        gridBagConstraints.gridy = row;
        gridBagConstraints.gridx = column;
        label.setXCoord(column);
        label.setYCoord(row);
        dungeonPanel.add(label, gridBagConstraints);
        if (compareLocations(playerCurrentLocation, locationNode)) {
          bounds = new Rectangle(column * 64, row * 64, column * 20, row * 20);
        }
      }
    }
    return bounds;
  }

  private void updateLocationDescription(Location playerCurrentLocation) {
    if (playerCurrentLocation != null) {
      Set<Move> nextMoves = playerCurrentLocation.getNextMoves();
      StringBuilder fileName = new StringBuilder("/color-cells/");
      for (Move move : nextMoves) {
        fileName.append(move.getShortForm());
      }
      fileName.append(".png");
      locationDesc.setIcon(getResizedImageIcon(fileName.toString(), 30, 30));
      String resetValue = "0";
      locationArrows.setText(resetValue);
      locationDiamond.setText(resetValue);
      locationSapphire.setText(resetValue);
      locationRuby.setText(resetValue);
      if (playerCurrentLocation.hasArrows()) {
        locationArrows.setText(String.valueOf(playerCurrentLocation.getArrows()));
      }
      updateTreasureDescription(playerCurrentLocation.hasTreasure(),
              playerCurrentLocation.getTreasure(), locationDiamond, locationSapphire, locationRuby);
    }
  }

  private void updatePlayerDescription() {
    Player playerDescription = dungeon.getPlayerDescription();
    playerArrows.setText(String.valueOf(playerDescription.getArrows()));
    updateTreasureDescription(playerDescription.hasTreasure(), playerDescription.getTreasure(),
            playerDiamond, playerSapphire, playerRuby);
  }

  private void updateTreasureDescription(boolean b, Map<Treasure, Integer> treasure,
                                         JLabel diamond, JLabel sapphire, JLabel ruby) {
    if (treasure != null && diamond != null && sapphire != null && ruby != null) {
      if (b) {
        for (Treasure treasureItem : treasure.keySet()) {
          switch (treasureItem) {
            case DIAMONDS:
              diamond.setText(String.valueOf(treasure.get(treasureItem)));
              break;
            case SAPPHIRES:
              sapphire.setText(String.valueOf(treasure.get(treasureItem)));
              break;
            case RUBIES:
              ruby.setText(String.valueOf(treasure.get(treasureItem)));
              break;
            default:
              //Do nothing
              break;
          }
        }
      }
    }
  }

  private StringBuilder visualizeKruskals(ReadOnlyDungeon dungeon) {
    if (dungeon == null) {
      return new StringBuilder();
    }
    List<List<Location>> maze = dungeon.getMaze();
    Location startLocation = dungeon.getStartLocation();
    Location endLocation = dungeon.getEndLocation();
    Location playerCurrentLocation = dungeon.getPlayerCurrentLocation();
    StringBuilder sb = new StringBuilder();
    int count = 0;
    for (List<Location> list : maze) {
      sb.append("\n");
      for (Location locationNode : list) {
        Set<Move> nextMoves = locationNode.getNextMoves();
        if (nextMoves.contains(Move.NORTH)) {
          sb.append("|");
          count++;
        } else {
          sb.append(" ");
        }
        sb.append("  ");
      }
      sb.append("\n");
      for (Location locationNode : list) {
        if (compareLocations(startLocation, locationNode)
                && compareLocations(playerCurrentLocation, locationNode)) {
          sb.append("$");
        } else if (compareLocations(startLocation, locationNode)) {
          sb.append("S");
        } else if (compareLocations(endLocation, locationNode)
                && compareLocations(playerCurrentLocation, locationNode)) {
          if (locationNode.hasMonster()) {
            sb.append("M");
          } else {
            sb.append("*");
          }
        } else if (compareLocations(playerCurrentLocation, locationNode)) {
          sb.append("P");
        } else if (compareLocations(endLocation, locationNode)) {
          sb.append("X");
        }
        if (locationNode.hasMonster()) {
          sb.append("M");
        } else {
          switch (dungeon.getSmell(locationNode)) {
            case NONE:
              sb.append(0);
              break;
            case LESS:
              sb.append(1);
              break;
            case MORE:
              sb.append(2);
              break;
            default:
              sb.append("N");
          }
        }
        Set<Move> nextMoves = locationNode.getNextMoves();
        if (nextMoves.contains(Move.EAST)) {
          sb.append("--");
          count++;
        } else {
          sb.append("  ");
        }
      }
    }
    sb.append("\n\nNumber of edges: ").append(count);
    return sb;
  }

  private Move convertClickToMove(int x, int y) {
    Location playerCurrentLocation = dungeon.getPlayerCurrentLocation();
    List<List<Location>> maze = dungeon.getMaze();
    int mazeRows = maze.size();
    int mazeColumns = maze.get(0).size();
    int row = playerCurrentLocation.getRow();
    Move move = null;
    int column = playerCurrentLocation.getColumn();
    if (row == y) {
      if (column - x == -1 || column - x == mazeColumns - 1) {
        move = Move.EAST;
      }
      if (column - x == 1 || column - x == -(mazeColumns - 1)) {
        move = Move.WEST;
      }
    }
    if (column == x) {
      if (row - y == -1 || row - y == mazeRows - 1) {
        move = Move.SOUTH;
      }
      if (row - y == 1 || row - y == -(mazeRows - 1)) {
        move = Move.NORTH;
      }
    }
    return move;
  }

  private boolean compareLocations(Location locationA, Location locationB)
          throws IllegalArgumentException {
    if (locationA == null || locationB == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    return locationA.getRow() == locationB.getRow()
            && locationA.getColumn() == locationB.getColumn();
  }
}
