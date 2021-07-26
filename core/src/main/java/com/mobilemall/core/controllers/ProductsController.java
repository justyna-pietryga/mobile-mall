package com.mobilemall.core.controllers;

import com.mobilemall.core.service.CategorizationService;
import com.mobilemall.persistence.repository.ShopRepository;
import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Product;
import com.mobilemall.scrapper.scrap_classes.Scrapable;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/products")
@Slf4j
public class ProductsController {
    private final CategorizationService categorizationService;
    private final Map<ShopsEnum, Scrapable> scrapperHandler;
    private final ShopRepository shopRepository;

    public ProductsController(CategorizationService categorizationService,
                              Map<ShopsEnum, Scrapable> scrapperHandler,
                              ShopRepository shopRepository) {
        this.categorizationService = categorizationService;
        this.scrapperHandler = scrapperHandler;
        this.shopRepository = shopRepository;
    }

    @GetMapping(path = "/byStandardCategoryIds", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Product> getAllProductsByStandardCategoryIds(@RequestParam List<Long> standardCategoryIds) {
        val categories = categorizationService.findCategoriesByStandardCategoryIds(standardCategoryIds);
        System.out.println("Requested products by categories: " + categories);
        log.info("Requested products by categories: {}", categories);
        return Flux.fromIterable(categories)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(category -> {
                            log.info("Shop: {}", category.getShop().getShop_id());
                            log.info("Category Url: {}", category.getUrl());
                            return scrapperHandler.get(ShopsEnum.valueOf(category.getShop().getShop_id()))
                                    .getProducts(category.getUrl())
                                    .publishOn(Schedulers.boundedElastic())
                                    .doOnNext(product -> log.info("Product: {}", product));
                        }
                );
    }
}
