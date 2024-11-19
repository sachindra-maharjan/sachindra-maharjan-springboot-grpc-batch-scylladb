package org.sm.config;

import org.sm.proto.ProductServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;


@Configuration
public class GrpcClientConfig {
    
    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(@GrpcClient("grpc-product-service") Channel channel) {
        return ProductServiceGrpc.newBlockingStub(channel);
    }

}
