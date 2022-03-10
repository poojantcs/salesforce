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

package io.cdap.plugin.salesforcebatchsource.actions;

import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.e2e.pages.actions.CdfBigQueryPropertiesActions;
import io.cdap.e2e.pages.actions.CdfPluginPropertiesActions;
import io.cdap.e2e.pages.locators.CdfBigQueryPropertiesLocators;
import io.cdap.e2e.utils.*;
import io.cdap.plugin.salesforce.authenticator.AuthenticatorCredentials;
import io.cdap.plugin.salesforce.plugin.source.batch.SalesforceBatchSource;
import io.cdap.plugin.salesforce.plugin.source.batch.SalesforceSourceConfig;
import io.cdap.plugin.salesforce.plugin.source.batch.SalesforceSourceConfigBuilder;
import io.cdap.plugin.salesforcebatchsource.locators.SalesforcePropertiesPage;
import io.cdap.plugin.utils.MiscUtils;
import io.cdap.plugin.utils.SchemaFieldTypeMapping;
import io.cdap.plugin.utils.SchemaTable;
import io.cdap.plugin.utils.enums.SOQLQueryType;
import io.cdap.plugin.utils.enums.SObjects;
import io.cdap.plugin.utils.enums.SalesforceBatchSourceProperty;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Salesforce source plugins - Actions.
 */
public class SalesforcePropertiesPageActions {
  private static final Logger logger = LoggerFactory.getLogger(SalesforcePropertiesPageActions.class);

  static {
    SeleniumHelper.getPropertiesLocators(SalesforcePropertiesPage.class);
  }

  public static void fillReferenceName(String referenceName) {
    logger.info("Fill Reference name: " + referenceName);
    SalesforcePropertiesPage.referenceInput.sendKeys(referenceName);
  }

  public static void fillAuthenticationProperties(String username, String password, String securityToken,
                                                  String consumerKey, String consumerSecret) {
    logger.info("Fill Authentication properties for user: " + username);
    SalesforcePropertiesPage.usernameInput.sendKeys(username);
    SalesforcePropertiesPage.passwordInput.sendKeys(password);
    SalesforcePropertiesPage.securityTokenInput.sendKeys(securityToken);
    SalesforcePropertiesPage.consumerKeyInput.sendKeys(consumerKey);
    SalesforcePropertiesPage.consumerSecretInput.sendKeys(consumerSecret);
  }

  public static void fillAuthenticationPropertiesForSalesforceAdminUser() {
    SalesforcePropertiesPageActions.fillAuthenticationProperties(
      PluginPropertyUtils.pluginProp("admin.username"),
      PluginPropertyUtils.pluginProp("admin.password"),
      PluginPropertyUtils.pluginProp("admin.security.token"),
      PluginPropertyUtils.pluginProp("admin.consumer.key"),
      PluginPropertyUtils.pluginProp("admin.consumer.secret"));
  }

  public static void fillAuthenticationPropertiesWithInvalidValues() {
    SalesforcePropertiesPageActions.fillAuthenticationProperties(
      PluginPropertyUtils.pluginProp("invalid.admin.username"),
      PluginPropertyUtils.pluginProp("invalid.admin.password"),
      PluginPropertyUtils.pluginProp("invalid.admin.security.token"),
      PluginPropertyUtils.pluginProp("invalid.admin.consumer.key"),
      PluginPropertyUtils.pluginProp("invalid.admin.consumer.secret"));
  }

  public static void fillSOQLPropertyField(SOQLQueryType queryType) {
    logger.info("Fill SOQL Query field for Type: " + queryType + " using the Query: " + queryType.query);
    SalesforcePropertiesPage.soqlTextarea.sendKeys(queryType.query);
  }

  public static void configureSalesforcePluginForSoqlQuery(SOQLQueryType queryType) {
    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    fillReferenceName(referenceName);
    fillSOQLPropertyField(queryType);
  }

  public static void fillSObjectName(String sObjectName) {
    SalesforcePropertiesPage.sObjectNameInput.sendKeys(sObjectName);
  }

  public static void configureSalesforcePluginForSObjectQuery(SObjects sObjectName) {
    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    fillReferenceName(referenceName);
    fillSObjectName(sObjectName.value);
  }

  public static void clickOnGetSchemaButton() {
    logger.info("Click on the Get Schema button");
    SalesforcePropertiesPage.getSchemaButton.click();
    WaitHelper.waitForElementToBeDisplayed(SalesforcePropertiesPage.loadingSpinnerOnGetSchemaButton);
    WaitHelper.waitForElementToBeHidden(SalesforcePropertiesPage.loadingSpinnerOnGetSchemaButton);
    WaitHelper.waitForElementToBeDisplayed(SalesforcePropertiesPage.getSchemaButton);
  }

