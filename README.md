robocode-kyh-pewpew
=====================

Example project showing how to develop robocode robots using the Maven build system.  To install this package:

1. Install robocode
-------------------

Install both the robocode basic distribution and the robocode testing plugin.  Both of these setup.jar files are available for version 1.9.3.5 [here](https://sourceforge.net/projects/robocode/files/robocode/1.9.3.5/) 

Be sure to run the system manually from the command line to ensure that you have the appropriate Java installed and so forth. 

2. Install Maven
----------------

Start by following the [directions on installing Maven](http://maven.apache.org/download.cgi).

Be sure to run mvn --version to verify that it is correctly installed.  This package has been tested using Maven 3.0.4.

3. Download this robocode-kyh-pewpew package
----------------------------------------------

For those who do not know about git, the easiest way is to click the "ZIP" button at the top of this page, which will download the latest version of this repository as a .zip file. 

For those who know about git, you will want to clone it. 

5.  Build and test the system
-----------------------------

Now that everything is installed, build and test the system. You use the standard Maven 'test' target.  There are two special aspects of this process of which you should be aware.  

First, the RobotTestBed class and the Maven POM file requires the definition of a System Property called "robocode.home", which should point to the directory where Robocode is installed. To define this property and its value, use the -Drobocode.home=robocodeHomeDirectory command line option, as illustrated below.

Second, the Robocode runtime system needs your newly developed robot to be known to the system during testing.  To accomplish this, the pom.xml file defines a "copy-resource" goal that copies your robot binary from the target/classes directory to the robocode.home/robots directory after completing compilation. Take a look at the pom.xml file to see how this is done.  

Note that this approach does not remove these files from the robocode installation after the testing process is done. 

Here is an example of the command line used to build and test the system, along with the output.

```shell
[~/projecthosting/github/robocode-kyh-pewpew]-> mvn -Drobocode.home=/Users/keone/robocode test
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building pewpew 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ pewpew ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/keone/projecthosting/github/robocode-kyh-pewpew/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ pewpew ---
[INFO] Compiling 1 source file to /Users/keone/projecthosting/github/robocode-kyh-pewpew/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:copy-resources (copy-resources) @ pewpew ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ pewpew ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/keone/projecthosting/github/robocode-kyh-pewpew/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:testCompile (default-testCompile) @ pewpew ---
[INFO] Compiling 3 source files to /Users/keone/projecthosting/github/robocode-kyh-pewpew/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.10:test (default-test) @ pewpew ---
[INFO] Surefire report directory: /Users/keone/projecthosting/github/robocode-kyh-pewpew/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running kyh.TestpewpewFiring
Loaded net.sf.robocode.api
Loaded net.sf.robocode.core
Loaded net.sf.robocode.battle
Loaded net.sf.robocode.repository
Loaded net.sf.robocode.host
Preparing battle...
----------------------
Round 1 initializing..
Let the games begin!
..
Round 1 cleaning up.

(test output deleted)

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.924 sec
Running kyh.TestpewpewMovement
Preparing battle...
----------------------
Round 1 initializing..
Let the games begin!
..
Round 1 cleaning up.

(test output deleted)

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.693 sec
Running kyh.TestpewpewVersusSittingDuck
Preparing battle...
----------------------
Round 1 initializing..
Let the games begin!
..
Round 1 cleaning up.

(test output deleted) 

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.62 sec

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 5.346s
[INFO] Finished at: Mon Jan 28 14:44:59 HST 2013
[INFO] Final Memory: 16M/309M
[INFO] ------------------------------------------------------------------------
```

To remove the robot from the robocode installation, invoke the clean target with the -D property:

```shell
[~/projecthosting/github/robocode-kyh-pewpew]-> mvn -Drobocode.home=/Users/keone/robocode clean
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building pewpew 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ pewpew ---
[INFO] Deleting /Users/keone/projecthosting/github/robocode-kyh-pewpew/target
[INFO] Deleting /Users/keone/robocode/robots/kyh (includes = [], excludes = [])
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.305s
[INFO] Finished at: Tue Jan 29 07:00:17 HST 2013
[INFO] Final Memory: 6M/309M
[INFO] ------------------------------------------------------------------------
```
6.  Install robocode-kyh-pewpew into Eclipse
----------------------------------------------

Now that the system is running from the command line, you'll want to also run it from Eclipse.  To do so, bring up Eclipse, and select File | Import | Maven | Existing Maven Projects, and then complete the dialog boxes to import your project.  Eclipse will read the POM file in order to determine the libraries to include on the build path.  

To run the kyh.pewpew robot within Eclipse, you must configure Eclipse and Robocode in the normal way:
  * In the Run configuration, change the working directory to your Robocode installation directory. 
  * In the Robocode window, select Options | Preferences | Development Options to add the target/classes directory so that Robocode will see your robot.

To run the test cases, edit the Run configuration for each test to include -Drobocode.home=robocodeHomeDirectory as a VM argument. 

7. Use this project as a template for your own development
----------------------------------------------------------

Once you have completed the above steps, you are ready to use this project for your own development. To do this:
  * Duplicate the robocode-kyh-pewpew directory, and rename it with your own robot's name.
  * Edit the POM file, and change the top lines to correspond to your own robot name. 
  * Import the project (as a Maven project) into Eclipse.
  * In Eclipse, select the project (robocode-kyh-pewpew), then right-click and Refactor | Rename to rename the project to your own robot name.







