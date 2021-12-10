package dungeon;

/**
 * A view for Dungeon: display the game board and provide visual interface
 * for users.
 */
public interface DungeonView {

  /**
   * Set up the controller to handle click, menu and key events in this view.
   *
   * @param listener the controller.
   * @throws IllegalArgumentException if provided listener is null.
   */
  void addListener(DungeonGraphicalController listener) throws IllegalArgumentException;

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Displays the provided message on the message panel.
   *
   * @param message the message to be shown.
   * @throws IllegalArgumentException if the provided string message is null.
   */
  void showMessage(String message) throws IllegalArgumentException;

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Set the source model of game state for this view.
   *
   * @param dungeon the source model that represents game state.
   * @throws IllegalArgumentException if provided model is null.
   */
  void setSource(ReadOnlyDungeon dungeon) throws IllegalArgumentException;

  /**
   * Show popup to acquire configuration for new game.
   */
  void acquireDungeonConfig();

  /**
   * Show popup to acquire arrow distance for shooting action.
   *
   * @return user entered integer value.
   * @throws NumberFormatException if user enters non-Integer value.
   */
  int acquireArrowDistance() throws NumberFormatException;

  /**
   * Shows the help message for using this game.
   */
  void showHelp();
}
