/*
 * Copyright (c) 2011 Bill Reh.
 *
 * This file is part of Content Management Faces.
 *
 * Content Management Faces is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package net.integration;

import org.junit.*;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * User: billreh
 * Date: 10/19/11
 * Time: 2:09 AM
 */
public class CmfClientTest {

    private WebDriver webDriver;

    @Before
    public void startDriver() {
        webDriver = new FirefoxDriver();
    }

    @After
    public void endDriver() {
        webDriver.quit();
    }

    @Ignore
    @Test
    public void testNavigateNamespace() {
        StartPage startPage = new StartPage(webDriver);

        NamespacePage namespacePage = startPage.goToNamespacePage("net");
        assertTrue(namespacePage.hasHeader("net"));
        namespacePage = startPage.goToNamespacePage("tralfamadore");
        assertTrue(namespacePage.hasHeader("net.tralfamadore"));
        namespacePage = startPage.goToNamespacePage("page1");
        assertTrue(namespacePage.hasHeader("net.tralfamadore.site.page1"));
    }

    @Ignore
    @Test
    public void testAddRemoveNamespace() throws Exception {
        String namespaceName = "com";
        StartPage startPage = new StartPage(webDriver);

        assertNull(SeleniumUtils.findLinkByText(webDriver, namespaceName));

        NamespacePage namespacePage = startPage.goToNamespacePage("page1");

        namespacePage.addNamespace(namespaceName);

        assertNotNull(SeleniumUtils.findLinkByText(webDriver, namespaceName));

        namespacePage = startPage.goToNamespacePage(namespaceName);

        namespacePage.removeNamespace(namespaceName);

        assertNull(SeleniumUtils.findLinkByText(webDriver, namespaceName));
    }
}
