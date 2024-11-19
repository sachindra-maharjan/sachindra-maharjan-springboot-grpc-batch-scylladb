package org.sm.service.helpers;

import java.util.ArrayList;
import java.util.List;

import org.sm.dto.Product;
import org.sm.proto.ProductList;

public class DtoMappingHelper {
    public static List<org.sm.dto.Product> mapProductListToProductDTO(ProductList productList) {
        List<Product> products = new ArrayList<>();
        productList.getProductList().forEach(product -> {
            Product product1 = getProduct();
            product1.setId(product.getProductId());
            product1.setCategoryId(product.getCategoryId());
            product1.setName(product.getName());
            product1.setDescription(product.getDescription());
            product1.setPrice(product.getPrice());

            products.add(product1);
        });

        return products;
    }

    public static org.sm.dto.Product mapProductToProductDTO(org.sm.proto.Product product) {
        Product product1 = getProduct();
        product1.setId(product.getProductId());
        product1.setCategoryId(product.getCategoryId());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        return product1;
    }

    private static Product getProduct(){
        return new Product();
    }
}
