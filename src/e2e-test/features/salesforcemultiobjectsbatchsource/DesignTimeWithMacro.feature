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
Feature: Salesforce Multi Objects Batch Source - Design time Scenarios and Run time Scenarios with Macro


  @BATCH-TS-SF-RNTM-MACRO-03
  Scenario:Verify user should be able to preview and run pipeline using Macro
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Multi Objects" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "SalesforceMultiObjects"
    And fill Reference Name property
    And Click on the Macro button of Property: "username"
    And Fill the macro enabled Property: "username" with value: "Username"
    And Click on the Macro button of Property: "password"
    And Fill the macro enabled Property: "password" with value: "Password"
    And Click on the Macro button of Property: "securityToken"
    And Fill the macro enabled Property: "securityToken" with value: "SecurityToken"
    And Click on the Macro button of Property: "consumerKey"
    And Fill the macro enabled Property: "consumerKey" with value: "ConsumerKey"
    And Click on the Macro button of Property: "consumerSecret"
    And Fill the macro enabled Property: "consumerSecret" with value: "ConsumerSecret"
    And Click on the Macro button of Property: "loginUrl"
    And Fill the macro enabled Property: "loginUrl" with value: "LoginUrl"
    And Click on the Macro button of Property: "whiteList"
    And Fill the macro enabled Property: "whiteList" with value: "WhiteList1"
    And fill Last modified After in format: yyyy-MM-ddThh:mm:ssZ: "lastmodified.after"
    And Validate "Salesforce" plugin properties
    And Close the Plugin Properties Page
    And Select Sink plugin: "BigQueryTable" from the plugins list
    And Navigate to the properties page of plugin: "BigQuery"
    And fill Reference Name property
    And fill Dataset name
    And fill Table name
    And fill the ProjectID
    And fill the Service Account Json
    Then Validate "BigQuery" plugin properties
    And Close the Plugin Properties Page
    And Connect source as "SalesforceMultiObjects" and sink as "BigQueryTable" to establish connection
    And Save the pipeline
    And then click on preview and configure
    And Enter runtime argument value "admin.username" for key "Username"
    And Enter runtime argument value "admin.password" for key "Password"
    And Enter runtime argument value "admin.security.token" for key "SecurityToken"
    And Enter runtime argument value "admin.consumer.key" for key "ConsumerKey"
    And Enter runtime argument value "admin.consumer.secret" for key "ConsumerSecret"
    And Enter runtime argument value "login.url" for key "LoginUrl"
    And Enter runtime argument value "Salesforce.sobjectName" for key "WhiteList1"
    And Run the preview of pipeline with runtime arguments
    And Verify the preview of pipeline is "successfully"
