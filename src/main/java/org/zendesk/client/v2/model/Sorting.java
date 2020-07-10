package org.zendesk.client.v2.model;

import java.util.Objects;

public class Sorting
{
    String sortBy;
    SortOrder sortOrder;

    public Sorting(String sortBy, SortOrder sortOrder)
    {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public String getSortBy()
    {
        return sortBy;
    }

    public void setSortBy(String sortBy)
    {
        this.sortBy = sortBy;
    }

    public SortOrder getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString()
    {
        return "Sorting{" +
                "sortBy='" + sortBy + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Sorting sorting = (Sorting) o;
        return Objects.equals(sortBy, sorting.sortBy) &&
                sortOrder == sorting.sortOrder;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(sortBy, sortOrder);
    }
}
