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
@Smoke
@Regression
Feature: Salesforce Sink  - Design time scenarios

  @Streaming-TS-SF-DSGN-05
  Scenario Outline: Verify user should be able to get input schema for valid SObjectName
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select Sink plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And I select operation type as INSERT
    And fill Authentication properties for Salesforce Admin user
    And I select max Records Per Batch as: "10000"
    And I select max Bytes Per Batch as: "10000000"
    And configure Salesforce sink for an SobjectName: "<SObjectName>"
    And I Select option type for error handling as SKIP_ON_ERROR
    And click on the Validate button
    Then verify No errors found success message
    Examples:
      | SObjectName |
      | ACCOUNT     |
      | CONTACT     |


