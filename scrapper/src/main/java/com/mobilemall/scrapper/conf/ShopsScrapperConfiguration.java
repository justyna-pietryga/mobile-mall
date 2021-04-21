package com.mobilemall.scrapper.conf;

import com.mobilemall.scrapper.scrap_classes.BershkaScraper;
import com.mobilemall.scrapper.scrap_classes.ReservedScraper;
import com.mobilemall.scrapper.scrap_classes.Scrapable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

import static com.mobilemall.scrapper.conf.ShopsEnum.BERSHKA;
import static com.mobilemall.scrapper.conf.ShopsEnum.RESERVED;

@Configuration
public class ShopsScrapperConfiguration {

    private final ReservedScraper reservedScraper;
    private final BershkaScraper bershkaScraper;

    public ShopsScrapperConfiguration(ReservedScraper reservedScraper, BershkaScraper bershkaScraper) {
        this.reservedScraper = reservedScraper;
        this.bershkaScraper = bershkaScraper;
    }

    @Bean
    public Map<ShopsEnum, Scrapable> scrapperHandler() {
        EnumMap<ShopsEnum, Scrapable> scrapperByShopType = new EnumMap<>(ShopsEnum.class);
        scrapperByShopType.put(RESERVED, reservedScraper);
        scrapperByShopType.put(BERSHKA, bershkaScraper);

        return scrapperByShopType;
    }
}
