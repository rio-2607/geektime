package com.beautyboss.slogen.geektime.module.request;

import lombok.Data;

@Data
public class SingleArticleRequest {

    private String id;

    private boolean includeNeighbors = true;

    private boolean isFreelyread = true;

    public static SingleArticleRequest create(String id) {
        SingleArticleRequest request = new SingleArticleRequest();
        request.setId(id);
        return request;
    }

}
