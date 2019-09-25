package com.beautyboss.slogen.geektime.module.response;

import lombok.Data;

@Data
public class BuyItemDetail {

    private String cover;

    private String title;

    private String type;

    private BuyItemDetailExtra extra;

}

