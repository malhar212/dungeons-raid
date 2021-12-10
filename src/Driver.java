import java.io.IOException;
import java.io.InputStreamReader;

import dungeon.Dungeon;
import dungeon.DungeonConsoleController;
import dungeon.DungeonController;
import dungeon.DungeonGraphicalController;
import dungeon.DungeonGraphicalView;
import dungeon.DungeonModel;
import dungeon.DungeonSwingController;
import dungeon.DungeonView;
import randomizer.GameRandomizer;
import randomizer.Randomizer;

/**
 * Driver class that acts as controller for the Dungeon.
 */
public class Driver {

  /**
   * Driver method to run the application.
   *
   * @param args Arguments to the main method.
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0 || args[0].equalsIgnoreCase("true")) {
      DungeonView view = new DungeonGraphicalView();
      DungeonGraphicalController guiController = new DungeonSwingController();
      guiController.setView(view);
      guiController.play();
    }
    else if (args[0].equalsIgnoreCase("false") && args.length == 7) {
      System.out.println("Please enter all parameters");
      int rows = 0;
      int columns = 0;
      int interconnectivity = 0;
      int treasurePercentage = 0;
      int numberOfMonsters = 0;
      try {
        rows = Integer.parseInt(args[1]);
        columns = Integer.parseInt(args[2]);
        interconnectivity = Integer.parseInt(args[4]);
        treasurePercentage = Integer.parseInt(args[5]);
        numberOfMonsters = Integer.parseInt(args[6]);
      } catch (NumberFormatException e) {
        System.out.println(
                "Rows, Columns, Interconnectivity, Treasure Percentage, Number of monsters have to "
                        + "be Integers");
        System.exit(0);
      }
      if (!(args[3].equalsIgnoreCase("true")
              || args[3].equalsIgnoreCase("false"))) {
        System.out.println(
                "Wrapped has to be true or false");
        System.exit(0);
      }
      boolean wrapped = Boolean.parseBoolean(args[3]);
      Randomizer randomizer = new GameRandomizer();
      Dungeon dungeon = new DungeonModel(
              rows, columns, wrapped, interconnectivity, treasurePercentage, numberOfMonsters,
              randomizer);
      DungeonController controller = new DungeonConsoleController(
              new InputStreamReader(System.in), System.out);
      controller.play(dungeon);
    }
    else if (args[0].equalsIgnoreCase("false") && args.length < 7) {
      System.out.println("Please provide all the parameters for text based game");
    }
  }
}
