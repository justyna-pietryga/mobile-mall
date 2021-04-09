package com.mobilemall.scrapper.model;

import lombok.*;

@Value
@Builder
public class Product {
    String url;
    String name;
    String imgUrl;
}
