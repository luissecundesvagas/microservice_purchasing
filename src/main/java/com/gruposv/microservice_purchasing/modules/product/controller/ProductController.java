package com.gruposv.microservice_purchasing.modules.product.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruposv.microservice_purchasing.dto.ApiResponseDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.attributes.ListAttributesDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.attributes.ProductAttributeDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.categories.ListCategoriesDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.categories.ProductCategoryDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.products.*;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductStatus;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductType;
import com.gruposv.microservice_purchasing.modules.product.enums.UnitOfMeasure;
import com.gruposv.microservice_purchasing.modules.product.mapper.products.ProductStatusMapper;
import com.gruposv.microservice_purchasing.modules.product.mapper.products.ProductTypeMapper;
import com.gruposv.microservice_purchasing.modules.product.mapper.products.UnitOfMeasureMapper;
import com.gruposv.microservice_purchasing.modules.product.service.ProductService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReturnProductDTO>> createProduct(@RequestBody @Valid ProductDTO productDTO){
        ReturnProductDTO product = this.productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), product, "Produto criado com sucesso!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<ReturnProductDTO>>> findAllProducts(@ModelAttribute @Valid ListProductsDTO listProductsDTO){
        Page<ReturnProductDTO> products = this.productService.findAllProducts(listProductsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), products, "Lista de produtos."));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnProductDTO>> findProductById(@PathVariable("id") Long id){
        ReturnProductDTO product = this.productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), product, "Produto encontrado com sucesso!"));
    }

    @GetMapping("/skucode/{skucode}")
    public ResponseEntity<ApiResponseDTO<ReturnProductDTO>> findProductBySkuCode(@PathVariable("skucode") String skuCode){
        ReturnProductDTO product = this.productService.findBySkuCode(skuCode);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), product, "Produto encontrado com sucesso!"));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponseDTO<ReturnProductDTO>> findProductByName(@PathVariable("name") String name){
        ReturnProductDTO product = this.productService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), product, "Produto encontrado com sucesso!"));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnProductDTO>> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO){
        ReturnProductDTO product = this.productService.updateProduct(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), product, "Produto editado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReturnProductDTO>> deleteProduct(@PathVariable("id") Long id){
        this.productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDTO<>("success", HttpStatus.NO_CONTENT.value(), null, "Produto excluído com sucesso!"));
    }

    @PostMapping("/delete-all-by-ids")
    public ResponseEntity<ApiResponseDTO<ReturnProductDTO>> deleteAllProductsById(@RequestBody @Valid DeleteListProductsDTO dto){
        this.productService.deleteProductsList(dto.getIds());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDTO<>("success", HttpStatus.NO_CONTENT.value(), null, "Produto excluído com sucesso!"));
    }

    @GetMapping("/unit_of_measures")
    public ResponseEntity<ApiResponseDTO<List<UnitOfMeasureDTO>>> returnUnitOfMeasureList(){
        List<UnitOfMeasureDTO> unitOfMeasureList = Arrays.stream(UnitOfMeasure.values()).map(UnitOfMeasureMapper::toDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), unitOfMeasureList, ""));
    }

    @GetMapping("/product_types")
    public ResponseEntity<ApiResponseDTO<List<ProductTypeDTO>>> returnProductTypeList(){
        List<ProductTypeDTO> productTypeList = Arrays.stream(ProductType.values()).map(ProductTypeMapper::toDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), productTypeList, ""));
    }

    @GetMapping("/product_status")
    public ResponseEntity<ApiResponseDTO<List<ProductStatusDTO>>> returnProductStatusList(){
        List<ProductStatusDTO> productStatusList = Arrays.stream(ProductStatus.values()).map(ProductStatusMapper::toDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), productStatusList, ""));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponseDTO<Page<ProductCategoryDTO>>> returnAllcategories(@ModelAttribute @Valid ListCategoriesDTO listCategoriesDTO){
        Page<ProductCategoryDTO> categories = this.productService.returnAllCategories(listCategoriesDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), categories, ""));
    }

    @GetMapping("/categories/productId/{id}")
    public ResponseEntity<ApiResponseDTO<List<ProductCategoryDTO>>> returnAllCategoriesByProductId(@PathVariable("id") Long productId){
        List<ProductCategoryDTO> categories = this.productService.returnAllCategoriesByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), categories, ""));
    }

    // createProductCategory
    @PostMapping("/categories")
    public ResponseEntity<ApiResponseDTO<ProductCategoryDTO>> createProductCategory(@RequestBody @Valid ProductCategoryDTO productCategoryDTO){
        ProductCategoryDTO category = this.productService.createProductCategory(productCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), category, "Categoria criada com sucesso!"));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCategory(@PathVariable("id") Long categoryId){
        this.productService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDTO<>("success", HttpStatus.NO_CONTENT.value(), null, ""));
    }

    @GetMapping("/attributes")
    public ResponseEntity<ApiResponseDTO<Page<ProductAttributeDTO>>> returnAllAttributes(@ModelAttribute @Valid ListAttributesDTO listAttributesDTO){
        Page<ProductAttributeDTO> attributes = this.productService.returnAllAttributes(listAttributesDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), attributes, ""));
    }

    @GetMapping("/attributes/productId/{id}")
    public ResponseEntity<ApiResponseDTO<List<ProductAttributeDTO>>> returnAllAttributesByProductId(@PathVariable("id") Long productId){
        List<ProductAttributeDTO> attributes = this.productService.returnAllAttributesByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("success", HttpStatus.OK.value(), attributes, ""));
    }

    @PostMapping("/attributes")
    public ResponseEntity<ApiResponseDTO<ProductAttributeDTO>> createProductAttribute(@RequestBody @Valid ProductAttributeDTO productAttributeDTO){
        ProductAttributeDTO attribute = this.productService.createProductAttribute(productAttributeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>("success", HttpStatus.CREATED.value(), attribute, "Atributo criado com sucesso!"));
    }

    @DeleteMapping("/attributes/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAttribute(@PathVariable("id") Long attributeId){
        this.productService.deleteAttribute(attributeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseDTO<>("success", HttpStatus.NO_CONTENT.value(), null, ""));
    }

}
