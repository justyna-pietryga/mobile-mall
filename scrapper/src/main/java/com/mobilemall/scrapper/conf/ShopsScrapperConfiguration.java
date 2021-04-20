package com.mobilemall.scrapper.conf;

import com.mobilemall.scrapper.categories.ReservedScraper;
import com.mobilemall.scrapper.categories.Scrapable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

import static com.mobilemall.scrapper.conf.ShopsEnum.RESERVED;

@Configuration
public class ShopsScrapperConfiguration {

    private final ReservedScraper reservedScraper;

    public ShopsScrapperConfiguration(ReservedScraper reservedScraper) {
        this.reservedScraper = reservedScraper;
    }

    @Bean
    public Map<ShopsEnum, Scrapable> scrapperHandler() {
        EnumMap<ShopsEnum, Scrapable> scrapperByShopType = new EnumMap<>(ShopsEnum.class);
        scrapperByShopType.put(RESERVED, reservedScraper);

        return scrapperByShopType;
    }
}
