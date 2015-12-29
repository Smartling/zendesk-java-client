package org.zendesk.client.v2.model.hc;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Translation
{
    public enum SourceType {
        ARTICLE("articles", "Article"), CATEGORY("categories", "Category"), SECTION("sections", "Section");

        private String urlPath;
        private String sourceType;

        private SourceType(String urlPath, String sourceName)
        {
            this.urlPath = urlPath;
            this.sourceType = sourceName;
        }

        public String getUrlPath()
        {
            return urlPath;
        }

        public String getSourceType()
        {
            return sourceType;
        }

        public static SourceType getBySourceName(String sourceName)
        {
            Optional<SourceType> type = Arrays.stream(SourceType.values()).filter(t -> t.getSourceType().equals(sourceName)).findFirst();
            return type.isPresent() ? type.get() : null;
        }
    };

    /** Automatically assigned when a translation is created */
    private Long id;

    /** The API url of the translation */
    private String url;  

    /** The url of the translation in Help Center */
    @JsonProperty("html_url")
    private String htmlUrl;  

    /** The id of the item that has this translation */
    @JsonProperty("source_id")
    private Long sourceId;  

    /** The type of the item that has this translation. Can be Article, Section, or Category */
    @JsonProperty("source_type")
    private String sourceType;  

    /** The locale of the translation */
    private String locale; 

    /** The title of the translation */
    private String title; 

    /** The body of the translation. Empty by default */
    private String body;  

    /** True if the translation is outdated; false otherwise. False by default */
    private Boolean outdated;  

    /** True if the translation is a draft; false otherwise. False by default */
    private Boolean  draft;  

    /** The time at which the translation was created */
    @JsonProperty("created_at")
    private Date createdAt;  

    /** The time at which the translation was last updated */
    @JsonProperty("updated_at")
    private Date updatedAt;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getHtmlUrl()
    {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl)
    {
        this.htmlUrl = htmlUrl;
    }

    public Long getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(Long sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(String sourceType)
    {
        this.sourceType = sourceType;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Boolean getOutdated()
    {
        return outdated;
    }

    public void setOutdated(Boolean outdated)
    {
        this.outdated = outdated;
    }

    public Boolean getDraft()
    {
        return draft;
    }

    public void setDraft(Boolean draft)
    {
        this.draft = draft;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", locale='" + locale + '\'' +
                ", outdated=" + outdated +
                ", draft=" + draft +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
