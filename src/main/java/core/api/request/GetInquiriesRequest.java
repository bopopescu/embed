package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;

/**
 * Created by digvijaysharma on 02/02/17.
 */
public class GetInquiriesRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private int pageNo;

    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

