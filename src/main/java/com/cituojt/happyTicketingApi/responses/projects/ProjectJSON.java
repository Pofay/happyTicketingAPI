package com.cituojt.happyTicketingApi.responses.projects;

import java.util.List;

public class ProjectJSON {

    private String name;
    private Long id;
    private String url;
    private List<String> methods;

    public ProjectJSON(Long id, String name, String url, List<String> methods) {
        this.setId(id);
        this.setName(name);
        this.setUrl(url);
        this.setMethods(methods);
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public String getUrl() {
        return String.format("%s/%s", this.url, this.id);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}