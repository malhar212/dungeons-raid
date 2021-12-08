package dungeon;

import java.io.IOException;

import randomizer.Randomizer;

public class DungeonSwingController implements DungeonGUIController {
  private Dungeon dungeon;
  private DungeonView view;

  /**
   * Starts the run of a dungeon game.
   *
   * @throws IOException              if an I/O error occurs.
   * @throws IllegalArgumentException if the given model is null.
   */
  @Override
  public void play() throws IOException, IllegalArgumentException {
  }

  @Override
  public void setView(DungeonView view) {
    this.view = view;
    view.addClickListener(this);
    view.makeVisible();
  }

  @Override
  public void setGameConfig(int rows, int columns, boolean wrapped, int interconnectivity,
                            int treasureAndArrowPercentage, int numberOfMonsters,
                            Randomizer randomizer) {

  }
}
