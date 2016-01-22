package org.zendesk.client.v2.model.hc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Variant
{
    private String url;

    private Long id;

    private String name;

    private boolean outdated;

    private boolean active;

    @JsonProperty("default")
    private boolean defaultVariant;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isOutdated()
    {
        return outdated;
    }

    public void setOutdated(boolean outdated)
    {
        this.outdated = outdated;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isDefaultVariant()
    {
        return defaultVariant;
    }

    public void setDefaultVariant(boolean defaultVariant)
    {
        this.defaultVariant = defaultVariant;
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
}
