package dungeon;

import javax.swing.JLabel;

//package-private class
class CustomDungeonGridLabel extends JLabel {

  private int xCoord;
  private int yCoord;

  public int getXCoord() {
    return xCoord;
  }

  public void setXCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public int getYCoord() {
    return yCoord;
  }

  public void setYCoord(int yCoord) {
    this.yCoord = yCoord;
  }
}
