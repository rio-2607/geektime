package com.beautyboss.slogen.geektime.module.response;

import lombok.Data;

import java.util.List;

@Data
public class BuyResponse {

    private int code;

    private List<BuyResponseItem> data;

    private List<Object> error;

    private List<Object> extra;

}
