package com.gruposv.microservice_purchasing.modules.product.mapper.products;

import com.gruposv.microservice_purchasing.modules.product.dto.products.ProductStatusDTO;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductStatus;

public class ProductStatusMapper {

    public static ProductStatusDTO toDTO(ProductStatus productStatus){
        return new ProductStatusDTO(productStatus);
    }

}
