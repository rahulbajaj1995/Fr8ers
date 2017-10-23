package com.truxapp.fbfire.Model;

import java.io.Serializable;

/**
 * Created by hp on 14/10/17.
 */

public class CountQuotationHistoryModel implements Serializable{
    private String pendingcount;
    private String sharedcount;
    private String approvecount;
    private String rejectcount;

    public String getPendingcount() {
        return pendingcount;
    }

    public void setPendingcount(String pendingcount) {
        this.pendingcount = pendingcount;
    }

    public String getSharedcount() {
        return sharedcount;
    }

    public void setSharedcount(String sharedcount) {
        this.sharedcount = sharedcount;
    }

    public String getApprovecount() {
        return approvecount;
    }

    public void setApprovecount(String approvecount) {
        this.approvecount = approvecount;
    }

    public String getRejectcount() {
        return rejectcount;
    }

    public void setRejectcount(String rejectcount) {
        this.rejectcount = rejectcount;
    }

}
