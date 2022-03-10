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
@Regression
Feature: Salesforce Sink - Design time - validation scenarios

  @BATCH-TS-SF-DSGN-06
  Scenario: Verify required fields missing validation for 'Topic name' property
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select Sink plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Reference Name property
    And click on the Validate button
    Then verify required fields missing validation message for Sobject Name property


  @BATCH-TS-SF-DSGN-07
  Scenario Outline: Verify validation message for invalid Max Records Per Batch
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select Sink plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce sink for an SobjectName: "<SObjectName>"
    And fill invalid max records per batch
    And click on the Validate button
    Then verify validation message for invalid Max Records Per Batch
    Examples:
      | SObjectName |
      | LEAD        |


  @BATCH-TS-SF-DSGN-08
  Scenario Outline: Verify validation message for invalid Max Bytes Per Batch
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select Sink plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce sink for an SobjectName: "<SObjectName>"
    And fill invalid max Bytes per batch
    And click on the Validate button
    Then verify validation message for invalid Max Bytes Per Batch
    Examples:
      | SObjectName |
      | LEAD        |
