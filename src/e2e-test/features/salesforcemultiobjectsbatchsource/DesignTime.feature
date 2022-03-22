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
Feature: Salesforce Multi Objects Batch Source - Design time Scenarios and Run time Scenarios

  @MULTIBATCH-TS-SF-DSGN-03
  Scenario: Verify user should be able to successfully validate the source for valid SObject names in the White List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Multi Objects" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "SalesforceMultiObjects"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And fill White List with below listed SObjects:
      | ACCOUNT | CONTACT |
    Then Validate "SalesforceMultiObjects" plugin properties


  @MULTIBATCH-TS-SF-DSGN-04
  Scenario: Verify user should be able to successfully validate the source for valid SObject names in the Black List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Multi Objects" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "SalesforceMultiObjects"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And fill Black List with below listed SObjects:
      | ACCOUNT | CONTACT |
    Then Validate "SalesforceMultiObjects" plugin properties


  @MULTIBATCH-TS-SF-RNTM-11
  Scenario: Verify user should be able to preview and run pipeline for valid SObject names in the White List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Multi Objects" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "SalesforceMultiObjects"
    And fill Authentication properties for Salesforce Admin user
    And fill Reference Name property
    And fill White List with below listed SObjects:
      | LEAD | CONTACT |
    And fill Last modified After in format: yyyy-MM-ddThh:mm:ssZ: "lastmodified.after"
    Then Validate "Salesforce" plugin properties
    And Capture the generated Output Schema
    And Close the Plugin Properties Page
    And Select Sink plugin: "BigQueryTable" from the plugins list
    And Navigate to the properties page of plugin: "BigQuery"
    And fill Reference Name property
    And fill Dataset name
    And fill Table name
    And fill the ProjectID
    And fill the Service Account Json
    Then Validate "Salesforce" plugin properties
    And Close the Plugin Properties Page
    And Connect source as "SalesforceMultiObjects" and sink as "BigQueryTable" to establish connection
    And Save the pipeline
    And Preview and run the pipeline
    And Verify the preview of pipeline is "successfully"
    # And click on preview data of sink: "BigQuery"
    And Verify sink plugin's Preview Data for Input Records table and the Input Schema matches the Output Schema of Source plugin
    # And Verify preview output schema matches the outputSchema captured in properties




  @MULTIBATCH-TS-SF-RNTM-12
  Scenario: Verify user should be able to run and deploy pipeline for valid SObject names in the White List
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Multi Objects" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "SalesforceMultiObjects"
    And fill Authentication properties for Salesforce Admin user
    And fill Reference Name property
    And fill White List with below listed SObjects:
      | LEAD | CONTACT |
    And fill Last modified After in format: yyyy-MM-ddThh:mm:ssZ: "lastmodified.after"
    Then Validate "Salesforce" plugin properties
    And Capture the generated Output Schema
    And Close the Plugin Properties Page
    And Select Sink plugin: "BigQueryTable" from the plugins list
    And Navigate to the properties page of plugin: "BigQuery"
    And fill Reference Name property
    And fill Dataset name
    And fill Table name
    And fill the ProjectID
    And fill the Service Account Json
    Then Validate "Salesforce" plugin properties
    And Close the Plugin Properties Page
    And Connect source as "SalesforceMultiObjects" and sink as "BigQueryTable" to establish connection
    And Save and Deploy Pipeline
    And Run the Pipeline in Runtime
    And Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
