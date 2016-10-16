package com.lsh.lshsidebardemo;

import com.lsh.lshsidebardemo.util.PinYin4jUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hua on 2016/10/16.
 */
public class PinYin4jUtilTest {
    private PinYin4jUtil pinYin4jUtil;

    @Before
    public void setup() {
        pinYin4jUtil = new PinYin4jUtil();
    }

    @Test
    public void convertHeadChar() throws Exception {
        Assert.assertEquals("Z", pinYin4jUtil.convertHeadChar(" 张三"));
        Assert.assertEquals("Z", pinYin4jUtil.convertHeadChar(" z"));
        Assert.assertEquals("D", pinYin4jUtil.convertHeadChar(" d"));
        Assert.assertEquals("#", pinYin4jUtil.convertHeadChar("12"));
        Assert.assertEquals("#", pinYin4jUtil.convertHeadChar("  "));
        Assert.assertEquals("#", pinYin4jUtil.convertHeadChar(null));
    }

    @Test
    public void getStrAllPinYin() throws Exception {
        Assert.assertEquals("ZHANGSAN", pinYin4jUtil.getStrAllPinYin(" 张三"));
        Assert.assertEquals("#1SAN", pinYin4jUtil.getStrAllPinYin(" 1三"));
        Assert.assertEquals("Ddg", pinYin4jUtil.getStrAllPinYin("ddg"));
        Assert.assertEquals("#", pinYin4jUtil.getStrAllPinYin(" "));
        Assert.assertEquals("#", pinYin4jUtil.getStrAllPinYin(null));
    }

    @Test
    public void toUpperCase() throws Exception {
        Assert.assertEquals("#3", pinYin4jUtil.toUpperCase('3'));
        Assert.assertEquals("E", pinYin4jUtil.toUpperCase('e'));
        Assert.assertEquals("E", pinYin4jUtil.toUpperCase('e'));
    }

    @Test
    public void getPinYinArray() throws Exception {

    }

}