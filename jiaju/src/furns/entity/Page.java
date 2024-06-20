package furns.entity;

import java.util.List;

public class Page<T> {

    //每页显示的记录数量其它地方也可以使用
    public static final Integer PAGE_SIZE = 3;

    //显示当前页(自己指定
    private Integer pageNo;

    //每页显示几条记录
    private Integer pageSize = PAGE_SIZE;

    //一共多少页
    private Integer pageTotalCount;

    //一共多少条记录
    private Integer totalRow;

    //当前页要显示的数据
    private List<T> items;

    //分页导航的字符串
    private String url;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
