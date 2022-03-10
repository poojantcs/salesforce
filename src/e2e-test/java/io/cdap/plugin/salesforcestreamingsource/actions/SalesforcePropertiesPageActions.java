/*
 * Copyright © 2022 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.salesforcestreamingsource.actions;

import io.cdap.e2e.utils.AssertionHelper;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.salesforcestreamingsource.locators.SalesforcePropertiesPage;
import io.cdap.plugin.utils.enums.SOQLQueryType;
import io.cdap.plugin.utils.enums.SalesforceStreamingSourceProperty;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Salesforce source plugins - Actions.
 */
public class SalesforcePropertiesPageActions {
    private static final Logger logger = LoggerFactory.getLogger
            (io.cdap.plugin.salesforcestreamingsource.actions.SalesforcePropertiesPageActions.class);

    static {
        SeleniumHelper.getPropertiesLocators(SalesforcePropertiesPage.class);
    }


    public static void fillReferenceName(String referenceName) {
        logger.info("Fill Reference name: " + referenceName);
        SalesforcePropertiesPage.referenceInput.sendKeys(referenceName);
    }


    private static void fillTopicName(String topicName) {
        SalesforcePropertiesPage.topicnameInput.sendKeys(topicName);
    }


    public static void configureSalesforcePluginForTopicName(String topicName) {
        String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
        fillReferenceName(referenceName);
        fillTopicName(topicName);

    }

    public static void fillSOQLPropertyField(SOQLQueryType queryType) {
        logger.info("Fill SOQL Query field for Type: " + queryType + " using the Query: " + queryType.query);
        SalesforcePropertiesPage.topicqueryInput.sendKeys(queryType.query);
    }

    public static void configureSalesforcePluginForPushTopicQuery(SOQLQueryType queryType) {
        String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
        fillReferenceName(referenceName);
        fillSOQLPropertyField(queryType);
    }

    public static void selectNotifyOnCreateOption(String onCreateOption) {
        SalesforcePropertiesPage.notifyoncreateDropdown.click();
        SalesforcePropertiesPage.getDropdownOptionElement(onCreateOption).click();

    }

    public static void selectNotifyOnUpdateOption(String onUpdateOption) {
        SalesforcePropertiesPage.notifyonupdateDropdown.click();
        SalesforcePropertiesPage.getDropdownOptionElement(onUpdateOption).click();

    }

    public static void selectNotifyOnDeleteOption(String onDeleteOption) {
        SalesforcePropertiesPage.notifyonDeleteDropdown.click();
        SalesforcePropertiesPage.getDropdownOptionElement(onDeleteOption).click();

    }

    public static void selectNotifyForFieldOption(String forFieldOption) {
        SalesforcePropertiesPage.notifyForFieldsDropdown.click();
        SalesforcePropertiesPage.getDropdownOptionElement(forFieldOption).click();

    }

    public static void verifyRequiredFieldsMissingValidationMessage(SalesforceStreamingSourceProperty propertyName) {

        WebElement element =SalesforcePropertiesPage.getPropertyInlineErrorMessage(propertyName);

        AssertionHelper.verifyElementDisplayed(element);
        AssertionHelper.verifyElementContainsText(element, propertyName.propertyMissingValidationMessage);
    }
}







