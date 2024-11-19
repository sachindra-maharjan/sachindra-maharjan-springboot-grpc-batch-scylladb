package org.sm;

import java.util.List;

import org.sm.proto.Category;
import org.sm.proto.Product;
import org.sm.proto.ProductList;
import org.sm.proto.ProductServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    @Override
    public void getProductByCategoryId(Category request, StreamObserver<ProductList> responseObserver) {
        List<Product> products = InMemoryData.getProducts()
            .stream()
            .filter(product -> product.getCategoryId() == request.getCategoryId())
            .toList();
        
        ProductList productList = ProductList.newBuilder().addAllProduct(products).build();

        responseObserver.onNext(productList);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductById(Product request, StreamObserver<Product> responseObserver) {
        InMemoryData.getProducts()
        .stream()
        .filter(product -> product.getProductId() == request.getProductId())
        .findFirst()
        .ifPresent(responseObserver::onNext);

        responseObserver.onCompleted();
    }
}
