package dungeon;

import randomizer.Randomizer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * This serves as JAVA swing based graphical user interface based controller for the player in the
 * dungeon game.
 */
public class DungeonSwingController implements DungeonGraphicalController {
  private Randomizer randomizer;
  private Dungeon dungeon;
  private DungeonView view;
  private Dungeon modelCopy;
  private int rows;
  private int columns;
  private boolean wrapped;
  private int interconnectivity;
  private int treasureAndArrowPercentage;
  private int numberOfMonsters;

  /**
   * Creates an instance of Java Swing based Graphical User Interface controller for controlling
   * the dungeon game.
   */
  public DungeonSwingController() {
    randomizer = null;
    dungeon = null;
    view = null;
    modelCopy = null;
    rows = 0;
    columns = 0;
    wrapped = false;
    interconnectivity = 0;
    treasureAndArrowPercentage = 0;
    numberOfMonsters = 0;
  }

  /**
   * Starts the game and makes the view visible.
   *
   * @throws IllegalStateException if the view is not set before calling this method.
   */
  @Override
  public void play() throws IllegalStateException {
    if (view == null) {
      throw new IllegalStateException("Please set view before calling play");
    }
    view.makeVisible();
    view.addListener(this);
  }

  /**
   * Sets the graphical view for this game.
   *
   * @param view the view to be used by the controller.
   * @throws IllegalArgumentException if the provided view is null.
   */
  @Override
  public void setView(DungeonView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
  }

  /**
   * Sets the configuration for the dungeon in the dungeon game.
   *
   * @param rows                       the number of rows in the dungeon maze.
   * @param columns                    the number of columns in the dungeon maze.
   * @param wrapped                    if the maze is wrapped around edges or not.
   * @param interconnectivity          the interconnectivity.
   * @param treasureAndArrowPercentage the percentage of caves that have treasure or arrows.
   *                                   If percentage of nodes is decimal it
   *                                   takes the lower bound number of nodes.
   * @param numberOfMonsters           the number of monsters in the dungeon of caves.
   * @param randomizer                 the game randomizer to use.
   * @throws IllegalArgumentException if model creation fails due to invalid arguments.
   * @throws IllegalStateException if view is not set before calling this method.
   */
  @Override
  public void setGameConfig(int rows, int columns, boolean wrapped, int interconnectivity,
                            int treasureAndArrowPercentage, int numberOfMonsters,
                            Randomizer randomizer) throws IllegalArgumentException,
          IllegalStateException {
    if (view == null) {
      throw new IllegalStateException("View cannot be null");
    }
    this.randomizer = randomizer;
    this.rows = rows;
    this.columns = columns;
    this.wrapped = wrapped;
    this.interconnectivity = interconnectivity;
    this.treasureAndArrowPercentage = treasureAndArrowPercentage;
    this.numberOfMonsters = numberOfMonsters;
    createGame();
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    MenuItems menuClicked = MenuItems.getByText(actionCommand);
    JFrame frame = (JFrame) view;
    switch (menuClicked) {
      case NEW_GAME: {
        view.acquireDungeonConfig();
        break;
      }
      case RESTART: {
        this.dungeon = new DungeonModel(modelCopy);
        view.setSource(dungeon);
        view.refresh();
        break;
      }
      case RESTART_CONFIG: {
        try {
          createGame();
        }
        catch (IllegalArgumentException iae) {
          view.showMessage(iae.getMessage());
          view.refresh();
        }
        break;
      }
      case QUIT: {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        break;
      }
      case HELP: {
        view.showHelp();
        break;
      }
      default:
        //Do nothing
        break;
    }
  }

  /**
   * Handles mouse click on the dungeon to move the player.
   *
   * @param move the direction in which player should move after click.
   */
  @Override
  public void handleCellClick(Move move) {
    try {
      dungeon.movePlayer(move);
      StringBuilder message = new StringBuilder("You moved ").append(move);
      view.showMessage(message.toString());
      view.refresh();
    }
    catch (IllegalArgumentException iae) {
      view.showMessage(iae.getMessage());
    }
    catch (IllegalStateException ise) {
      //Do nothing
    }
  }

