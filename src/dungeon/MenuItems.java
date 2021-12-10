package dungeon;

import java.util.Locale;

/**
 * Represents the menu items available in menu bar of a GUI for the dungeon game.
 */
public enum MenuItems {
  NEW_GAME("New Game"), RESTART("Restart Same Dungeon"),
  RESTART_CONFIG("Restart Same Config"), HELP("Help"), QUIT("Quit");

  private final String text;

  MenuItems(String text) {
    this.text = text;
  }

  /**
   * Returns the shorthand representation of this command.
   *
   * @return shorthand representation of this command.
   */
  public String getText() {
    return this.text;
  }

  /**
   * Returns the MenuItem for the provided text.
   *
   * @param text the shorthand whose MenuItem needs to be retrieved.
   * @return the MenuItem for the provided text representation
   * @throws IllegalArgumentException if the provided text is invalid.
   */
  public static MenuItems getByText(String text) throws IllegalArgumentException {
    if (text == null) {
      throw new IllegalArgumentException("Provided value cannot be null");
    }
    text = text.toUpperCase(Locale.ROOT);
    switch (text) {
      case "NEW GAME": {
        return NEW_GAME;
      }
      case "RESTART SAME DUNGEON": {
        return RESTART;
      }
      case "RESTART SAME CONFIG": {
        return RESTART_CONFIG;
      }
      case "HELP": {
        return HELP;
      }
      case "QUIT": {
        return QUIT;
      }
      default: {
        throw new IllegalArgumentException("No value for this shorthand");
      }
    }
  }
}
