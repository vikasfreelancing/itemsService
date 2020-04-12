package com.viku.itemService.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class LostItem {
    public LostItem(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "IMAGES")
    private String images;

    @Column(name="USER_ID")
    private String userId;

    @Column(name = "TYPE")
    private String type;
    @Column(name="IS_FOUND")
    private boolean isFound;
    @Column(name = "FOUNDED_ID")
    private Long foundId;
}
