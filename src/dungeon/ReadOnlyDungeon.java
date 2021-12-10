package dungeon;

import java.util.List;
import java.util.Set;

/**
 * Represents a read only model of the Dungeon game.
 * It contains the player, maze, treasure and maintains game state.
 */
public interface ReadOnlyDungeon {
  /**
   * Returns the maze of the dungeon.
   *
   * @return the maze as List of Lists of {@link Location}.
   */
  List<List<Location>> getMaze();

  /**
   * Returns the starting location/cave in the maze.
   *
   * @return {@link Location} representing the starting location.
   */
  Location getStartLocation();

  /**
   * Returns the end location/cave in the maze.
   *
   * @return {@link Location} representing the end location.
   */
  Location getEndLocation();

  /**
   * Returns the current location of player in the maze.
   *
   * @return {@link Location} representing the player's location.
   */
  Location getPlayerCurrentLocation();

  /**
   * Returns the moves that are possible from the current location.
   *
   * @return a Set of {@link Move}s
   */
  Set<Move> getAvailableDirections();

  /**
   * Checks whether the player has visited the end cave.
   *
   * @return true if {@link Player} has visited the end cave.
   */
  boolean playerVisitedEnd();

  /**
   * Checks whether the game is over or not.
   *
   * @return true if game has ended.
   */
  boolean isGameOver();

  /**
   * Provides the description of a player.
   * @return player in the maze as a {@link Player}.
   */
  Player getPlayerDescription();

  /**
   * Gets the SmellLevel at the provided location.
   *
   * @param location the location whose smell level is to be obtained.
   * @return SmellLevel of the particular location.
   * @throws IllegalStateException if there is problem with SmellLevel at the location.
   * @throws IllegalArgumentException if invalid location is provided.
   */
  SmellLevel getSmell(Location location) throws IllegalStateException;


  /**
   * Checks whether the player is alive or dead.
   *
   * @return true if {@link Player} has been killed.
   */
  boolean isPlayerDead();
}
