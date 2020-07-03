package org.zendesk.client.v2.model;

import java.util.Objects;

public class Page
{
    int pageNo;
    int perPage;

    public Page()
    {
    }

    public Page(int pageNo, int perPage)
    {
        this.pageNo = pageNo;
        this.perPage = perPage;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getPerPage()
    {
        return perPage;
    }

    public void setPerPage(int perPage)
    {
        this.perPage = perPage;
    }

    @Override
    public String toString()
    {
        return "Page{" +
                "page=" + pageNo +
                ", perPage=" + perPage +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Page page1 = (Page) o;
        return pageNo == page1.pageNo &&
                perPage == page1.perPage;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(pageNo, perPage);
    }
}
