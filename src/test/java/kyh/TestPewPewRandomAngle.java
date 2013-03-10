package kyh;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
* Tests that PewPew has random turning.
*
* @author Keone Hiraide
*/
public class TestPewPewRandomAngle extends RobotTestBed {
  /** 
   * True if our robot has seemingly random turning in battle. 
   */
  private boolean randomAngle = true;
  
  /**
   * Used to store this robot's previous heading.
   */
  private double oldAngle = -9999;
  
  /**
   * If this bot has the same header in a row for a certain amount of 
   * times for for its turns, this variable will increase in size.
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
    if (event.getTurnSnapshot().getTurn() == 0) { // 
      // First turn, we will store our robot body's heading.
      this.oldAngle = robot.getBodyHeading();
    }
    else {
      // If our robot body has the same heading as its previous turn,
      // increment this variable.
      if (this.oldAngle == robot.getBodyHeading()) {
        this.timesInARow++;
      }
      else {
        this.timesInARow = 0; // reseting the times in a row variable.
      }
    }
    // Our body header was the same for 30 times in a row. 
    if (this.timesInARow > 100) {
      this.randomAngle =  false; // We aren't turning randomly.
    }
    this.oldAngle = robot.getBodyHeading(); // Preserving our robot's angle.
   
    // Initially our header value is -9999, it shouldn't be this.
    assertTrue("Old header initial value check", this.oldAngle != -9999);
  }

  /**
   * After the battle, check to see that our robot has random turning.
   *
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue("Random turning Check (" + this.timesInARow + ")", 
        this.randomAngle);
  }
}