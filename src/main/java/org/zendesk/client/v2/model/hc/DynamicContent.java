package org.zendesk.client.v2.model.hc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class DynamicContent
{
    private Long id;

    private String url;

    private String name;

    private String placeholder;

    @JsonProperty("default_locale_id")
    private Long defaultLocaleId;

    private boolean outdated;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    private List<Variant> variants;

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPlaceholder()
    {
        return placeholder;
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder = placeholder;
    }

    public Long getDefaultLocaleId()
    {
        return defaultLocaleId;
    }

    public void setDefaultLocaleId(Long defaultLocaleId)
    {
        this.defaultLocaleId = defaultLocaleId;
    }

    public boolean isOutdated()
    {
        return outdated;
    }

    public void setOutdated(boolean outdated)
    {
        this.outdated = outdated;
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

    public List<Variant> getVariants()
    {
        return variants;
    }

    public void setVariants(List<Variant> variants)
    {
        this.variants = variants;
    }
}
