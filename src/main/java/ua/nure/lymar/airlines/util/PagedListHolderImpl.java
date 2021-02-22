package ua.nure.lymar.airlines.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class PagedListHolderImpl<T> {
    private static final Logger LOGGER = Logger.getLogger(PagedListHolderImpl.class);
    private List<T> list;
    private Integer pageSize;
    private int page;
    private String attribut = "list";

    //строк на странице
    public PagedListHolderImpl(List<T> list) {
        this.list = list;
        this.pageSize = 10;
    }

    public void setAttribut(String attribut) {
        this.attribut = attribut;
    }

    List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    int getPage() {
        return page;
    }

    private void setPage(int page) {
        this.page = page;
    }

    private List<T> getPageList() {
        return this.getList().subList(this.getFirstElementOfPage(), this.getLastElementOfPage() + 1);
    }

    private int getLastElementOfPage() {
        int endIndex = this.getPageSize() * (this.getPage() + 1);
        int size = this.list.size();
        return (endIndex > size ? size : endIndex) - 1;
    }

    private int getFirstElementOfPage() {
        return this.getPageSize() * this.getPage();
    }

    private int getNumberOfPages() {
        return (int) Math.ceil((double) this.list.size() / this.getPageSize());
    }

    public void setPadding(HttpServletRequest request){
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e){
            LOGGER.debug("page not number");
        }
        request.setAttribute("maxPages" , getNumberOfPages());
        String url = request.getRequestURI();
        String contex = request.getContextPath();
        url = url.substring(contex.length());
        request.setAttribute("currentPage", url);
        if (page < 1 || page > getNumberOfPages())
            page = 1;
        request.setAttribute("page", page);
        setPage(page-1);
        request.setAttribute(attribut, getPageList());
    }
}
