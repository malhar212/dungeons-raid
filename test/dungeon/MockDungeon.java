package dungeon;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a mock dungeon used for testing purposes.
 */
public class MockDungeon implements Dungeon {
  private LocationPrivate currentLocation;
  private final PlayerPrivate player;

  /**
   * Creates a new instance of the mock dungeon.
   */
  public MockDungeon() {
    currentLocation = new LocationNode(5,6);
    currentLocation.setNextMove(Move.WEST);
    currentLocation.setNextMove(Move.EAST);
    currentLocation.setNextMove(Move.SOUTH);
    currentLocation.setArrows(2);
    currentLocation.setTreasure(Treasure.DIAMONDS,3);
    player = new PlayerModel();
  }

  /**
   * Moves the player in the maze in the provided Move direction.
   *
   * @param move {@link Move} to be executed.
   * @throws IllegalArgumentException if provided move is not a valid move.
   * @throws IllegalStateException    if game is over and player is dead.
   */
  @Override
  public void movePlayer(Move move) throws IllegalArgumentException, IllegalStateException {
    if (currentLocation.getNextMoves().contains(move)) {
      currentLocation = new LocationNode(6,6);
    }
    else {
      throw new IllegalArgumentException("Provided move is not a valid move " + move);
    }
  }

  /**
   * Makes the player pick the treasure at the current location.
   *
   * @throws IllegalStateException if current location has no treasure.
   * @throws IllegalStateException if game is over and player is dead.
   */
  @Override
  public void playerPickTreasure() throws IllegalStateException {
    Map<Treasure, Integer> treasure = currentLocation.getTreasure();
    for (Treasure treasureType: treasure.keySet()) {
      player.addToTreasure(treasureType,treasure.get(treasureType));
    }
  }

  /**
   * Fires the crooked arrow in the specified direction and distance.
   *
   * @param direction     the direction in which to fire the arrow.
   * @param arrowDistance the number of caves the arrow should traverse.
   * @return ArrowHitOutcome value that represents whether monster is successfully hit.
   * @throws IllegalArgumentException if distance is less than 1 or greater than 5.
   * @throws IllegalArgumentException if an invalid direction is provided.
   * @throws IllegalStateException    if player has no arrows to fire.
   * @throws IllegalStateException    if game has ended.
   */
  @Override
  public ArrowHitOutcome shootArrow(Move direction, int arrowDistance)
          throws IllegalStateException, IllegalArgumentException {
    player.fireArrow();
    return ArrowHitOutcome.MISS;
  }

  /**
   * Makes the player pick the arrows from the their current location.
   *
   * @throws IllegalStateException if the current location has no arrows.
   * @throws IllegalStateException if game is over and player is dead.
   */
  @Override
  public void playerPickArrows() throws IllegalStateException {
    int arrows = currentLocation.pickArrows();
    player.pickArrows(arrows);
  }

  /**
   * Returns the maze of the dungeon.
   *
   * @return the maze as List of Lists of {@link Location}.
   */
  @Override
  public List<List<Location>> getMaze() {
    return null;
  }

  /**
   * Returns the starting location/cave in the maze.
   *
   * @return {@link Location} representing the starting location.
   */
  @Override
  public Location getStartLocation() {
    return null;
  }

  /**
   * Returns the end location/cave in the maze.
   *
   * @return {@link Location} representing the end location.
   */
  @Override
  public Location getEndLocation() {
    return null;
  }

  /**
   * Returns the current location of player in the maze.
   *
   * @return {@link Location} representing the player's location.
   */
  @Override
  public Location getPlayerCurrentLocation() {
    return currentLocation;
  }

  /**
   * Returns the moves that are possible from the current location.
   *
   * @return a Set of {@link Move}s
   */
  @Override
  public Set<Move> getAvailableDirections() {
    return null;
  }

  /**
   * Checks whether the player has visited the end cave.
   *
   * @return true if {@link Player} has visited the end cave.
   */
  @Override
  public boolean playerVisitedEnd() {
    return false;
  }

  /**
   * Checks whether the game is over or not.
   *
   * @return true if game has ended.
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * Provides the description of a player.
   *
   * @return player in the maze as a {@link Player}.
   */
  @Override
  public Player getPlayerDescription() {
    return player;
  }

  /**
   * Gets the SmellLevel at the provided location.
   *
   * @param location the location whose smell level is to be obtained.
   * @return SmellLevel of the particular location.
   * @throws IllegalStateException    if there is problem with SmellLevel at the location.
   * @throws IllegalArgumentException if invalid location is provided.
   */
  @Override
  public SmellLevel getSmell(Location location) throws IllegalStateException {
    return SmellLevel.NONE;
  }

  /**
   * Checks whether the player is alive or dead.
   *
   * @return true if {@link Player} has been killed.
   */
  @Override
  public boolean isPlayerDead() {
    return false;
  }
}
