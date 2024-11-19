package org.sm.service;


import java.util.List;

import org.sm.proto.Category;
import org.sm.proto.Product;
import org.sm.proto.ProductList;
import org.sm.proto.ProductServiceGrpc;
import static org.sm.service.helpers.DtoMappingHelper.mapProductListToProductDTO;
import static org.sm.service.helpers.DtoMappingHelper.mapProductToProductDTO;
import org.springframework.stereotype.Service;
;

@Service
public class ProductServiceRPC {

    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public ProductServiceRPC(ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub) {
        this.productServiceBlockingStub = productServiceBlockingStub;
    }

    public org.sm.dto.Product getProductById(int id){
        Product product = Product.newBuilder().setProductId(id).build();
        Product response = productServiceBlockingStub.getProductById(product);
        return mapProductToProductDTO(response);        
    }

    public List<org.sm.dto.Product> getProductByCategoryId(int id) {
        Category category = Category.newBuilder().setCategoryId(id).build();
        ProductList response = productServiceBlockingStub.getProductByCategoryId(category);
        return  mapProductListToProductDTO(response);
    }

}
