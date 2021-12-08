package dungeon;


import java.io.IOException;

import randomizer.Randomizer;

public interface DungeonGUIController {

  /**
   * Starts the run of a dungeon game.
   *
   * @throws IOException              if an I/O error occurs.
   * @throws IllegalArgumentException if the given model is null.
   */
  public void play() throws IOException, IllegalArgumentException;

  void setView(DungeonView view);

  void setGameConfig(int rows, int columns, boolean wrapped,
                     int interconnectivity, int treasureAndArrowPercentage, int numberOfMonsters,
                     Randomizer randomizer);
}
