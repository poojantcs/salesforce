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
@SFBatchSource
@Smoke
@Regression
Feature: Salesforce Batch Source - Design time scenarios

  @BATCH-TS-SF-DSGN-07
  Scenario Outline: Verify user should be able to get output schema for a valid SOQL query
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce source for an SOQL Query of type: "<QueryType>"
    And click on the Get Schema button
    Then verify the Output Schema table for an SOQL query of type: "<QueryType>"
    Examples:
      | QueryType     |
      | SIMPLE        |
      | WHERE         |
      | CHILDTOPARENT |

  @BATCH-TS-SF-DSGN-09
  Scenario Outline: Verify user should be able to get output schema for a valid SObject Name
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as 'Data Pipeline - Batch'
    And Select plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce source for an SObject Query of SObject: "<SObjectName>"
    And click on the Validate button
    Then verify No errors found success message
    And verify the Output Schema table for an SObject Query of SObject: "<SObjectName>"
    Examples:
      | SObjectName |
      | ACCOUNT     |
      | CONTACT     |
      | OPPORTUNITY |
      | LEAD        |


  @BATCH-TS-SF-DSGN-10
  Scenario Outline: Verify user should be able to preview and run pipeline
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce source for an SObject Query of SObject: "<SObjectName>"
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
    And Connect source as "Salesforce" and sink as "BigQueryTable" to establish connection
    And Save the pipeline
    And Preview and run the pipeline
    And Verify the preview of pipeline is "successfully"
    And click on sink plugin preview data
    And Verify preview output schema matches the outputSchema captured in properties
    Examples:
      | SObjectName |
      | LEAD        |



  @BATCH-TS-SF-DSGN-11
  Scenario Outline: Verify user should be able to run and deploy pipeline
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce source for an SObject Query of SObject: "<SObjectName>"
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
    And Connect source as "Salesforce" and sink as "BigQueryTable" to establish connection
    And Save and Deploy Pipeline
    And Run the Pipeline in Runtime
    And Wait till pipeline is in running state
    Then Verify the pipeline status is "Succeeded"
    Examples:
      | SObjectName |
      | LEAD        |


