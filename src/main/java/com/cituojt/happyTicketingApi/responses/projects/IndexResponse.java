package com.cituojt.happyTicketingApi.responses.projects;

import java.util.List;

public class IndexResponse {

    private List<ProjectDetailsJSON> data;

    public IndexResponse(List<ProjectDetailsJSON> data) {
        this.setData(data);
    }

    public List<ProjectDetailsJSON> getData() {
        return data;
    }

    public void setData(List<ProjectDetailsJSON> data) {
        this.data = data;
    }
}