  public static void clickOnValidateButton() {
    logger.info("Click on the Validate button");
    SalesforcePropertiesPage.validateButton.click();
    WaitHelper.waitForElementToBeDisplayed(SalesforcePropertiesPage.loadingSpinnerOnValidateButton);
    //WaitHelper.waitForElementToBeHidden(SalesforcePropertiesPage.loadingSpinnerOnValidateButton);
    WaitHelper.waitForElementToBeDisplayed(SalesforcePropertiesPage.validateButton);
  }

  public static void verifyNoErrorsFoundSuccessMessage() {
    AssertionHelper.verifyElementDisplayed(SalesforcePropertiesPage.noErrorsFoundSuccessMessage);
  }

  private static AuthenticatorCredentials setAuthenticationCredentialsOfAdminUser() {
    return new AuthenticatorCredentials(
      PluginPropertyUtils.pluginProp("admin.username"),
      PluginPropertyUtils.pluginProp("admin.password"),
      PluginPropertyUtils.pluginProp("admin.consumer.key"),
      PluginPropertyUtils.pluginProp("admin.consumer.secret"),
      PluginPropertyUtils.pluginProp("login.url")
    );
  }

  private static SalesforceSourceConfig getSourceConfigWithSOQLQuery(SOQLQueryType queryType) {
    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    AuthenticatorCredentials adminUserAuthCreds = setAuthenticationCredentialsOfAdminUser();

    return new SalesforceSourceConfigBuilder()
      .setReferenceName(referenceName)
      .setUsername(adminUserAuthCreds.getUsername())
      .setPassword(adminUserAuthCreds.getPassword())
      .setConsumerKey(adminUserAuthCreds.getConsumerKey())
      .setConsumerSecret(adminUserAuthCreds.getConsumerSecret())
      .setSecurityToken(PluginPropertyUtils.pluginProp("security.token"))
      .setLoginUrl(adminUserAuthCreds.getLoginUrl())
      .setQuery(queryType.query)
      .build();
  }

  public static SchemaTable getExpectedSchemaTableForSOQLQuery(SOQLQueryType queryType) {
    SalesforceBatchSource batchSource = new SalesforceBatchSource(getSourceConfigWithSOQLQuery(queryType));
    Schema expectedSchema = batchSource.retrieveSchema();
    return getExpectedSchemaTableFromSchema(expectedSchema);
  }

  private static SalesforceSourceConfig getSourceConfigWithSObjectName(SObjects sObjectName) {
    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    AuthenticatorCredentials adminUserAuthCreds = setAuthenticationCredentialsOfAdminUser();

    return new SalesforceSourceConfigBuilder()
      .setReferenceName(referenceName)
      .setUsername(adminUserAuthCreds.getUsername())
      .setPassword(adminUserAuthCreds.getPassword())
      .setConsumerKey(adminUserAuthCreds.getConsumerKey())
      .setConsumerSecret(adminUserAuthCreds.getConsumerSecret())
      .setSecurityToken(PluginPropertyUtils.pluginProp("security.token"))
      .setLoginUrl(adminUserAuthCreds.getLoginUrl())
      .setSObjectName(sObjectName.value)
      .build();
  }

  public static SchemaTable getExpectedSchemaTableForSObjectQuery(SObjects sObjectName) {
    SalesforceBatchSource batchSource = new SalesforceBatchSource(getSourceConfigWithSObjectName(sObjectName));
    Schema expectedSchema = batchSource.retrieveSchema();
    return getExpectedSchemaTableFromSchema(expectedSchema);
  }

  public static SchemaTable getExpectedSchemaTableFromSchema(Schema expectedSchema) {
    SchemaTable expectedSchemaTable = new SchemaTable();
    List<Schema.Field> schemaFields = expectedSchema.getFields();

    for (Schema.Field field : schemaFields) {
      String fieldName = field.getName();
      String fieldTypeInSchema = field.getSchema().toString();
      String fieldType;

      if (fieldTypeInSchema.contains("logicalType") && fieldTypeInSchema.contains("date")) {
        fieldType = "date";
      } else if (fieldTypeInSchema.contains("logicalType") && fieldTypeInSchema.contains("timestamp")) {
        fieldType = "timestamp";
      } else {
        fieldType = MiscUtils.getSubStringBetweenDoubleQuotes(fieldTypeInSchema);
      }

      expectedSchemaTable.addField(new SchemaFieldTypeMapping(fieldName, fieldType));
    }

    return expectedSchemaTable;
  }

