package org.zendesk.client.v2.model;

/**
 * Represents a locale inside Zendesk ecosystem
 */
public class Locale
{
    /** Internal ID, assigned to a locale */
    private Long id;
    /** Locale code */
    private String locale;
    /** Locale name */
    private String name;

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(final String locale)
    {
        this.locale = locale;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }
}