  /**
   * Invoked when a key has been typed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key typed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyTyped(KeyEvent e) {
    //Do nothing
  }

  /**
   * Invoked when a key has been pressed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key pressed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyPressed(KeyEvent e) {
    //Do nothing
  }

  /**
   * Invoked when a key has been released.
   * See the class description for {@link KeyEvent} for a definition of
   * a key released event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (e != null) {
      if (e.isControlDown()) {
        Move direction = null;
        if (e.getKeyCode() == 37) {
          direction = Move.WEST;
        } else if (e.getKeyCode() == 38) {
          direction = Move.NORTH;
        } else if (e.getKeyCode() == 39) {
          direction = Move.EAST;
        } else if (e.getKeyCode() == 40) {
          direction = Move.SOUTH;
        }
        Integer distance = null;
        try {
          distance = view.acquireArrowDistance();
          ArrowHitOutcome arrowHitOutcome = dungeon.shootArrow(direction, distance);
          String message = "";
          switch (arrowHitOutcome) {
            case MISS: {
              message = "\nYour arrow goes whistling through the dungeon "
                      + "and there's a clunk "
                      + "as it falls to the ground after hitting a cave wall";
              break;
            }
            case INJURED: {
              message = "\nYou hear a painful roar in the distance. "
                      + "It seems your arrow hit an Otyugh";
              break;
            }
            case KILLED: {
              message = "\nYou hear a painful roar and wild thrashing in the "
                      + "darkness and then silence. "
                      + "It seems you've killed an Otyugh";
              break;
            }
            default: {
              message = "\nYour arrow goes whistling through the dungeon "
                      + "and there's a clunk "
                      + "as it falls to the ground after hitting a cave wall";
            }
          }
          view.showMessage(message);
          view.refresh();
        } catch (NumberFormatException ne) {
          view.showMessage("Shooting cancelled");
        } catch (IllegalArgumentException | IllegalStateException iae) {
          view.showMessage(iae.getMessage());
        }
      } else {
        try {
          StringBuilder message = new StringBuilder("You moved ");
          if (e.getKeyCode() == 37) {
            dungeon.movePlayer(Move.WEST);
            message.append(Move.WEST);
          } else if (e.getKeyCode() == 38) {
            dungeon.movePlayer(Move.NORTH);
            message.append(Move.NORTH);
          } else if (e.getKeyCode() == 39) {
            dungeon.movePlayer(Move.EAST);
            message.append(Move.EAST);
          } else if (e.getKeyCode() == 40) {
            dungeon.movePlayer(Move.SOUTH);
            message.append(Move.SOUTH);
          } else if (e.getKeyCode() == 65) {
            message.delete(0, message.length());
            if (dungeon.getPlayerCurrentLocation().hasArrows()) {
              dungeon.playerPickArrows();
              message.append("You picked up arrows");
            }
            else {
              message.append("There are no arrows to pickup");
            }
          } else if (e.getKeyCode() == 84) {
            message.delete(0, message.length());
            if (dungeon.getPlayerCurrentLocation().hasTreasure()) {
              dungeon.playerPickTreasure();
              message.append("You picked up some treasure");
            }
            else {
              message.append("There is no treasure to pickup");
            }
          }
          if (!message.toString().equals("You moved ")) {
            view.showMessage(message.toString());
          }
          view.refresh();
        } catch (IllegalArgumentException iae) {
          view.showMessage(iae.getMessage());
        } catch (IllegalStateException ise) {
          //Do nothing
        }
      }
    }
  }

  private void createGame() {
    this.dungeon = new DungeonModel(
            rows, columns, wrapped, interconnectivity, treasureAndArrowPercentage, numberOfMonsters,
            randomizer);
    this.modelCopy = new DungeonModel(dungeon);
    view.setSource(dungeon);
    view.showMessage("Welcome to the dungeons\nYou can use arrow keys or mouse for navigation\nA "
            + "to pickup arrows\nT to pickup treasure\nCtrl + arrow key to shoot in that "
            + "direction");
    view.refresh();
  }

}
