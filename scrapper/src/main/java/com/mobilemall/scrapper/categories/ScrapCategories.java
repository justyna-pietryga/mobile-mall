package com.mobilemall.scrapper.categories;

import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;

import java.io.IOException;
import java.util.List;

public interface ScrapCategories {
     List<Category> getScrappedCategories();
     List<Product> getProducts(Category category) throws IOException;
}
