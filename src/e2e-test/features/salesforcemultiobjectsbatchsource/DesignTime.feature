# Copyright © 2022 Cask Data, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.

@SalesforceSalesCloud
@SFMultiObjectsBatchSource
@Smoke
@Regression
Feature: Salesforce Multi Objects Batch Source - Design time scenarios

  @MULTIBATCH-TS-SF-DSGN-06
  Scenario: Verify user should be able to successfully validate the source for valid SObject names in the White List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as 'Data Pipeline - Batch'
    And Select plugin: "Salesforce Multi Objects" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce MultiObjects"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And fill White List with below listed SObjects:
      | ACCOUNTS | CONTACTS |
    And click on the Validate button
    Then verify No errors found success message

  @MULTIBATCH-TS-SF-DSGN-07
  Scenario: Verify user should be able to successfully validate the source for valid SObject names in the Black List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as 'Data Pipeline - Batch'
    And Select plugin: "Salesforce Multi Objects" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce MultiObjects"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And fill Black List with below listed SObjects:
      | ACCOUNTS | CONTACTS | OPPORTUNITIES |
    And click on the Validate button
    Then verify No errors found success message

  @MULTIBATCH-TS-SF-DSGN-12
  Scenario: Verify user should be able to preview and run pipeline for valid SObject names in the White List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Multi Objects" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "SalesforceMultiObjects"
    And fill Authentication properties for Salesforce Admin user
    And fill Reference Name property
    And fill White List with below listed SObjects:
      | LEAD | CONTACT |
    And fill Last modified After and before
    And click on the Validate button
    And verify No errors found success message
    And Capture the generated Output Schema
    And Close the Plugin Properties Page
    And Select Sink plugin: "BigQueryTable" from the plugins list
    And Navigate to the properties page of plugin: "BigQuery"
    And fill Reference Name property
    And fill Dataset name
    And fill Table name
    And fill the ProjectID
    And fill the Service Account File path
    And click on the Validate button
    And verify No errors found success message
    And Close the Plugin Properties Page
    And Connect source as "SalesforceMultiObjects" and sink as "BigQueryTable" to establish connection
    And Save the pipeline
    And Preview and run the pipeline
    And Verify the preview of pipeline is "successfully"
    And click on sink plugin preview data
    And Verify preview output schema matches the outputSchema captured in properties




  @MULTIBATCH-TS-SF-DSGN-13
  Scenario: Verify user should be able to run and deploy pipeline for valid SObject names in the White List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Multi Objects" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "SalesforceMultiObjects"
    And fill Authentication properties for Salesforce Admin user
    And fill Reference Name property
    And fill White List with below listed SObjects:
      | LEAD | CONTACT |
    And fill Last modified After and before
    And click on the Validate button
    And verify No errors found success message
    And Capture the generated Output Schema
    And Close the Plugin Properties Page
    And Select Sink plugin: "BigQueryTable" from the plugins list
    And Navigate to the properties page of plugin: "BigQuery"
    And fill Reference Name property
    And fill Dataset name
    And fill Table name
    And fill the ProjectID
    And fill the Service Account File path
    And click on the Validate button
    And verify No errors found success message
    And Close the Plugin Properties Page
    And Connect source as "SalesforceMultiObjects" and sink as "BigQueryTable" to establish connection
    And Save and Deploy Pipeline
    And Run the Pipeline in Runtime
    And Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