  public static void verifyFieldTypeMappingDisplayed(SchemaFieldTypeMapping schemaFieldTypeMapping) {
    WebElement element = SalesforcePropertiesPage.getSchemaFieldTypeMappingElement(schemaFieldTypeMapping);
    AssertionHelper.verifyElementDisplayed(element);
  }

  public static void verifyOutputSchemaTable(SchemaTable schemaTable) {
    List<SchemaFieldTypeMapping> listOfFields = schemaTable.getListOfFields();

    for (SchemaFieldTypeMapping schemaFieldTypeMapping : listOfFields) {
      verifyFieldTypeMappingDisplayed(schemaFieldTypeMapping);
    }
  }

  public static void clickOnClosePropertiesPageButton() {
    logger.info("Close the Salesforce (batch) source properties page");
    SalesforcePropertiesPage.closePropertiesPageButton.click();
  }

  public static void verifyRequiredFieldsMissingValidationMessage(SalesforceBatchSourceProperty propertyName) {
    WebElement element = SalesforcePropertiesPage.getPropertyInlineErrorMessage(propertyName);

    AssertionHelper.verifyElementDisplayed(element);
    AssertionHelper.verifyElementContainsText(element, propertyName.propertyMissingValidationMessage);
  }

  public static void verifyPropertyInlineErrorMessage(SalesforceBatchSourceProperty property,
                                                      String expectedErrorMessage) {
    WebElement element = SalesforcePropertiesPage.getPropertyInlineErrorMessage(property);

    AssertionHelper.verifyElementDisplayed(element);
    AssertionHelper.verifyElementContainsText(element, expectedErrorMessage);
  }

  public static void verifyInvalidSoqlQueryErrorMessageForStarQueries() {
    verifyPropertyInlineErrorMessage(SalesforceBatchSourceProperty.SOQL_QUERY,
      PluginPropertyUtils.errorProp("invalid.soql.starquery"));
  }

  public static void verifyErrorMessageOnHeader(String expectedErrorMessage) {
    AssertionHelper.verifyElementContainsText(SalesforcePropertiesPage.errorMessageOnHeader,
      expectedErrorMessage);
  }

  public static void verifyValidationMessageForBlankAuthenticationProperty() {
    verifyErrorMessageOnHeader(PluginPropertyUtils.errorProp("empty.authentication.property"));
  }

  public static void verifyValidationMessageForInvalidAuthenticationProperty() {
    verifyErrorMessageOnHeader(PluginPropertyUtils.errorProp("invalid.authentication.property"));
  }

  public static void verifyValidationMessageForMissingSoqlOrSobjectNameProperty() {
    verifyErrorMessageOnHeader(PluginPropertyUtils.errorProp("required.property.soqlorsobjectname.error"));
    verifyRequiredFieldsMissingValidationMessage(SalesforceBatchSourceProperty.SOQL_QUERY);
  }

  public static void verifyValidationMessageForInvalidSObjectName(String invalidSObjectName) {
    String expectedValidationMessage = PluginPropertyUtils.errorProp(
      "invalid.sobjectname.error") + " '" + invalidSObjectName + "''";
    verifyErrorMessageOnHeader(expectedValidationMessage);
  }


  //changes
  public static void fillDatasetInSink() {
    String datasetName = "Dataset" + RandomStringUtils.randomAlphanumeric(7);
    CdfBigQueryPropertiesActions.enterBigQueryDataset(datasetName);
  }


  public static void fillTabelName() {
    String tabelName = "Tablename" + RandomStringUtils.randomAlphanumeric(7);
    CdfBigQueryPropertiesActions.enterBigQueryTable(tabelName);

  }

  public static void fillProjectId() throws IOException {
    String projectId = PluginPropertyUtils.pluginProp("sink.projectid");
    CdfBigQueryPropertiesActions.enterProjectId(projectId);

  }


