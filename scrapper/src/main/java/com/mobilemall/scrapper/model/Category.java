package com.mobilemall.scrapper.model;

import com.mobilemall.scrapper.conf.ShopsEnum;
import lombok.*;

@Value
@Builder
public class Category {
    String name;
    String url;
    ShopsEnum shop;
}
