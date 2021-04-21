package com.mobilemall.general.controllers;

import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.scrap_classes.Scrapable;
import com.mobilemall.scrapper.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@RestController
@RequestMapping(path = "/api")
public class MallController {

    private final Map<ShopsEnum, Scrapable> scrapperHandler;

    @Autowired
    public MallController(Map<ShopsEnum, Scrapable> scrapperHandler) {
        this.scrapperHandler = scrapperHandler;
    }

    @GetMapping(path = "/categories")
    @ResponseStatus(HttpStatus.OK)
    public Map<ShopsEnum, List<Category>> getCategories(@RequestParam Set<ShopsEnum> shopList) {
        return shopList.stream()
                .collect(Collectors.toMap(identity(), shop -> scrapperHandler.get(shop).getScrappedCategories()));
    }

    //TODO handle the situation when scrapping from one shop would fail
    //TODO Introduce React Java or sth else
    //TODO Consider removing WebDriver as a bean and create seperate instance for every run and make calls in parallel
}
