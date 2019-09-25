package com.beautyboss.slogen.geektime.module.request;

import lombok.Data;

@Data
public class ArticlesRequest {

    private int cid ;

    private int size = 100;

    private int prev = 0;

    private String order = "earliest";

    private boolean sample = false;

    public static ArticlesRequest create(int cid) {
        ArticlesRequest request = new ArticlesRequest();
        request.setCid(cid);
        return request;
    }
}
