package org.zendesk.client.v2.model.media;

import java.util.ArrayList;
import java.util.List;

public class MediaResponse {
    private List<MediaRecord> records = new ArrayList<>();
    private Meta meta = new Meta();

    public List<MediaRecord> getRecords() {
        return records;
    }

    public void setRecords(List<MediaRecord> records) {
        this.records = records;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
