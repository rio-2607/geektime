package com.beautyboss.slogen.geektime.module.response;

import lombok.Data;

@Data
public class ArticlesResponseItem {

    private String articleSubtitle;

    private Integer id;

    private String columnCover; // 封面

    private String articleTitle; // 文章标题

    private String authorName; //  作者名称

    private Long articleCtime; // 文章发布时间

}
