package com.myobservatory.testcases.navigation;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.myobservatory.testcases.business.Weather;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;


import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

/**
 *
 */
public class ForecastService {
    //create a java client appiumdriver
    private AppiumDriver<WebElement> appiumDriver;
    @BeforeTest
    public void setup(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android"); //指定测试平台
        capabilities.setCapability("deviceName", "02157df2c8ee9535");
        capabilities.setCapability("platformVersion", "7.0");
        //指定启动包名和首屏activity
        capabilities.setCapability("appPackage","hko.MyObservatory_v1_0");
        capabilities.setCapability("appActivity","hko.MyObservatory_v1_0.AgreementPage");
        capabilities.setCapability("autoLaunch",true);
        //等待界面展示
        capabilities.setCapability("appWaitActivity","hko.homepage.Homepage2Activity");
        capabilities.setCapability("sessionOverride",true);
        capabilities.setCapability("noReset",true);
        //创建个androidDriver
        try {
            appiumDriver =  new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void nineDayForecast() {
        appiumDriver.findElementByAccessibilityId("Navigate up").click();

        /**
         * 滚动到九天预报item并点击
         */
        Actions actions = new Actions(appiumDriver);
        appiumDriver.findElement(MobileBy.ByAndroidUIAutomator.AndroidUIAutomator
                ("new UiScrollable(new UiSelector().resourceId(\"hko.MyObservatory_v1_0:id/left_drawer\"))" +
                        ".scrollIntoView(new UiSelector().text(\"9-Day Forecast\"))")).click();

        /**
         * Assert 当前界面是否已选择 9天天气预报
         */
        Assert.assertTrue(appiumDriver.findElement(MobileBy.ByAndroidUIAutomator
                .AndroidUIAutomator("new UiSelector().text(\"9-Day Forecast\")")).isSelected());

        //获取当前Location 时间
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();
        String currentDate = String.valueOf(date);
        String[] dateString = currentDate.split(" ");
        String currentDayMonth = dateString[2]+" "+dateString[1];
        //计算并得到第9天的时间
        String nineDate = String.valueOf(getNineDate(date,8));
        System.out.print("XXXXXXXXXX"+nineDate);
        String[] nineDateString = nineDate.split(" ");
        String nineDateDayMonth = nineDateString[2]+" "+nineDateString[1];
        //定义一个weatherItemList
        ArrayList<HashMap<String,String>> weatherItemList = new ArrayList<HashMap<String,String>>();




            HashMap<String,String> weatherItemMap = new HashMap<String,String>();
            //获取当前listview已展示item的个数
            int visibleItemWeather = appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                    .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).size();

            /**
             * 滚动到最底下的item
             */
            appiumDriver.findElement(MobileBy.ByAndroidUIAutomator.AndroidUIAutomator
                    ("new UiScrollable(new UiSelector().resourceId(\"hko.MyObservatory_v1_0:id/mainAppSevenDayView\")).scrollIntoView(new UiSelector()."+
                            "text("+"\""+nineDateDayMonth+"\""+"))"));
/**
 * 1.判断某个元素是否存在，这个可以封装成一个方法
 * 2.返回某个元素的text也可以封装成方法
 */
            //判断当前item是否完整
            for(int i = 0 ;i<visibleItemWeather;i++){
                //判断当前界面的item 是否完整。
                if(appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                        .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_date")).isDisplayed() && appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                        .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_details")).isDisplayed()){
                    weatherItemMap.put("date",appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                            .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_date")).getText());
                    weatherItemMap.put("dateofweek",appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                            .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_day_of_week")).getText());
                    weatherItemMap.put("iconofweather",appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                            .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_Icon")).toString());
                    weatherItemMap.put("temperature",appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                            .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_temp")).getText());
                    weatherItemMap.put("humidity",appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                            .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_rh")).getText());
                    weatherItemMap.put("wind",appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                            .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_wind")).getText());
                    weatherItemMap.put("details",appiumDriver.findElementById("hko.MyObservatory_v1_0:id/mainAppSevenDayView")
                            .findElements(By.id("hko.MyObservatory_v1_0:id/sevenDayLinearLayout")).get(i).findElement(By.id("hko.MyObservatory_v1_0:id/sevenday_forecast_details")).getText());
                    System.out.println("^^^^^^^^^^^^^"+weatherItemMap);
                    //断言item 的信息是不是都有展示
                    for(String key:weatherItemMap.keySet()){
                        Assert.assertNotNull(weatherItemMap.get(key));
                    }
                    weatherItemList.add(weatherItemMap);
                }else continue;
            }
    }

    @AfterTest
    public void teardown(){
        appiumDriver.quit();
    }
    public static Date getNineDate(Date date,int day){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+day);
        return calendar.getTime();
    }

    /**
     * description：定位到元素坐标，屏幕往下滚
     * 网上推荐的方法，不过没效果，原因还在查找中...
     */
    public void swipeScreenDown(AppiumDriver<WebElement> driver,String targetElementId,int moveX,int moveY){
        WebElement elem = driver.findElement(By.id(targetElementId));
        Duration duration = Duration.ofNanos(1000);


        Point start = elem.getLocation();
        TouchAction ta = new TouchAction(driver);

        //当前起点
        int startX = start.x;
        int startY = start.y;

        Dimension q = elem.getSize();
        int x = q.getWidth();
        int y = q.getHeight();
        // 计算出控件结束坐标
        int endX = x + startX;
        int endY = y + startY;

        // 计算中间点坐标
        int centreX = (endX + startX) / 2;
        int centreY = (endY + startY) / 2;

        System.out.print("*******"+startX+" "+startY);
        ta.press(startX,startY).waitAction(duration).moveTo(moveX,moveY).release().perform();
    }

    public static void swipeToUp(AppiumDriver<WebElement> driver, WebElement element) {
        //元素起始x和y坐标
        Point point=element.getLocation();
        int startX=point.x;
        int startY=point.y;
        System.out.println("元素起始x="+startX+",元素起始y="+startY);
        //计算元素的宽和高
        Dimension dimension=element.getSize();
        int width=dimension.getWidth();
        int height=dimension.getHeight();
        System.out.println("元素宽width="+width+",元素高height="+height);
        //计算元素中间坐标
        int centerX=startX+width*1/2;
        int centerY=(startY+height)/2;
        System.out.println("元素中心点距边框宽centerX="+centerX+",高centerY="+centerY);
        int heightY=driver.manage().window().getSize().height;
        System.out.println("获取屏高==="+heightY);
        TouchAction action=new TouchAction(driver);
        action.press(centerX, centerY).moveTo(centerX, 0).release().perform();
    }
}
