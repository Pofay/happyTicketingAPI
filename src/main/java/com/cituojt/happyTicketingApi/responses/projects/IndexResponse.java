package com.cituojt.happyTicketingApi.responses.projects;

import java.util.List;

public class IndexResponse {

    private List<ProjectJSON> data;

    public IndexResponse(List<ProjectJSON> data) {
        this.setData(data);
    }

    public List<ProjectJSON> getData() {
        return data;
    }

    public void setData(List<ProjectJSON> data) {
        this.data = data;
    }
}
