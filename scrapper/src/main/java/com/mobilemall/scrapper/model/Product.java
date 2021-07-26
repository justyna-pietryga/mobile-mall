package com.mobilemall.scrapper.model;

import lombok.*;

@Value
@Builder
@ToString
public class Product {
    String url;
    String name;
    String imgUrl;
}
