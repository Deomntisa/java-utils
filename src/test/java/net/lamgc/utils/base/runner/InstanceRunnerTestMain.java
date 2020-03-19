package net.lamgc.utils.base.runner;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

public class InstanceRunnerTestMain {

    private final int id = new Random().nextInt();

    private final Logger log = LoggerFactory.getLogger("RunnerMain-id" + id);

    public static void main(String[] args) {
        ArgumentsRunner.runInThisClass(new InstanceRunnerTestMain(), args);
    }

    @Command(commandName = "help", defaultCommand = true)
    public static Boolean noParameterDefaultCommand() {
        System.out.println("Usage: java -jar jarFile <Command> [Arguments...]");
        return Boolean.TRUE;
    }

    @Command(commandName = "test-01")
    public void test_01(
            @Argument(name = "propName") String propName,
            @Argument(name = "length") int length,
            @Argument(name = "port") short port,
            @Argument(name = "percentage", force = false, defaultValue = "0.0") float percentage,
            @Argument(name = "bool") boolean bool,
            @Argument(name = "pi", force = false, defaultValue = "3.1415926") double pi,
            @Argument(name = "bool2") boolean bool2
    ) {
        log.info("PropName: {}, Length: {}, port: {}, percentage: {}, bool: {}, pi: {}, bool2: {}", propName, length, port, percentage, bool, pi, bool2);
    }

    @Command(commandName = "printTime")
    public void printTimeByTimeStamp(@Argument long timeStamp) {
        log.info("Time: {}", new Date(timeStamp));
    }

    @Command
    public void implicitCommandName() {
        log.info("test_02被调用");
    }

    @Command(commandName = "forceParamTest")
    public void forceParamTest(@Argument(name = "arg1") String arg1) {
        Assert.fail();
    }

    @Command
    public void preParameterNullTest(
            @Argument(name = "nullParam1", force = false) String arg1,
            @Argument(name = "nonNullParam1", force = false) String arg2,
            @Argument(name = "nullParam2", force = false) String arg3,
            @Argument(name = "nonNullParam2", force = false) String arg4,
            @Argument(name = "nonNullParam3", force = false) String arg5,
            @Argument(name = "nullParam3", force = false) String arg6
    ) {
        Assert.assertNull("nullParam1" ,arg1);
        Assert.assertNotNull("nonNullParam1", arg2);
        Assert.assertNull("nullParam2" ,arg3);
        Assert.assertNotNull("nonNullParam2", arg4);
        Assert.assertNotNull("nonNullParam3", arg5);
        Assert.assertNull("nullParam3" ,arg6);
    }

    @Command
    public String ignoredCommandCaseTest() {
        log.info("ignoredCommandCaseTest调用成功");
        return "ignoredCommandCase";
    }

    @Command
    public boolean customTrueFlagTest(@Argument(name = "flag") boolean flag) {
        return flag;
    }

    @Command
    public int strictDefaultCheckTest(@Argument(name = "num", force = false) int num) {
        return num;
    }

}
