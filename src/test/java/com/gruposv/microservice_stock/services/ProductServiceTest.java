package com.gruposv.microservice_stock.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.gruposv.microservice_purchasing.modules.product.dto.products.ListProductsDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.products.ProductDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductStatus;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductType;
import com.gruposv.microservice_purchasing.modules.product.enums.UnitOfMeasure;
import com.gruposv.microservice_purchasing.modules.product.exception.DuplicateNameProductException;
import com.gruposv.microservice_purchasing.modules.product.exception.DuplicateSkuCodeException;
import com.gruposv.microservice_purchasing.modules.product.exception.ProductNotFoundException;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductRepository;
import com.gruposv.microservice_purchasing.modules.product.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setName("Produto Teste");
        productDTO.setSkuCode("SKU123");
        productDTO.setProductType(ProductType.SERVICOS);
        productDTO.setUnitOfMeasure(UnitOfMeasure.UNIDADE);

        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName(productDTO.getName());
        productEntity.setSkuCode(productDTO.getSkuCode());
        productEntity.setProductType(productDTO.getProductType());
        productEntity.setUnitOfMeasure(productDTO.getUnitOfMeasure());
        productEntity.setProductStatus(ProductStatus.ACTIVE);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdateAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateProductSuccessfully() {
        // Arrange
        when(productRepository.existsByName(productDTO.getName())).thenReturn(false);
        when(productRepository.existsBySkuCode(productDTO.getSkuCode())).thenReturn(false);
        when(productRepository.save(any())).thenReturn(productEntity);

        // Act
        ReturnProductDTO result = productService.createProduct(productDTO);

        // Assert
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getSkuCode(), result.getSkuCode());
        verify(productRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNameExists() {
        when(productRepository.existsByName(productDTO.getName())).thenReturn(true);

        assertThrows(DuplicateNameProductException.class, () -> {
            productService.createProduct(productDTO);
        });

        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenSkuExists() {
        when(productRepository.existsByName(productDTO.getName())).thenReturn(false);
        when(productRepository.existsBySkuCode(productDTO.getSkuCode())).thenReturn(true);

        assertThrows(DuplicateSkuCodeException.class, () -> {
            productService.createProduct(productDTO);
        });

        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldReturnPaginatedProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        ListProductsDTO listProductsDTO = new ListProductsDTO(0, 10);
        Page<ProductEntity> page = new PageImpl<>(List.of(productEntity));

        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<ReturnProductDTO> result = productService.findAllProducts(listProductsDTO);

        assertEquals(1, result.getContent().size());
        assertEquals(productEntity.getName(), result.getContent().get(0).getName());
    }

    @Test
    void shouldFindProductByIdSuccessfully() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        ReturnProductDTO result = productService.findById(1L);

        assertEquals(productEntity.getName(), result.getName());
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    void shouldFindProductByNameSuccessfully() {
        when(productRepository.findByNameContainingIgnoreCase("Produto Teste")).thenReturn(Optional.of(productEntity));

        ReturnProductDTO result = productService.findByName("Produto Teste");

        assertEquals("Produto Teste", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameNotFound() {
        when(productRepository.findByNameContainingIgnoreCase("Inexistente")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findByName("Inexistente"));
    }

    @Test
    void shouldFindProductBySkuSuccessfully() {
        when(productRepository.findBySkuCode("SKU123")).thenReturn(Optional.of(productEntity));

        ReturnProductDTO result = productService.findBySkuCode("SKU123");

        assertEquals("SKU123", result.getSkuCode());
    }

    @Test
    void shouldThrowExceptionWhenSkuNotFound() {
        when(productRepository.findBySkuCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findBySkuCode("INVALID"));
    }

//    @Test
//    void shouldUpdateProductSuccessfully() {
//        ProductDTO updateDTO = new ProductDTO();
//        updateDTO.setName("Produto Atualizado");
//        updateDTO.setSkuCode("SKU456");
//
//        ProductEntity updatedEntity = new ProductEntity();
//        updatedEntity.setId(1L);
//        updatedEntity.setName("Produto Atualizado");
//        updatedEntity.setSkuCode("SKU456");
//        updatedEntity.setProductType(ProductType.SERVICOS);
//        updatedEntity.setUnitOfMeasure(UnitOfMeasure.UNIDADE);
//        updatedEntity.setProductStatus(ProductStatus.ACTIVE);
//        updatedEntity.setCreatedAt(LocalDateTime.now());
//        updatedEntity.setUpdateAt(LocalDateTime.now());
//
//        when(productRepository.existsByName("Produto Atualizado")).thenReturn(false);
//        when(productRepository.existsBySkuCode("SKU456")).thenReturn(false);
//        when(productRepository.findBySkuCode("SKU123")).thenReturn(Optional.of(productEntity));
//        when(productRepository.save(any())).thenReturn(updatedEntity);
//
//        ReturnProductDTO result = productService.updateProduct(1, updateDTO);
//
//        assertEquals("Produto Atualizado", result.getName());
//        assertEquals("SKU456", result.getSkuCode());
//    }
//
//    @Test
//    void shouldThrowExceptionOnUpdateWhenNameExists() {
//        ProductDTO updateDTO = new ProductDTO();
//        updateDTO.setName("Duplicado");
//
//        when(productRepository.existsByName("Duplicado")).thenReturn(true);
//
//        assertThrows(DuplicateNameProductException.class, () -> {
//            productService.updateProduct("SKU123", updateDTO);
//        });
//    }
//
//    @Test
//    void shouldThrowExceptionOnUpdateWhenSkuExists() {
//        ProductDTO updateDTO = new ProductDTO();
//        updateDTO.setSkuCode("SKU_DUP");
//
//        when(productRepository.existsBySkuCode("SKU_DUP")).thenReturn(true);
//
//        assertThrows(DuplicateSkuCodeException.class, () -> {
//            productService.updateProduct("SKU123", updateDTO);
//        });
//    }
//
//    @Test
//    void shouldThrowExceptionOnUpdateWhenProductNotFound() {
//        ProductDTO updateDTO = new ProductDTO();
//
//        when(productRepository.findBySkuCode("INVALID")).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFoundException.class, () -> {
//            productService.updateProduct("INVALID", updateDTO);
//        });
//    }
}

