package kyh;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
* Tests that PewPew has random movement.
*
* @author Keone Hiraide
*/
public class TestPewPewRandomMovement extends RobotTestBed {
  /** 
   * True if our robot has seemingly random movement in battle. 
   */
  private boolean randomMovement = true;
  
  /**
   * Used to store this robot's previous x coordinate.
   */
  private double oldX = -9999;
  
  /**
   * Used to store this bot's previous y coordinate.
   */
  private double oldY = -9999;
  
  /**
   * If this bot has the same x and y coordinate in a row for its turns, this
   * will increase in size.
   */
  private int timesInARow = 0;

  /**
  * Specifies that SittingDuck and PewPew are to be matched up in this test case.
  *
  * @return The comma-delimited list of robots in this match.
  */
  @Override
  public String getRobotNames() {
    return "sample.SittingDuck, kyh.PewPew";
  }

  /**
  * This test runs for 10 round.
  *
  * @return The number of rounds.
  */
  @Override
  public int getNumRounds() {
    return 10;
  }

  /**
   * After each turn, check to see if we're at a corner. If so, set the corresponding flag.
   *
   * @param event Info about the current state of the battle.
   */
  @Override
  public void onTurnEnded(TurnEndedEvent event) {
    IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];
    if (event.getTurnSnapshot().getTurn() == 0) {
      this.oldX = robot.getX();
      this.oldY = robot.getY();
    }
    else {
      if (this.oldX == robot.getX() && this.oldY == robot.getY()) {
        this.timesInARow++;
      }
      else {
        this.timesInARow = 0;
      }
    }
    if (this.timesInARow > 30) {
      this.randomMovement =  false;
    }
    this.oldX = robot.getX();
    this.oldY = robot.getY();
   
    assertTrue("oldX initial value check", this.oldX != -9999);
    assertTrue("oldY initial value check", this.oldY != -9999);
  }

  /**
   * After the battle, check to see that our robot has random velocity.
   *
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue("Random Movement Check (" + this.timesInARow + ")", 
        this.randomMovement);
  }
}