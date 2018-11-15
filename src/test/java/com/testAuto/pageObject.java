package com.testAuto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class pageObject {
    private WebDriver browser;
    private  Code c;

    pageObject(WebDriver browser) {
        this.browser = browser;
        this.c = new Code(browser);
        PageFactory.initElements(browser, this);
    }
}
