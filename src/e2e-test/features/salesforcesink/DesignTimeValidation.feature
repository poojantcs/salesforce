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
@SFStreamingSource
@Smoke
@Regression
Feature: Salesforce Sink - Design time - validation scenarios

  @Sink-TS-SF-DSGN-ERROR-09
  Scenario: Verify validation message for providing an invalid SObject Name
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select Sink plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And fill SObject Name property in Sink with an invalid value
    And Click on the Validate button
    Then Verify that the Plugin is displaying an error message: "invalidsink.sobjectname.error" on the header



  @Sink-TS-SF-DSGN-ERROR-10
  Scenario Outline: Verify validation message for invalid Max Records Per Batch
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select Sink plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce sink for an SobjectName: "<SObjectName>"
    And fill max Records Per Batch as: "1000000"
    And Click on the Validate button
    And Verify that the Plugin Property: "maxRecordsPerBatch" is displaying an in-line error message: "invalid.maxrecords"
    Examples:
      | SObjectName |
      | LEAD        |


  @Sink-TS-SF-DSGN-ERROR-11
  Scenario Outline: Verify validation message for invalid Max Bytes Per Batch
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select Sink plugin: "Salesforce" from the plugins list
    And Navigate to the properties page of plugin: "Salesforce"
    And fill Authentication properties for Salesforce Admin user
    And configure Salesforce sink for an SobjectName: "<SObjectName>"
    And fill max Bytes Per Batch as: "1000000000"
    And Click on the Validate button
    And Verify that the Plugin Property: "maxBytesPerBatch" is displaying an in-line error message: "invalid.maxbytes"
    Examples:
      | SObjectName |
      | LEAD        |
