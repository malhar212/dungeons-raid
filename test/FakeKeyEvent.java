import java.awt.event.KeyEvent;
import javax.swing.JLabel;

/**
 * Represents a fake KeyEvent used to trigger controller in a testing environment.
 */
public class FakeKeyEvent extends KeyEvent {

  /**
   * Creates a fake key event.
   */
  public FakeKeyEvent(int mask, int keyCode, char keyChar) {
    super(new JLabel(), 1,1, mask, keyCode, KeyEvent.getKeyText(keyCode).charAt(0));
  }
}
