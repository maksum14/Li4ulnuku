package com.softserve.edu.dto;

import com.softserve.edu.service.SecurityUserDetailsService;

import java.util.List;

public class PageDTO<T> {

    private Long totalItems;
    private List<T> content;
    private Long idOrganization;
    public PageDTO() {}

    public PageDTO(Long totalItems, List<T> content) {
        this.totalItems = totalItems;
        this.content = content;
    }

    public PageDTO(Long totalItems, List<T> content, Long userId) {
        this.totalItems = totalItems;
        this.content = content;
        this.idOrganization=userId;
    }


    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Long getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(Long idOrganization) {
        this.idOrganization = idOrganization;
    }
}
