package com.mobilemall.core.controllers;

import com.mobilemall.persistence.model.Category;
import com.mobilemall.persistence.model.StandardCategory;
import com.mobilemall.persistence.repository.CategoryRepository;
import com.mobilemall.persistence.repository.StandardCategoryRepository;
import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.scrap_classes.Scrapable;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategorizationService {
    private final Map<ShopsEnum, Scrapable> scrapperHandler;
    private final CategoryRepository categoryRepository;
    private final StandardCategoryRepository standardCategoryRepository;

    public CategorizationService(Map<ShopsEnum, Scrapable> scrapperHandler,
                                 CategoryRepository categoryRepository,
                                 StandardCategoryRepository standardCategoryRepository) {
        this.scrapperHandler = scrapperHandler;
        this.categoryRepository = categoryRepository;
        this.standardCategoryRepository = standardCategoryRepository;
    }

    public List<Category> findCategoriesByStandardCategoryNames(List<String> standardCategoryNames) {
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

    public List<Category> findCategoriesByStandardCategoryIds(List<Long> standardCategoryIds) {
        val sc = (List<StandardCategory>) standardCategoryRepository.findAllById(standardCategoryIds);
        return sc.stream()
                .map(categoryRepository::findCategoriesByStandardCategory)
                .peek(st -> log.info("cat after conversion: {}", st))
                .flatMap(Collection::stream)
                .peek(st -> log.info("flat: {}", st))
                .collect(Collectors.toList());
    }

    public void saveCategoryWithStandardization(@RequestBody List<com.mobilemall.persistence.model.Category> categories) {
        log.info("Save categories: {}", categories);
        categories
                .stream()
                .map(this::saveStandardCategory)
                .forEach(categoryRepository::save);
    }

    public Flux<com.mobilemall.scrapper.model.Category> extractCategories(Set<ShopsEnum> shopList, boolean extractOnlyNotPersisted) {
        return Flux.fromIterable(shopList)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(shop -> scrapperHandler.get(shop).getScrappedCategories()
                        .subscribeOn(Schedulers.boundedElastic())
                        .filter(category -> !extractOnlyNotPersisted || isCategoryNotPersisted(category)));
    }

    public List<StandardCategory> receiveAllStandardCategories() {
        return (List<StandardCategory>) standardCategoryRepository.findAll();
    }


    private com.mobilemall.persistence.model.Category saveStandardCategory(
            com.mobilemall.persistence.model.Category category) {
        var standard = category.getStandardCategory();
        val optional = standardCategoryRepository.findStandardCategoryByName(standard.getName());
        if (optional.isEmpty()) {
            standard = standardCategoryRepository.save(standard);
        }
        category.setStandardCategory(optional.orElse(standard));
        return category;
    }

    private boolean isCategoryNotPersisted(com.mobilemall.scrapper.model.Category category) {
        return categoryRepository.findCategoryByOriginalNameAndUrl(category.getName(), category.getUrl()).isEmpty();
    }
}
