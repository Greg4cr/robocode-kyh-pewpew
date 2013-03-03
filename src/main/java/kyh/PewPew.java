package kyh;
import java.awt.Color;
import java.util.Random;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * This robot will sit still, pick one enemy and attempt to track it. 
 *
 * @author Keone Hiraide
 */
public class PewPew extends AdvancedRobot {
  /**
   * Flag which indicates whether this robot is moving forwards or backwards.
   */
  private boolean move;
  
  /**
   * Flag which indicates whether this robot is moving left or right.
   */
  private boolean turn;
  
  /**
   * Integer which Increments by one after every turn that passes in the game.
   * Once this integer equals turnsToChangeAngle, it will be reset to zero.
   */
  private int gameTurnsForAngle;
  
  /**
   * Integer which Increments by one after every turn that passes in the game.
   * Once this integer equals turnsToChangeMovement, it will be reset to zero.
   */
  private int gameTurnsForMovement;
  
  /**
   * Amount of turns we should reach before changing our turn angle.
   */
  
  /**
   * Sets various private member variables.
   */
  public PewPew() {
    this.move = false;
    this.turn = false;
    this.gameTurnsForAngle = 0;
    this.gameTurnsForMovement = 0;
  }
  /**
   * @param e event parameter.
   * @see robocode.Robot#onScannedRobot(robocode.ScannedRobotEvent)
   */
  @Override
  public void onScannedRobot(ScannedRobotEvent e) {
    double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
    setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - 
    getGunHeadingRadians() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - 
    absoluteBearing) / 13.0)));
   
    setFire(Rules.MAX_BULLET_POWER);
  }
  
  /**
   * Move in the opposite direction if this robot touches a wall.
   * @param event The event parameter.
   */
  @Override
  public void onHitWall(HitWallEvent event) {
    this.move = !this.move;
  }
  
  /**
   * Turn in the opposite direction if this robot gets hit by a bullet.
   * @param event The event parameter.
   */
  @Override
  public void onHitByBullet(HitByBulletEvent event) {
    this.turn = !this.turn;
  }
  
  /**
   * Robot will move forward or backwards depending the direction specified.
   * @return The movement amount that was produced randomly.
   */
  public double moveRandomly() {
    Random random = new Random();
    double movement = 2 + (12 - 2) * random.nextDouble();
    if (this.move) {
      this.setAhead(movement);
    }
    else {
      this.setBack(movement);
    }
    
    return movement;
  }
  
  /**
   * Robot will move left or right.
   * @return The angle that produced randomly.
   */
  public double turnRandomly() {
    Random random = new Random();
    double angle = 90 + (360 - 90) * random.nextDouble();
    if (this.turn) {
      this.setTurnRight(angle);
    }
    else {
      this.setTurnLeft(angle);
    }
    return angle;
  }
  
  /**
   * If we are close to a wall, move in the opposite direction.
   */
  public void avoidWalls() {
    double heightDiff = Math.abs(this.getBattleFieldHeight() - this.getY());
    double widthDiff = Math.abs(this.getBattleFieldWidth() - this.getX());
    if (heightDiff <= 80 || widthDiff <= 80) {
      this.move = !this.move;
    }
  }
  
  /**
   * Robot will keep turning until it scans and finds a robot.
   * 
   * @see robocode.Robot#run()
   */
  @Override
  public void run() {
    this.turnRadarRight(360);
    this.setAdjustRadarForRobotTurn(true);
    this.setAdjustGunForRobotTurn(true);
    this.setAdjustRadarForGunTurn(true);
    
    
    while (true) {
      setBodyColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
      setGunColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
      setRadarColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
      setBulletColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
      setScanColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
      
      this.setTurnRadarRight(360);
      if (this.gameTurnsForAngle ==  30) { // After every 30 game turns, change angles.
        this.turn = !this.turn;
        this.gameTurnsForAngle = 0;
      }
      
      if (this.gameTurnsForMovement == 60) { // After every 60 turns, change velocity.
        this.move = !this.move;
        this.gameTurnsForMovement = 0;
      }    
      turnRandomly();
      moveRandomly();
      this.gameTurnsForAngle++;
      this.gameTurnsForMovement++;
      this.execute();
    }
  }
  
} // End of PewPew class.
