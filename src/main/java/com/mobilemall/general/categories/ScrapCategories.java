package com.mobilemall.general.categories;

import com.mobilemall.general.model.Category;

import java.io.IOException;
import java.util.Set;

public interface ScrapCategories {
     Set<Category> getScrappedCategories() throws IOException;
}
