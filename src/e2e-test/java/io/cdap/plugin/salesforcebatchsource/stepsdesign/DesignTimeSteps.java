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

package io.cdap.plugin.salesforcebatchsource.stepsdesign;

import io.cdap.e2e.pages.actions.CdfPluginPropertiesActions;
import io.cdap.e2e.utils.CdfHelper;
import io.cdap.plugin.salesforcebatchsource.actions.SalesforcePropertiesPageActions;
import io.cdap.plugin.utils.SchemaTable;
import io.cdap.plugin.utils.enums.SOQLQueryType;
import io.cdap.plugin.utils.enums.SObjects;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;

/**
 * Design-time steps of Salesforce plugins.
 */
public class DesignTimeSteps implements CdfHelper {
  String invalidSobjectName = "blahblah";

  @When("fill Reference Name property")
  public void fillReferenceNameProperty() {
    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    SalesforcePropertiesPageActions.fillReferenceName(referenceName);
  }

  @When("fill Authentication properties with invalid values")
  public void fillAuthenticationPropertiesWithInvalidValues() {
    SalesforcePropertiesPageActions.fillAuthenticationPropertiesWithInvalidValues();
  }

  @When("fill Authentication properties for Salesforce Admin user")
  public void fillAuthenticationPropertiesForSalesforceAdminUser() {
    SalesforcePropertiesPageActions.fillAuthenticationPropertiesForSalesforceAdminUser();
  }

  @When("configure Salesforce source for an SOQL Query of type: {string}")
  public void configureSalesforceForSoqlQuery(String queryType) {
    SalesforcePropertiesPageActions.configureSalesforcePluginForSoqlQuery(SOQLQueryType.valueOf(queryType));
  }

  @When("configure Salesforce source for an SObject Query of SObject: {string}")
  public void configureSalesforceForSObjectQuery(String sObjectName) {
    SalesforcePropertiesPageActions.configureSalesforcePluginForSObjectQuery(SObjects.valueOf(sObjectName));
  }

  @Then("verify the Output Schema table for an SOQL query of type: {string}")
  public void verifyOutputSchemaTableForSoqlQuery(String queryType) {
    SchemaTable schemaTable = SalesforcePropertiesPageActions.
      getExpectedSchemaTableForSOQLQuery(SOQLQueryType.valueOf(queryType));
    SalesforcePropertiesPageActions.verifyOutputSchemaTable(schemaTable);
  }

  @Then("verify the Output Schema table for an SObject Query of SObject: {string}")
  public void verifyOutputSchemaTableForSObjectQuery(String sObjectName) {
    SchemaTable schemaTable = SalesforcePropertiesPageActions.
      getExpectedSchemaTableForSObjectQuery(SObjects.valueOf(sObjectName));
    SalesforcePropertiesPageActions.verifyOutputSchemaTable(schemaTable);
  }

  @When("close plugin properties page")
  public void closePluginPropertiesPage() {
    SalesforcePropertiesPageActions.clickOnClosePropertiesPageButton();
  }

  @When("fill SOQL Query field with a Star Query")
  public void fillSoqlQueryFieldWithStarQuery() {
    SalesforcePropertiesPageActions.fillSOQLPropertyField(SOQLQueryType.STAR);
  }

  @When("fill SObject Name property with an invalid value")
  public void fillSObjectNameFieldWithInvalidValue() {
    SalesforcePropertiesPageActions.fillSObjectName(invalidSobjectName);
  }

  @And("fill Dataset name")
  public void fillDatasetNameAs() {
    SalesforcePropertiesPageActions.fillDatasetInSink();
  }

  @And("fill Table name")
  public void fillTableNameAs() {
    SalesforcePropertiesPageActions.fillTabelName();
  }

  @And("Close the Plugin Properties Page")
  public void closeThePluginPropertiesPage() {
    SalesforcePropertiesPageActions.closePluginPropertiesPage();
  }

  @And("fill the ProjectID")
  public void fillTheProjectID() throws IOException {
    SalesforcePropertiesPageActions.fillProjectId();
  }

  @And("fill the Service Account Json")
  public void fillServiceAccountFilePath() throws IOException, InterruptedException {
    SalesforcePropertiesPageActions.fillJson();
  }

  @And("click on preview data of sink: {string}")
  public void openSinkPluginPreviewData(String sinkName) {
    //SalesforcePropertiesPageActions.openPluginPreviewdata(sinkName);

  }

  @And("fill Last modified After in format: yyyy-MM-ddThh:mm:ssZ: {string}")
  public void fillLastModifiedAfterInFormatYyyyMMDdThhMmSsZ(String lastModifiedAfterLocation) {
    SalesforcePropertiesPageActions.fillLastModifyAfter(lastModifiedAfterLocation);
  }
  @And("then click on preview and configure")
  public void thenClickOnPreviewAndConfigure() {
    SalesforcePropertiesPageActions.clickPreviewAndConfigure();
  }
}
