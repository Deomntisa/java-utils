package net.lamgc.utils.base;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;

public class ArgumentsPropertiesTest{

    private final String[] argsList = {"-c", "34", "-d:56", "--EF", "78", "--GH:90", "--KL", "-MN"};

    private final static HashMap<String, String> valueList = new HashMap<>();

    static {
        valueList.put("c", "34");
        valueList.put("d", "56");
        valueList.put("EF", "78");
        valueList.put("GH", "90");
        valueList.put("KL", "");
        valueList.put("MN", "");
    }


    @Test
    public void loadTest(){
        Logger log = LoggerFactory.getLogger(ArgumentsPropertiesTest.class.getSimpleName());
        StringBuilder argsStrBuilder = new StringBuilder();
        for (String item : argsList) {
            argsStrBuilder.append(item).append(" ");
        }
        //解析参数
        new ArgumentsProperties();
        ArgumentsProperties argsProperties = new ArgumentsProperties(argsList);
        argsProperties.load(argsStrBuilder.toString());
        log.info(Arrays.toString(argsProperties.getKeysWithFlag().toArray(new String[0])));
        log.info(Arrays.toString(argsProperties.getRawArguments()));
        for(String key : argsProperties.getKeys().toArray(new String[0])){
            String value =  argsProperties.getValue(key);
            log.info("{}: {}", key, value == null ? "(null)" : value);
            Assert.assertEquals(valueList.get(key), value);
        }
        Assert.assertNull(argsProperties.getValue("test"));
    }

    @Test
    public void customFlagTest() {
        String[] args = new String[] {"*a=1", "*b", "5"};
        ArgumentsProperties argsProp = new ArgumentsProperties(args, new String[] {"*"});
        Assert.assertEquals("1", argsProp.getValue("a"));
        Assert.assertEquals("5", argsProp.getValue("b"));
    }

}

