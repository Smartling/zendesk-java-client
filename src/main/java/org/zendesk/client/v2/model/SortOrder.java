package org.zendesk.client.v2.model;

import java.util.Arrays;

/**
 * An enum that represents sorting order. The name value maps to what the Zendesk API actually 
 * expects in the query param.
 * @author rbolles on 2/7/18.
 */
public enum SortOrder {
    ASCENDING("asc"), DESCENDING("desc");

    private final String queryParameter;

    public static SortOrder fromQueryParameter(String queryParameter) {
        return Arrays.stream(SortOrder.values())
                .filter(s -> s.getQueryParameter().equals(queryParameter))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid queryparameter: " + queryParameter));
    }

    SortOrder(String queryParameter) {
        this.queryParameter = queryParameter;
    }

    public String getQueryParameter() {
        return queryParameter;
    }
}