  public static void fillFilePath() throws IOException, InterruptedException {

    String json = "{   \"type\": \"service_account\",   \"project_id\": \"cdf-entcon\",   \"private_key_id\": \"9ebcaa5065f022988ea24454b188868020861bb5\",   \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCBZl6GRj2hnU41\\ne+dv7BkkzQ1aFjt1CywNJF1w62OXqZZ8poZK2Xqz2UlGlL/b8Dpb9LPoks1SNEKT\\nUGTpYYI48Mysgy2fYhXUO4KVhQ5vimbhgBEJwBTUg2lLmnuYg3sRhASuJJqR38yH\\nSos534/GuBalVQTMNRk+K8PTg6MlsAqTyThAmuFrvgJu6AxLFVwZgK35Zy/d3wRf\\n95KYHJzeCjTGxPgEDOb4qEGvVBb/1LVpWYliObBIV/k6bIIWGC+kxp0E6OKGw1Bb\\nS3raAenpWUrWb6xX2dN/3ASw3atuEQnNT4Xes5vb0MFLaWADYUiU4wKQuW0VfTD+\\niqCJ/SLnAgMBAAECggEAJwYs5pjDWHyczANwgi/1S0RtzOfciYlTgSkg5v+OOvxh\\njnkYEfWxjBCxCRCuJdG2f+n14eN3+V0aYNwDeuC1yZ9RUouDFEib5bQGxznn5xqZ\\nuVMKkGK1xXtWI39U2+N3F4q1cKFIXkrcn0aLY2o5LYhyB+1yc3VmBfpj5eOSrbgS\\nSNkpu+twk9lvTlEVxMMicvCKbFE/VREXS+pjTA+eY7XmmtAMRF8KBrGZ4/nsvUuL\\nLX1AkE5Raehn57PyLWSnHWWXWT+8P68UQ2hmrTEs11AQ5ncny5dW9ZqNbrKXoT+W\\n0aG/WEn0yL19gCqLRyEqObYHCigHTBUfTmvF2Z1VuQKBgQC2VzD0RxPjMyjV1xXH\\nDL6fHe4jAB0tiUA9y/QbF8ZjMWZ1A63Pl+iCf5bAU6fk3VO0flQCkXl+hWkjuF7s\\nZC7JjTooGCtx/SmihM14q5Nyzdkr6DZfZV5Ua5cf9HIVuRHXCYUhc74A+H2z8FeS\\neu7IMD8YTnVsp6PelZm1KlkVyQKBgQC1rExHTWzL74R0+z1kphvxqoWuy7n2VRtf\\nyxx0t5mNmI86a20E42Rac32cxWZirY73fPpZEP8sUy/80u/d401Gifzp7UoVo0ZA\\nqUkq6nO/3KL60YvWTIh0advzpckQucELdsl6umFcz32Vyf6LDvW7WR2iEnT+w2UU\\nvGf0vV6LLwKBgDgoMlOJcH6QywrQ60waiLrIpQbyign0M2zU26FceSUjmcKAF4/P\\n4TQPx4YEPbkm0RjNr2H7G8fznqX5qoJzeFVqwXaHuxmoNqJu9Dkt3oOFElWLdvN6\\nbMrjN/AHgPtfvDbWH8JluybhYRWB9/aNFe1hroz13QRRvQ5YVLaPDFDBAoGAOoHc\\npvHmYd8nN01aPjnIshGKr/poT06lXDpbVbNzPkith9Dk8TgSL5cWExD3ojvm2Qlk\\nzp5Mr4ey4qdEHJ+BWYBC9Us4GrPUgiqCp9GNqXJzAnfNoxxOrb8R2OvBTyOjaf+9\\nb0rq5CrmdQDyevI8bTOL+ZAcIZfny7/JcuYxtn0CgYAxBN4jnJyzLfyaAB5PKlWd\\n6XA9w0OkYTguuRq14ZN+uQ8RyOmZG4OspPIZ/8eh2LJi35I0CSV4i8ZkHewTcXqW\\n2bTuYwoHCDJpuRqGZ/onzA8DWhwfs5dQW7r9QTtL4E+HbrReddqUQMcRHyUldV5H\\nS3TuezJQrTo4vN1yUtgA/Q==\\n-----END PRIVATE KEY-----\\n\",   \"client_email\": \"cdf-ent-con-sf@cdf-entcon.iam.gserviceaccount.com\",   \"client_id\": \"109233043269964188972\",   \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",   \"token_uri\": \"https://oauth2.googleapis.com/token\",   \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",   \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/cdf-ent-con-sf%40cdf-entcon.iam.gserviceaccount.com\" }\n";

    //String filePath = PluginPropertyUtils.pluginProp("sink.serviceaccount.filepath");

    //Created a new method which click on the json radio button and pass json properties
    CdfBigQueryPropertiesActions.enterAccountJson(json);
  }

  public static void fillLastModifyAfter() {
    String lastModifyafter = "2022-03-08T00:00:00Z";
    String lastModifyBefore = "2021-11-13T00:00:00Z";
    SalesforcePropertiesPage.lastModifiedAfterInput.sendKeys(lastModifyafter);
    //SalesforcePropertiesPage.lastModifiedBeforeInput.sendKeys(lastModifyBefore);

  }

  public static void openPluginPreviewdata() {
    String pluginName = "BigQuery";
    CdfHelper.openSinkPluginPreviewData(pluginName);
  }

  public static void closePluginPropertiesPage() {
    CdfPluginPropertiesActions.clickCloseButton();
  }
}
