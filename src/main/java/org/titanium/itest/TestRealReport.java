package org.titanium.itest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(RealTimeReport.class)
public class TestRealReport {
    @Test
    public void testone(){
        Assert.assertTrue(true);
    }

    @Test
    public void testtwo(){
        Assert.assertTrue(false);
    }
    @Test(dependsOnMethods = {"testtwo"})
    public void testthree(){
        Assert.assertTrue(true);
    }


}
