package org.zendesk.client.v2.model.hc;

import java.util.Arrays;

public enum TranslationSourceType {
    ARTICLE("articles", "Article"), CATEGORY("categories", "Category"), SECTION("sections", "Section");

    private String urlPath;
    private String sourceType;

    TranslationSourceType(String urlPath, String sourceType)
    {
        this.urlPath = urlPath;
        this.sourceType = sourceType;
    }

    public String getUrlPath()
    {
        return urlPath;
    }

    public String getSourceType()
    {
        return sourceType;
    }

    public static TranslationSourceType getBySourceName(String sourceType)
    {
        return Arrays.stream(TranslationSourceType.values())
                .filter(t -> t.getSourceType().equals(sourceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("sourceType=" + sourceType + " does not exist"));
    }
};
