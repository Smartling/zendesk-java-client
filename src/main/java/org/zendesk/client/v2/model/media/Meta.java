package org.zendesk.client.v2.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {
    @JsonProperty("has_more")
    private boolean hasMore;
    @JsonProperty("after_cursor")
    private String afterCursor;
    @JsonProperty("before_cursor")
    private String beforeCursor;

    public boolean hasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getAfterCursor() {
        return afterCursor;
    }

    public void setAfterCursor(String afterCursor) {
        this.afterCursor = afterCursor;
    }

    public String getBeforeCursor() {
        return beforeCursor;
    }

    public void setBeforeCursor(String beforeCursor) {
        this.beforeCursor = beforeCursor;
    }
}
