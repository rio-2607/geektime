package com.beautyboss.slogen.geektime.module.response;

import lombok.Data;

import java.util.List;

@Data
public class SingleArticleResponse {

    private List<Object> error;

    private List<Object> extra;

    private ArticleItem data;

}
