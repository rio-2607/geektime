package com.beautyboss.slogen.geektime.call;

import com.beautyboss.slogen.geektime.module.request.ArticlesRequest;
import com.beautyboss.slogen.geektime.module.request.SingleArticleRequest;
import com.beautyboss.slogen.geektime.module.response.ArticlesResponse;
import com.beautyboss.slogen.geektime.module.response.BuyResponse;
import com.beautyboss.slogen.geektime.module.response.SingleArticleResponse;
import com.beautyboss.slogen.easy.client.EasyClient;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@EasyClient(name = "geektime",url="https://time.geekbang.org",snake = true)
public interface GeekTimeCall {

    @Headers({
            "Content-Type: application/json",
            "Cookie : 这里填写自己的cookie，登录后抓包任何一个极客时间的页面都可以",
            "Origin: https://time.geekbang.org",
            "User-Agent: 这里填你的UA"
    })
    @POST("/serv/v1/column/articles")
    ArticlesResponse articles(@Body ArticlesRequest request);


    @Headers({
            "Accept: application/json, text/plain, */*",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,ja;q=0.6",
            "Cache-Control: no-cache",
            "Connection: keep-alive",
            "Content-Length: 61",
            "Content-Type: application/json",
            "Cookie : 这里填写自己的cookie，登录后抓包任何一个极客时间的页面都可以",
            "Origin: https://time.geekbang.org",
            "User-Agent: 这里填你的UA",
            "Host: time.geekbang.org",
            "Sec-Fetch-Mode: cors",
            "Sec-Fetch-Site: same-origin",
            "Pragma: no-cache"
    })
    @POST("/serv/v1/article")
    SingleArticleResponse singleArticle(@Body SingleArticleRequest request);


    @Headers({
            "Content-Type: application/json",
            "Cookie : 这里填写自己的cookie，登录后抓包任何一个极客时间的页面都可以",
            "Origin: https://time.geekbang.org",
            "User-Agent: 这里填你的UA"
    })
    @POST("/serv/v1/my/products/all")
    BuyResponse allBuy();

}
