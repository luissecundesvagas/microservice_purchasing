package com.gruposv.microservice_purchasing.modules.product.mapper.products;

import com.gruposv.microservice_purchasing.modules.product.dto.products.ProductTypeDTO;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductType;

public class ProductTypeMapper {

    public static ProductTypeDTO toDTO(ProductType productType) {
        return new ProductTypeDTO(productType);
    }

}
