package com.lt.chapter2.model;

import lombok.Data;

@Data
public class Consumer {
    private Long consumerId;
    private  String consumerName;
    private  String consumerContact;
    private String consumerTelephone;
    private String consumerEmail;
    private String consumerRemark;
}
