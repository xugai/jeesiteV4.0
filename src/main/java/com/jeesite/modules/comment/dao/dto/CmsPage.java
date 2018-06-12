package com.jeesite.modules.comment.dao.dto;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.comment.entity.CmsComment;
import java.util.List;

public class CmsPage{
    private List<List<CmsComment>>list;
    private Page page;
    public CmsPage(){}
    public CmsPage(List<List<CmsComment>> list, Page page) {
        this.list = list;
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<List<CmsComment>> getCmsList() {
        return list;
    }

    public void setCmsList(List<List<CmsComment>> list) {
        this.list = list;
    }
}
