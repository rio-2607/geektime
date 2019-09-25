package com.beautyboss.slogen.geektime.controller;

import com.beautyboss.slogen.geektime.call.GeekTimeCall;
import com.beautyboss.slogen.geektime.module.request.ArticlesRequest;
import com.beautyboss.slogen.geektime.module.request.SingleArticleRequest;
import com.beautyboss.slogen.geektime.module.response.*;
import com.beautyboss.slogen.geektime.utils.FileUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/geektime")
public class GeekTimeController {

    private String footer = "</div></div></div> <div class=\"_2sRsF5RP_0\"></div></div><link rel=\"dns-prefetch\" href=\"//cdn.mathjax.org\"></body></html>";

    private String template = "<div class=\"_50pDbNcP_0\"><h1 class=\"vJXgmLTi_0 _2QmGFWqF_0\">%s</h1><div class=\"_2LbT9q3y_0 _2QmGFWqF_0\"><span>%s</span><span>%s</span></div><div class=\"_3Jbcj4Iu_0 _2QmGFWqF_0\"><img src=\"%s\" class=\"_1-ZfmNK8_0\"><div class=\"_2SACi4xg_0\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACsAAAABCAYAAABHaleQAAAAIklEQVQYV2N89+7df0FBQQYQeP/+PZgGAWQxUuRx6aOGeQCGgifLklNmnQAAAABJRU5ErkJggg==\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAABCAYAAADaZ14YAAAAHUlEQVQYV2N89+7df0FBQQYQeP/+PZgGAWQxcuQBvSgSy8lu4vMAAAAASUVORK5CYII=\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEEAAAABCAYAAACBr8MpAAAAIElEQVQoU2N89+7df0FBQQYQeP/+PZgGAXqLIdtHb7sBVh8ty3KDP+sAAAAASUVORK5CYII=\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAABCAYAAADaZ14YAAAAH0lEQVQYV2N89+7dfwYoEBQUBLPev3/PgIuNTS02MQCNKBLL0Byp2gAAAABJRU5ErkJggg==\"></div><div class=\"_3IatBmhv_0\"><div class=\"_29HP61GA_0\">%s</div></div> </div></div> ";

    private final int COLUMN_TYPE = 1; // 专栏类型的文章ID

    @Resource
    private GeekTimeCall geekTimeCall;

    @GetMapping("/all")
    public Object all() {
        List<Integer> allColumnIds = getAllBuy();
        allColumnIds.forEach(cid -> {
            System.out.println(">>>>>>>> 处理cid:" + cid);
            try {
                toHtml(cid);
                Thread.sleep(300);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        return allColumnIds;
    }

    @GetMapping("/column")
    public Object column(@RequestParam("cid") int cid) throws IOException {
        toHtml(cid);
        return "success";
    }

    private void toHtml(int cid) throws IOException {

        String fileName = String.format("%s.html",cid);

        ArticlesRequest request = ArticlesRequest.create(cid);
        ArticlesResponse response = geekTimeCall.articles(request);

        StringBuilder builder = new StringBuilder();

        // 读入html文档头
        List<String> headers = FileUtils.readFile("template/header.html",false);
        headers.forEach(builder::append);

        if(null != response.getData() && !CollectionUtils.isEmpty(response.getData().getList())) {

            List<ArticlesResponseItem> articles = response.getData().getList();
            System.out.println(String.format("%s总共有%d篇文章",cid,articles.size()));
            articles.forEach(article -> {
                System.out.println(article.getArticleTitle());

                SingleArticleRequest articleRequest = SingleArticleRequest.create(String.format("%s",article.getId()));

                SingleArticleResponse articleResponse = geekTimeCall.singleArticle(articleRequest);

                ArticleItem item = articleResponse.getData();

                String cTime = transferLongToDate("yyyy-MM-dd HH:mm:ss",article.getArticleCtime()*1000);

                String content = String.format(template, item.getArticleTitle(),cTime,article.getAuthorName(),item.getArticleCover(),item.getArticleContent());
                builder.append(content);
                try {
                    Thread.sleep((120+random())*1000); // 随机停留5分钟左右
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            builder.append(footer);
            FileUtils.writeFile(fileName,builder.toString());
            System.out.println(String.format("%d 保存完毕",cid));
        }

    }

    private List<Integer> getAllBuy() {
        BuyResponse buyResponse = geekTimeCall.allBuy();
        if(null == buyResponse || CollectionUtils.isEmpty(buyResponse.getData())) {
            return Collections.EMPTY_LIST;
        }
        Optional<BuyResponseItem> columnArticle = buyResponse.getData().stream().filter(item -> COLUMN_TYPE == item.getId()).findFirst();
        return columnArticle.map(buyResponseItem -> buyResponseItem.getList().stream().map(item -> item.getExtra().getColumnId()).collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }


    private static String transferLongToDate(String dateFormat,Long millSec){

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        Date date= new Date(millSec);

        return sdf.format(date);

    }

    private int random() {
        return (int)(1+Math.random()*(10-1+1));
    }
}
