package com.viku.itemService.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@Setter
public class FoundItem {
    @Id
    private String id;
    private String images;
    private String userId;
    private String type;
}
