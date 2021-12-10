package dungeon;

/**
 * Represents the Dungeon game.
 * It contains the player, maze, treasure and maintains game state.
 */
public interface Dungeon extends ReadOnlyDungeon {


  /**
   * Moves the player in the maze in the provided Move direction.
   *
   * @param move {@link Move} to be executed.
   * @throws IllegalArgumentException if provided move is not a valid move.
   * @throws IllegalStateException if game is over and player is dead.
   */
  void movePlayer(Move move) throws IllegalArgumentException, IllegalStateException;

  /**
   * Makes the player pick the treasure at the current location.
   *
   * @throws IllegalStateException if current location has no treasure.
   * @throws IllegalStateException if game is over and player is dead.
   */
  void playerPickTreasure() throws IllegalStateException;

  /**
   * Fires the crooked arrow in the specified direction and distance.
   *
   * @param direction the direction in which to fire the arrow.
   * @param arrowDistance the number of caves the arrow should traverse.
   * @return ArrowHitOutcome value that represents whether monster is successfully hit.
   * @throws IllegalArgumentException if distance is less than 1 or greater than 5.
   * @throws IllegalArgumentException if an invalid direction is provided.
   * @throws IllegalStateException if player has no arrows to fire.
   * @throws IllegalStateException if game has ended.
   */
  ArrowHitOutcome shootArrow(Move direction, int arrowDistance)
          throws IllegalStateException, IllegalArgumentException;

  /**
   * Makes the player pick the arrows from the their current location.
   *
   * @throws IllegalStateException if the current location has no arrows.
   * @throws IllegalStateException if game is over and player is dead.
   */
  void playerPickArrows() throws IllegalStateException;
}
