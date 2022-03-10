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


package io.cdap.plugin.utils.enums;

import io.cdap.e2e.utils.PluginPropertyUtils;


/**
 * Salesforce Sink plugin properties.
 */
public enum SalesforceSinkProperty {
    SOBJECT_NAME("sObject", PluginPropertyUtils.errorProp("required.property.sobjectname")),
    MAXRECORDS_PER_BATCH("maxRecordsPerBatch",
            PluginPropertyUtils.errorProp("invalid.maxrecords")),
    MAXBYTES_PER_BATCH("maxBytesPerBatch",
            PluginPropertyUtils.errorProp("invalid.maxbytes")),
    INVALID_MAX_RECORDS("1000000"),
    INVALID_MAX_BYTES("1000000000");

    public final String dataCyAttributeValue;
    public String propertyMissingValidationMessage;

    SalesforceSinkProperty(String value) {
        this.dataCyAttributeValue = value;
    }

    SalesforceSinkProperty(String value, String propertyMissingValidationMessage) {
        this.dataCyAttributeValue = value;
        this.propertyMissingValidationMessage = propertyMissingValidationMessage;
    }
}