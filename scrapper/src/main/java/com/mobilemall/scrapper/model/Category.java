package com.mobilemall.scrapper.model;

import lombok.*;

@Value
@Builder
public class Category {
    String name;
    String url;
}
