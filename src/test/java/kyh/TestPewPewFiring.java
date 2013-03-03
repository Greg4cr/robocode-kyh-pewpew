package kyh;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IBulletSnapshot;
import robocode.control.testing.RobotTestBed;
import robocode.util.Utils;

/**
 * This tests to make sure that PewPew bot always fires at max power, every time.
 * 
 * @author Keone Hiraide
 */
public class TestPewPewFiring extends RobotTestBed {

  private boolean firePower3 = false;

  /**
   * Specifies that SittingDuck and PewPew are to be matched up in this test case.
   * 
   * @return The comma-delimited list of robots in this match.
   */
  @Override
  public String getRobotNames() {
    return "kyh.PewPew,kyh.PewPew";
  }

  /**
   * This test runs for 10 rounds.
   * 
   * @return The number of rounds.
   */
  @Override
  public int getNumRounds() {
    return 10;
  }

  /**
   * At the end of each turn, checks the power of all bullets moving across the battlefield. Checks
   * to see if there we are firing at max power, every time.
   * 
   * @param event Info about the current state of the battle.
   */
  @Override
  public void onTurnEnded(TurnEndedEvent event) {

    // All active bullets belong to DaCruzer since SittingDuck does not fire.
    IBulletSnapshot bullets[] = event.getTurnSnapshot().getBullets();
    double bulletPower;

    for (int i = 0; i < bullets.length; i++) {

      bulletPower = bullets[i].getPower();

      if (Utils.isNear(3.0, bulletPower)) {
        this.firePower3 = true;
      }
      else if (Utils.isNear(1.0, bulletPower)) {
        this.firePower3 = true;
      }
      else if (bulletPower > 0.0) {
        this.firePower3 = true;
      }
      else {
        fail("Should not fire bullet with power of " + bulletPower);
      }
    }
  }

  /**
   * After running all matches, determine if PewPew fired at max power, every time.
   * 
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue("Should be able to fire bullet with a power of 3", this.firePower3);
  }
}