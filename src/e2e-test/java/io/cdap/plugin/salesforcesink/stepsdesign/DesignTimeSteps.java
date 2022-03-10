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


package io.cdap.plugin.salesforcesink.stepsdesign;
import io.cdap.plugin.salesforce.plugin.source.batch.SalesforceWideRecordReader;
import io.cdap.plugin.salesforcesink.actions.SalesforcePropertiesPageActions;
import io.cdap.plugin.utils.enums.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

/**
 * Design-time steps of Salesforce plugins.
 */
public class DesignTimeSteps {

String INVALID_MAX_RECORDS = "1000000";
String INVALID_MAX_BYTES = "1000000000";

    @And("configure Salesforce sink for an SobjectName: {string}")
    public void configureSalesforceSinkForSobjectName(String sObjectName) {
        SalesforcePropertiesPageActions
                .configureSalesforcePluginForSobjectName(SObjects.valueOf(sObjectName));
    }

    @And("I Select option type for error handling as {}")
    public void selectOptionTypeForErrorHandling(ErrorHandlingOptions option) {
        SalesforcePropertiesPageActions.selectErrorHandlingOptionType(option.value);
    }

    @Then("I select operation type as {}")
    public void selectOperationType(OperationTypes operationType) {
        SalesforcePropertiesPageActions.selectOperationType(operationType.value);

    }

    @And("I select max Records Per Batch as: {string}")
    public void selectMaxRecordsPerBatchAs(String maxRecordsPerBatch) {
        SalesforcePropertiesPageActions.selectMaxRecords(maxRecordsPerBatch);
    }

    @And("I select max Bytes Per Batch as: {string}")
    public void SelectMaxBytesPerBatchAs(String maxBytesPerBatch) {
        SalesforcePropertiesPageActions.selectMaxBytes(maxBytesPerBatch);
    }

    @Then("verify required fields missing validation message for Sobject Name property")
    public void verifyRequiredFieldsMissingValidationMessageForSobjectNameProperty() {
        SalesforcePropertiesPageActions.verifyRequiredFieldsMissingValidationMessage(
        SalesforceSinkProperty.SOBJECT_NAME);
    }

    @Then("verify validation message for invalid Max Records Per Batch")
    public void verifyValidationMessageForInvalidMaxRecordsPerBatch() {
        SalesforcePropertiesPageActions.verifyInvalidMaxRecordsValidationmessage(
        SalesforceSinkProperty.MAXRECORDS_PER_BATCH);
    }

    @And("fill invalid max records per batch")
    public void fillInvalidMaxRecordsPerBatch() {
        SalesforcePropertiesPageActions.fillInvalidMaxRecords(INVALID_MAX_RECORDS);
    }

    @And("fill invalid max Bytes per batch")
    public void fillInvalidMaxBytesPerBatch() {
        SalesforcePropertiesPageActions.fillInvalidMaxBytes(INVALID_MAX_BYTES);
    }

    @Then("verify validation message for invalid Max Bytes Per Batch")
    public void verifyValidationMessageForInvalidMaxBytesPerBatch() {
        SalesforcePropertiesPageActions.verifyInvalidMaxBytesValidationmessage(
                SalesforceSinkProperty.MAXBYTES_PER_BATCH);
    }

}
