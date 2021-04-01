package com.xb.entity;

import lombok.Data;

/**
 * @Author Alan_
 * @create 2021/4/1 7:34
 */
@Data
public class Filemanager {
    private int id;

    private String filename;

    private String username;

    private String type;

    private String url;

    private int count;
}
