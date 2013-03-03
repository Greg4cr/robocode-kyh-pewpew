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
public class TestPewPewRandomVelocity extends RobotTestBed {
  /** 
   * True if our robot has moved randomly throughout the whole battle. 
   */
  private boolean randomVelocity = true;
  
  /**
   * We will store the robot's previous velocity and compare it with its current 
   * velocity.
   */
  private double oldMovementVelocity;
  
  /**
   * We should have random movement velocity after every 60 game turns.
   * If we have the same velocity 3 times in a row, then our test will fail.
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
    // Get the snapshot of the robot daCruzer
    IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];
    if (event.getTurnSnapshot().getTurn() == 1) { // First turn, save the coordinates of our robot.
      this.oldMovementVelocity = robot.getVelocity();
    }
    // After every 60 turns, our movement velocity most likely
    // will be be different.
    if ((event.getTurnSnapshot().getTurn() % 60) == 0 && 
        this.oldMovementVelocity == robot.getVelocity()) {
        this.timesInARow++;
    }
    
    // After 60 turns our velocity should have changed, so we want to set this as the
    // new previous velocity. 
    if ((event.getTurnSnapshot().getTurn() % 60 == 0)) {
      this.oldMovementVelocity = robot.getVelocity();
    }
    
    if (this.timesInARow > 3) { // If we had the same velocity more than 3 times in a row.
      this.randomVelocity = false;
    }
  }

  /**
   * After the battle, check to see that our robot has random velocity.
   *
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue("Random Movement Check", this.randomVelocity);
  }

}