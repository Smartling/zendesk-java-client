package org.zendesk.client.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SupportCenterLocale implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** Automatically assigned when creating items */
    private Long id;

    /** The API url of this item */
    private String url;

    /** ISO Locale code */
    private String locale;

    /** Human readable locale name */
    private String name;

    /** Human readable locale name on the locale language */
    @JsonProperty("native_name")
    private String nativeName;

    /** Display name */
    @JsonProperty("presentation_name")
    private String presentationName;

    /** Right to left flag */
    private Boolean rtl;

    /** If the locale is the default for the account */
    @JsonProperty("default")
    private Boolean isDefault;

    /** When this record was created */
    @JsonProperty("created_at")
    private Date createdAt;

    /** When this record last got updated */
    @JsonProperty("updated_at")
    private Date updatedAt;

    public SupportCenterLocale()
    {
    }

    public SupportCenterLocale(Long id, String url, String locale, String name, String nativeName, String presentationName, Boolean rtl, Boolean isDefault, Date createdAt, Date updatedAt)
    {
        this.id = id;
        this.url = url;
        this.locale = locale;
        this.name = name;
        this.nativeName = nativeName;
        this.presentationName = presentationName;
        this.rtl = rtl;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

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

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNativeName()
    {
        return nativeName;
    }

    public void setNativeName(String nativeName)
    {
        this.nativeName = nativeName;
    }

    public String getPresentationName()
    {
        return presentationName;
    }

    public void setPresentationName(String presentationName)
    {
        this.presentationName = presentationName;
    }

    public Boolean getRtl()
    {
        return rtl;
    }

    public void setRtl(Boolean rtl)
    {
        this.rtl = rtl;
    }

    public Boolean getDefault()
    {
        return isDefault;
    }

    public void setDefault(Boolean aDefault)
    {
        isDefault = aDefault;
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
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SupportCenterLocale that = (SupportCenterLocale) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(url, that.url) &&
                Objects.equals(locale, that.locale) &&
                Objects.equals(name, that.name) &&
                Objects.equals(nativeName, that.nativeName) &&
                Objects.equals(presentationName, that.presentationName) &&
                Objects.equals(rtl, that.rtl) &&
                Objects.equals(isDefault, that.isDefault) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, url, locale, name, nativeName, presentationName, rtl, isDefault, createdAt, updatedAt);
    }

    @Override
    public String toString()
    {
        return "SupportLocale{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", locale='" + locale + '\'' +
                ", name='" + name + '\'' +
                ", nativeName='" + nativeName + '\'' +
                ", presentationName='" + presentationName + '\'' +
                ", rtl=" + rtl +
                ", isDefault=" + isDefault +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
