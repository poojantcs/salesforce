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

package io.cdap.plugin.salesforcesink.actions;

import io.cdap.e2e.utils.AssertionHelper;
import io.cdap.e2e.utils.ElementHelper;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.salesforcesink.locators.SalesforcePropertiesPage;
import io.cdap.plugin.utils.enums.OperationTypes;
import io.cdap.plugin.utils.enums.SObjects;
import io.cdap.plugin.utils.enums.SalesforceSinkProperty;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Salesforce sink plugins - Actions.
 */

public class SalesforcePropertiesPageActions{
    private static final Logger logger = LoggerFactory.getLogger
            (io.cdap.plugin.salesforcesink.actions.SalesforcePropertiesPageActions.class);

    static {
        SeleniumHelper.getPropertiesLocators(SalesforcePropertiesPage.class);
    }


    public static void fillReferenceName(String referenceName) {
        logger.info("Fill Reference name: " + referenceName);
        SalesforcePropertiesPage.referenceInput.sendKeys(referenceName);
    }


    private static void fillsObjectName(String sObjectName) {
        SalesforcePropertiesPage.sObjectNameInput.sendKeys(sObjectName);
    }

    public static void configureSalesforcePluginForSobjectName(SObjects sObjectName) {
        String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
        fillReferenceName(referenceName);
        fillsObjectName(sObjectName.value);

    }

    public static void selectOperationType(String operationType) {
        SalesforcePropertiesPage.getOperationType(operationType).click();
        verifyIfOperationTypeISUpsert(operationType);
    }

    public static void verifyIfOperationTypeISUpsert(String operationType) {

        if (operationType.equals(OperationTypes.UPSERT.value)) {
            fillUpsertExternalIdField();
        } else {
            return;
        }

    }
    public static void fillUpsertExternalIdField() {
        String id = "121";
        SalesforcePropertiesPage.upsertExternalIdFieldInput.sendKeys(id);
    }

    public static void selectErrorHandlingOptionType(String errorOption) {

        SalesforcePropertiesPage.errordropdown.click();
        SalesforcePropertiesPage.getErrorHandlingOptions(errorOption).click();
    }

    public static void selectMaxRecords(String maxRecords) {

        ElementHelper.replaceElementValue(SalesforcePropertiesPage.maxRecordsPerbatchInput,"");

        int inputMaxRecords = Integer.parseInt(maxRecords);
        String defaultRecords = "5000";

        if(inputMaxRecords < 1)
            SalesforcePropertiesPage.maxRecordsPerbatchInput.sendKeys(defaultRecords);


        else if(inputMaxRecords > 10000)
            SalesforcePropertiesPage.maxRecordsPerbatchInput.sendKeys(defaultRecords);

        else
            SalesforcePropertiesPage.maxRecordsPerbatchInput.sendKeys(maxRecords);


    }

    public static void selectMaxBytes(String maxBytesPerBatch) {

        ElementHelper.replaceElementValue(SalesforcePropertiesPage.maxBytesPerBatchInput,"");

        long inputMaxBytes = Integer.parseInt(maxBytesPerBatch);
        String defaultRecords = "100000";

        if(inputMaxBytes < 1)
            SalesforcePropertiesPage.maxBytesPerBatchInput.sendKeys(defaultRecords);


        else if(inputMaxBytes > 10000000)
            SalesforcePropertiesPage.maxBytesPerBatchInput.sendKeys(defaultRecords);

        else
            SalesforcePropertiesPage.maxBytesPerBatchInput.sendKeys(maxBytesPerBatch);


    }

    public static void verifyRequiredFieldsMissingValidationMessage(SalesforceSinkProperty sobjectName) {
        WebElement element = SalesforcePropertiesPage.getPropertyInlineErrorMessage(sobjectName);

        AssertionHelper.verifyElementDisplayed(element);
        AssertionHelper.verifyElementContainsText(element, sobjectName.propertyMissingValidationMessage);
    }

    public static void fillInvalidMaxRecords(String invalidMaxRecords) {
        ElementHelper.replaceElementValue(SalesforcePropertiesPage.maxRecordsPerbatchInput,"");
        SalesforcePropertiesPage.maxRecordsPerbatchInput.sendKeys(invalidMaxRecords);
    }


    public static void fillInvalidMaxBytes(String invalidMaxBytes) {
        ElementHelper.replaceElementValue(SalesforcePropertiesPage.maxBytesPerBatchInput,"");
        SalesforcePropertiesPage.maxBytesPerBatchInput.sendKeys(invalidMaxBytes);
    }


    public static void verifyInvalidMaxRecordsValidationmessage(SalesforceSinkProperty maxrecordsPerBatch) {
        WebElement element = SalesforcePropertiesPage.getPropertyInlineErrorMessage(maxrecordsPerBatch);

        AssertionHelper.verifyElementDisplayed(element);
        AssertionHelper.verifyElementContainsText(element, maxrecordsPerBatch.propertyMissingValidationMessage);
    }


    public static void verifyInvalidMaxBytesValidationmessage(SalesforceSinkProperty maxbytesPerBatch) {
        WebElement element = SalesforcePropertiesPage.getPropertyInlineErrorMessage(maxbytesPerBatch);

        AssertionHelper.verifyElementDisplayed(element);
        AssertionHelper.verifyElementContainsText(element, maxbytesPerBatch.propertyMissingValidationMessage);
    }





}
