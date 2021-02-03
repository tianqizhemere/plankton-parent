package top.tianqi.plankton.common.utils;

import java.util.List;

/**
 * 分页对象
 * @author Wukh
 * @create 2021-01-24
 */
public class PageResult {

    /** 当前页码*/
    private Long pageNum;

    /**每页数量*/
    private Long pageSize;

    /**记录总数*/
    private Long totalSize;

    /**页码总数*/
    private Long totalPages;

    /** 分页数据*/
    private List<?> content;

    public PageResult() {
    }

    public PageResult(Long pageNum, Long pageSize, Long totalSize, Long totalPages, List<?> content) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.totalPages = totalPages;
        this.content = content;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<?> getContent() {
        return content;
    }

    public void setContent(List<?> content) {
        this.content = content;
    }
}
