package com.mobilemall.general;

import com.mobilemall.general.categories.ReservedScrapCategories;
import com.mobilemall.general.categories.ScrapCategories;
import com.mobilemall.general.model.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/test")
public class Control {

    private final ScrapCategories scrapCategories;

    public Control(ReservedScrapCategories scrapCategories) {
        this.scrapCategories = scrapCategories;
    }


    @RequestMapping(method = RequestMethod.GET)
    public Collection<Category> getAllFlights() throws IOException {
        return scrapCategories.getScrappedCategories();
    }
}
