package com.mobilemall.core.controllers;

import com.mobilemall.persistence.model.MallOrder;
import com.mobilemall.persistence.model.MallUser;
import com.mobilemall.persistence.model.Product;
import com.mobilemall.persistence.model.Shop;
import com.mobilemall.persistence.repository.MallOrderRepository;
import com.mobilemall.persistence.repository.MallUserRepository;
import com.mobilemall.persistence.repository.ProductRepository;
import com.mobilemall.persistence.repository.ShopRepository;
import com.mobilemall.scrapper.conf.ShopsEnum;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/orders")
@CrossOrigin(origins = "*")
@Slf4j
public class OrdersController {

    private final MallOrderRepository mallOrderRepository;
    private final MallUserRepository mallUserRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    public OrdersController(MallOrderRepository mallOrderRepository,
                            MallUserRepository mallUserRepository,
                            ProductRepository productRepository, ShopRepository shopRepository) {
        this.mallOrderRepository = mallOrderRepository;
        this.mallUserRepository = mallUserRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    @PostMapping(path = "/save")
    @ResponseStatus(HttpStatus.OK)
    public void saveOrder(@RequestBody Set<Product> products) {
        val email = "justyna.pietryga@gmail.com";
        val user = mallUserRepository.findByEmail(email).orElse(
                        new MallUser("Justyna", "Postrozny", email)
        );
        val order = new MallOrder();
        order.setMallUser(user);
        products.forEach(product -> {
            product.setMallOrder(order);
            val shop = shopRepository.findById(product.getShop().getShop_id()).orElse(new Shop(product.getName()));
            product.setShop(shop);
        });
        val savedProducts = productRepository.saveAll(products);
        order.setProducts((List<Product>) savedProducts);
        mallOrderRepository.save(order);
    }

    @GetMapping(path = "/get-order")
    @ResponseStatus(HttpStatus.OK)
    public List<MallOrder> getOrder() {
        return (List<MallOrder>) mallOrderRepository.findMallOrdersByMallUser_Email("justyna.pietryga@sabre.com");
    }
}
