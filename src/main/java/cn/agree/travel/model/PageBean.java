package cn.agree.travel.model;

import java.util.List;

public class PageBean<E> {
    private List<E> list;
    private int curPage;
    private int pageSize;
    private long totalSize;
    private long totalPage;

    public PageBean() {
    }

    public PageBean(List<E> list, int curPage, int pageSize, long totalSize, long totalPage) {
        this.list = list;
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.totalPage = totalPage;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "list=" + list +
                ", curPage=" + curPage +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", totalPage=" + totalPage +
                '}';
    }

}
