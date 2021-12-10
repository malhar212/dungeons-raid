package dungeon;

import randomizer.Randomizer;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 * This serves as a graphical user interface based controller for the player in the dungeon game.
 */
public interface DungeonGraphicalController extends ActionListener, KeyListener {


  /**
   * Starts the game and makes the view visible.
   *
   * @throws IllegalStateException if the view is not set before calling this method.
   */
  void play() throws IllegalArgumentException, IllegalStateException;

  /**
   * Sets the graphical view for this game.
   *
   * @param view the view to be used by the controller.
   * @throws IllegalArgumentException if the provided view is null.
   */
  void setView(DungeonView view) throws IllegalArgumentException;

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
  void setGameConfig(int rows, int columns, boolean wrapped,
                     int interconnectivity, int treasureAndArrowPercentage, int numberOfMonsters,
                     Randomizer randomizer) throws IllegalArgumentException, IllegalStateException;

  /**
   * Handles mouse click on the dungeon to move the player.
   *
   * @param move the direction in which player should move after click.
   */
  void handleCellClick(Move move);
}
