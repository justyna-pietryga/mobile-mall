package com.mobilemall.scrapper.categories;

import com.mobilemall.scrapper.model.Category;

import java.io.IOException;
import java.util.Set;

public interface ScrapCategories {
     Set<Category> getScrappedCategories() throws IOException;
}
