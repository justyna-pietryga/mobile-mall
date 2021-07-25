package com.mobilemall.core.controllers;

import com.mobilemall.persistence.model.StandardCategory;
import com.mobilemall.persistence.repository.CategoryRepository;
import com.mobilemall.persistence.repository.ShopRepository;
import com.mobilemall.persistence.repository.StandardCategoryRepository;
import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.scrap_classes.Scrapable;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/categorisation")
@Slf4j
public class CategorizationController {

    private final Map<ShopsEnum, Scrapable> scrapperHandler;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    private final StandardCategoryRepository standardCategoryRepository;

    public CategorizationController(Map<ShopsEnum, Scrapable> scrapperHandler, ShopRepository shopRepository, CategoryRepository categoryRepository, StandardCategoryRepository standardCategoryRepository) {
        this.scrapperHandler = scrapperHandler;
        this.shopRepository = shopRepository;
        this.standardCategoryRepository = standardCategoryRepository;

        this.categoryRepository = categoryRepository;
    }

    @GetMapping(path = "/all-categories", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> receiveAllCategories(@RequestParam(required = false) Set<ShopsEnum> shopList) {
        if (shopList == null) shopList = Set.of(ShopsEnum.values());
        return Flux.fromIterable(shopList)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(shop -> scrapperHandler.get(shop).getScrappedCategories().subscribeOn(Schedulers.boundedElastic()));
    }

    @GetMapping(path = "/all-standard-categories")
    @ResponseStatus(HttpStatus.OK)
    public List<StandardCategory> receiveAllStandardCategories() {
        return (List<StandardCategory>) standardCategoryRepository.findAll();
    }

    @PostMapping(path = "/categories-by-standard")
    @ResponseStatus(HttpStatus.OK)
    public List<com.mobilemall.persistence.model.Category> receiveCategoriesByStandard(@RequestBody List<String> standardCategoryNames) {
        return standardCategoryNames.stream()
                .peek(st -> log.info("category: {}", st))
                .map(standardCategoryRepository::findStandardCategoryByName)
                .peek(stCat -> log.info("standard: {}", stCat))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(st -> log.info("cat after: {}", st))
                .map(categoryRepository::findCategoriesByStandardCategory)
                .peek(st -> log.info("cat after conversion: {}", st))
                .flatMap(Collection::stream)
                .peek(st -> log.info("flat: {}", st))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/categories", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> receiveNotPersistedCategories(@RequestParam(required = false) Set<ShopsEnum> shopList) {
        if (shopList == null) shopList = Set.of(ShopsEnum.values());
        return Flux.fromIterable(shopList)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(shop -> scrapperHandler.get(shop).getScrappedCategories()
                        .subscribeOn(Schedulers.boundedElastic())
                        .filter(this::CATEGORY_PERSISTED_PREDICATE));
    }

    @PostMapping(path = "/save-categories")
    @ResponseStatus(HttpStatus.OK)
    public void saveCategoryWithStandardization(@RequestBody List<com.mobilemall.persistence.model.Category> categories) {
        log.info("Save categories: {}", categories);
        categories
                .stream()
                .map(this::saveStandardCategory)
                .forEach(categoryRepository::save);
    }

    private com.mobilemall.persistence.model.Category saveStandardCategory(com.mobilemall.persistence.model.Category category) {
        var standard = category.getStandardCategory();
        val optional = standardCategoryRepository.findStandardCategoryByName(standard.getName());
        if (optional.isEmpty()) {
            standard = standardCategoryRepository.save(standard);
        }
        category.setStandardCategory(optional.orElse(standard));
        return category;
    }

    private boolean CATEGORY_PERSISTED_PREDICATE(Category category) {
        return categoryRepository.findCategoryByOriginalNameAndUrl(category.getName(), category.getUrl()).isEmpty();
    }
}
