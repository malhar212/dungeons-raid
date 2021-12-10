package dungeon;

import javax.swing.JFrame;

/**
 * A mock view of the dungeon for testing purposes.
 */
public class MockDungeonView extends JFrame implements DungeonView {

  private DungeonGraphicalController listener;
  private ReadOnlyDungeon dungeon;
  private final StringBuilder stringBuilder;

  /**
   * Creates an instance of mock view of the dungeon for testing purposes.
   */
  public MockDungeonView(StringBuilder stringBuilder) {
    listener = null;
    dungeon = null;
    this.stringBuilder = stringBuilder;
  }

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addListener(DungeonGraphicalController listener) {
    this.listener = listener;
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    //Do nothing
  }

  @Override
  public void showMessage(String message) {
    stringBuilder.append(message);
    stringBuilder.append("\n");
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    stringBuilder.append("Make visible called");
    stringBuilder.append("\n");
  }

  @Override
  public void setSource(ReadOnlyDungeon dungeon) {
    stringBuilder.append("Setting Source");
    if (this.dungeon != null) {
      boolean rowsMatch = this.dungeon.getMaze().size() == dungeon.getMaze().size();
      boolean columnMatch = this.dungeon.getMaze().get(0).size() == dungeon.getMaze().get(0).size();
      if (rowsMatch && columnMatch) {
        stringBuilder.append("Size Match");
      }
      boolean startRow =
              this.dungeon.getStartLocation().getRow() == dungeon.getStartLocation().getRow();
      boolean startColumn =
              this.dungeon.getStartLocation().getColumn() == dungeon.getStartLocation().getColumn();
      if (startRow && startColumn) {
        stringBuilder.append("Start Location Match");
      }
      else {
        stringBuilder.append("Start Location Don't Match");
      }
      boolean endRow =
              this.dungeon.getEndLocation().getRow() == dungeon.getEndLocation().getRow();
      boolean endColumn =
              this.dungeon.getEndLocation().getColumn() == dungeon.getEndLocation().getColumn();
      if (endRow && endColumn) {
        stringBuilder.append("End Location Match");
      }
      else {
        stringBuilder.append("End Location Don't Match");
      }
    }
    this.dungeon = dungeon;
  }

  @Override
  public void acquireDungeonConfig() {
    stringBuilder.append("Acquired Dungeon Config");
  }

  @Override
  public int acquireArrowDistance() {
    stringBuilder.append("Acquired Arrow Distance");
    return 1;
  }

  /**
   * Shows the help message for using this game.
   */
  @Override
  public void showHelp() {
    stringBuilder.append("You can use arrow keys or "
            + "mouse for navigation, 'A' to pickup arrows, 'T' to pickup treasure, 'Ctrl "
            + "+ arrow key' to shoot in that direction");
  }
}
