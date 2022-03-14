import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;

import dungeon.DungeonGraphicalController;
import dungeon.DungeonSwingController;
import dungeon.DungeonView;
import dungeon.MockDungeonView;
import dungeon.Move;
import randomizer.GameRandomizer;
import randomizer.Randomizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Used for testing DungeonGraphicalController.
 */
public class DungeonGraphicalControllerTest {
  private DungeonGraphicalController guiController;
  private DungeonGraphicalController guiMockModelController;
  private Randomizer monsterRandomizer;
  private StringBuilder log;

  @Before
  public void setUp() {
    monsterRandomizer = new GameRandomizer(35, 33, 6, 6, 31, 4, 0, 32, 28, 12, 14, 7,
            14, 20, 4, 23, 11, 12, 11, 2, 10, 4, 4, 0, 2, 3, 1, 3, 9, 6, 7, 1, 4, 5, 2, 2, 1, 2, 1,
            0, 16, 1, 2, 4, 2, 1, 1, 4, 3, 2, 2, 5, 0, 1, 1, 1, 2, 1, 6, 2, 0, 5, 0, 1, 1, 2, 2, 3,
            1, 3, 0, 2, 2, 1, 0, 4, 5, 2, 1, 5, 0, 3, 11, 2, 0, 3, 13, 3, 6, 1, 0, 2, 11, 2, 12, 2,
            11, 1, 8, 3, 9, 2, 0, 2, 0, 7, 0);
    guiController = new DungeonSwingController();
    log = new StringBuilder();
    DungeonView view = new MockDungeonView(log);
    guiController.setView(view);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidPlay() {
    DungeonGraphicalController controller = new DungeonSwingController();
    controller.play();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetView() {
    guiController.setView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetGameConfigInvalidRow() {
    guiController.setGameConfig(0, 5, true, 10, 30, 10, new GameRandomizer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetGameConfigInvalidColumn() {
    guiController.setGameConfig(5, -5, true, 10, 30, 10, new GameRandomizer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetGameConfigInvalidInterconnectivity() {
    guiController.setGameConfig(5, 5, false, 30, 30, 10, new GameRandomizer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetGameConfigInvalidTreasurePercentage() {
    guiController.setGameConfig(5, 5, false, 0, 130, 10, new GameRandomizer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetGameConfigInvalidMonster() {
    guiController.setGameConfig(5, 5, false, 0, 30, 100, new GameRandomizer());
  }

  @Test(expected = IllegalStateException.class)
  public void testSetGameConfigInvalidRandomizer() {
    guiController.setGameConfig(5, 5, false, 0, 30, 5,null);
  }

  @Test
  public void testSetGameConfig() {
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    assertTrue(log.toString().contains("Welcome to the dungeons\nYou can use arrow keys or mouse "
            + "for navigation\nA "
            + "to pickup arrows\nT to pickup treasure\nCtrl + arrow key to shoot in that "
            + "direction"));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidStateSetGameConfig() {
    DungeonGraphicalController controller = new DungeonSwingController();
    controller.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidStatePlay() {
    DungeonGraphicalController controller = new DungeonSwingController();
    controller.play();
  }


  @Test
  public void testInvalidhandleCellClick() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    guiController.handleCellClick(null);
    assertFalse(log.toString().contains("Provided move is not a valid move"));
  }

  @Test
  public void handleInvalidCellClick() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    guiController.handleCellClick(Move.SOUTH);
    assertTrue(log.toString().contains("Provided move is not a valid move SOUTH"));
  }

  @Test
  public void handleCellClick() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    guiController.handleCellClick(Move.NORTH);
    assertFalse(log.toString().contains("Provided move is not a valid move"));
  }

  @Test
  public void testMoveKeyReleased() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_UP,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    assertFalse(log.toString().contains("Provided move is not a valid move"));
  }

  @Test
  public void testInvalidMoveKeyReleased() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_DOWN,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    assertTrue(log.toString().contains("Provided move is not a valid move SOUTH"));
  }

  @Test
  public void testInvalidShootingDirectionKey() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.CTRL_DOWN_MASK,KeyEvent.VK_DOWN,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    assertTrue(log.toString().contains("Provided direction is not a valid direction for current "
            + "location"));
  }

  @Test
  public void testShootingDirectionKey() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.CTRL_DOWN_MASK,KeyEvent.VK_UP,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    assertFalse(log.toString().contains("Provided direction is not a valid direction for current "
            + "location"));
    assertTrue(log.toString().contains("Acquired Arrow Distance"));
  }

  @Test
  public void testArrowPickup() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_A,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    assertTrue(log.toString().contains("You picked up arrows"));
  }

  @Test
  public void testTreasurePickup() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_T,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    assertTrue(log.toString().contains("You picked up some treasure"));
  }

  @Test
  public void testNoArrowPickup() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_LEFT,
            KeyEvent.CHAR_UNDEFINED);
    KeyEvent keyEventPickup = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_A,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    guiController.keyReleased(keyEventPickup);
    guiController.keyReleased(keyEventPickup);
    assertTrue(log.toString().contains("There are no arrows to pickup"));
  }

  @Test
  public void testNoTreasurePickup() {
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    KeyEvent keyEvent = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_LEFT,
            KeyEvent.CHAR_UNDEFINED);
    KeyEvent keyEventPickup = new FakeKeyEvent(InputEvent.BUTTON1_DOWN_MASK,KeyEvent.VK_T,
            KeyEvent.CHAR_UNDEFINED);
    guiController.keyReleased(keyEvent);
    guiController.keyReleased(keyEventPickup);
    assertTrue(log.toString().contains("There is no treasure to pickup"));
  }

  @Test
  public void actionPerformedNewGame() {
    ActionEvent event = new ActionEvent(new JLabel(),ActionEvent.ACTION_FIRST,"New Game");
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    guiController.actionPerformed(event);
    assertTrue(log.toString().contains("Acquired Dungeon Config"));
  }

  @Test
  public void actionPerformedRestartSameDungeon() {
    ActionEvent event = new ActionEvent(new JLabel(),ActionEvent.ACTION_FIRST,
            "Restart Same Dungeon");
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    guiController.actionPerformed(event);
    assertTrue(log.toString().contains("Setting Source"));
    assertTrue(log.toString().contains("Size Match"));
    assertTrue(log.toString().contains("Start Location Match"));
    assertTrue(log.toString().contains("End Location Match"));
  }

  @Test
  public void actionPerformedRestartSameConfig() {
    ActionEvent event = new ActionEvent(new JLabel(),ActionEvent.ACTION_FIRST,"Restart Same"
            + " Config");
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    guiController.actionPerformed(event);
    assertTrue(log.toString().contains("Setting Source"));
    assertTrue(log.toString().contains("Size Match"));
    assertTrue(log.toString().contains("Start Location Don't Match") || log.toString().contains(
            "End Location Don't Match"));
  }

  @Test
  public void actionPerformedHelp() {
    ActionEvent event = new ActionEvent(new JLabel(),ActionEvent.ACTION_FIRST,"Help");
    guiController.play();
    guiController.setGameConfig(
            5, 4, true, 4, 50, 3,
            monsterRandomizer);
    guiController.actionPerformed(event);
    assertTrue(log.toString().contains("You can use arrow keys or "
            + "mouse for navigation, 'A' to pickup arrows, 'T' to pickup treasure, 'Ctrl "
            + "+ arrow key' to shoot in that direction"));
  }
}