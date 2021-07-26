package com.mobilemall.core.controllers;

import com.mobilemall.core.service.CategorizationService;
import com.mobilemall.persistence.model.StandardCategory;
import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/categorisation")
@Slf4j
public class CategorizationController {

    private final CategorizationService categorizationService;

    public CategorizationController(CategorizationService categorizationService) {
        this.categorizationService = categorizationService;
    }

    @GetMapping(path = "/all-categories", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> receiveAllCategories(@RequestParam(required = false) Set<ShopsEnum> shopList) {
        if (shopList == null) shopList = Set.of(ShopsEnum.values());
        return categorizationService.extractCategories(shopList, false);
    }

    @GetMapping(path = "/categories", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> receiveNotPersistedCategories(@RequestParam(required = false) Set<ShopsEnum> shopList) {
        if (shopList == null) shopList = Set.of(ShopsEnum.values());
        return categorizationService.extractCategories(shopList, true);
    }

    @GetMapping(path = "/all-standard-categories")
    @ResponseStatus(HttpStatus.OK)
    public List<StandardCategory> receiveAllStandardCategories() {
        return categorizationService.receiveAllStandardCategories();
    }

    @PostMapping(path = "/categories-by-standard")
    @ResponseStatus(HttpStatus.OK)
    public List<com.mobilemall.persistence.model.Category> receiveCategoriesByStandard(
            @RequestBody List<String> standardCategoryNames) {
        return categorizationService.findCategoriesByStandardCategoryNames(standardCategoryNames);
    }

    @GetMapping(path = "/categories-by-standardIds")
    @ResponseStatus(HttpStatus.OK)
    public List<com.mobilemall.persistence.model.Category> receiveCategoriesByStandardIds(
            @RequestParam List<Long> standardCategoryIds) {
        return categorizationService.findCategoriesByStandardCategoryIds(standardCategoryIds);
    }

    @PostMapping(path = "/save-categories")
    @ResponseStatus(HttpStatus.OK)
    public void saveCategoryWithStandardization(@RequestBody List<com.mobilemall.persistence.model.Category> categories) {
        categorizationService.saveCategoryWithStandardization(categories);
    }
}
