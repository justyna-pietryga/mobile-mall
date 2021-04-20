package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;

import java.io.IOException;
import java.util.List;

public interface Scrapable {
     List<Category> getScrappedCategories();
     List<Product> getProducts(Category category) throws IOException;
}
