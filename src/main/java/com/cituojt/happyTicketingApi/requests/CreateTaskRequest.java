package com.cituojt.happyTicketingApi.requests;

public class CreateTaskRequest {

    private String name;
    private String status;
    private Integer estimatedTime;

    public CreateTaskRequest() {

    }

    public CreateTaskRequest(String name, String status, Integer estimatedTime) {
        this.setName(name);
        this.setStatus(status);
        this.setEstimatedTime(estimatedTime);
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
