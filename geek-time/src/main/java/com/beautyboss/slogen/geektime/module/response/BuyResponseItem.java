package com.beautyboss.slogen.geektime.module.response;

import lombok.Data;

import java.util.List;

@Data
public class BuyResponseItem {

    private int id;

    private String title;

    private BuyResponseItemPage page;

    private List<BuyItemDetail> list;
}

@Data
class BuyResponseItemPage {

    private int count;

    private boolean more;
}
