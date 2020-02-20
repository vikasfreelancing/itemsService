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
public class Item {
    public Item(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "IMAGES")
    private String images;

    @Column(name="USER_ID")
    private String userId;

    @Column(name = "TYPE")
    private String type;
}
