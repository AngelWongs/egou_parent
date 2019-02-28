package cn.ken.egou.query;

public class BaseQuery {
    /**
     * 当前页 (page-1)*size
     */
    private Integer page = 1;
    /**
     * 每页条数
     */
    private Integer rows = 10;
    /**
     * 从哪里开始
     */
    private Integer start = 0;
    /**
     * 模糊查询
     */
    private String keyword;

    public Integer getStart() {
        return (this.page-1)*this.rows;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
