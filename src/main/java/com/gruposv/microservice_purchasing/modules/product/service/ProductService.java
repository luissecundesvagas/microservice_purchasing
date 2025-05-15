package com.gruposv.microservice_purchasing.modules.product.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gruposv.microservice_purchasing.modules.product.dto.attributes.ListAttributesDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.attributes.ProductAttributeDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.categories.ListCategoriesDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.categories.ProductCategoryDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.products.ListProductsDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.products.ProductDTO;
import com.gruposv.microservice_purchasing.modules.product.dto.products.ReturnProductDTO;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductAttributeEntity;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductCategoryEntity;
import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.product.enums.ProductStatus;
import com.gruposv.microservice_purchasing.modules.product.exception.*;
import com.gruposv.microservice_purchasing.modules.product.mapper.attributes.AttributesMapper;
import com.gruposv.microservice_purchasing.modules.product.mapper.categories.CategoriesMapper;
import com.gruposv.microservice_purchasing.modules.product.mapper.products.ProductMapper;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductAttributeRepository;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductCategoryRepository;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository productRepository, ProductAttributeRepository productAttributeRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    public ReturnProductDTO createProduct(ProductDTO productDTO){

        ProductEntity productToSaved = ProductMapper.toEntity(productDTO);

        // Verificar se o SKU ou o nome é repetido
        if(this.productRepository.existsByName(productDTO.getName())) throw new DuplicateNameProductException("Nome do produto já existente na base de dados. Forneça um nome diferente.");
        if(this.productRepository.existsBySkuCode(productDTO.getSkuCode())) throw new DuplicateSkuCodeException("Código do produto já existente na base de dados. Forneça um código diferente.");

        // Verificar se existem categorias ou atributos para serem cadastrados ou associados
        this.processProductAttributes(productDTO, productToSaved);
        this.processProductCategories(productDTO, productToSaved);

        productToSaved.setCreatedAt(LocalDateTime.now());
        productToSaved.setUpdateAt(LocalDateTime.now());
        productToSaved.setProductStatus(ProductStatus.ACTIVE);

        ProductEntity product = this.productRepository.save(productToSaved);
        return ProductMapper.returnDTO(product);
    }

    public Page<ReturnProductDTO> findAllProducts(ListProductsDTO listProductsDTO){
        Pageable pageable = PageRequest.of(listProductsDTO.getPage(), listProductsDTO.getSize());
        return this.productRepository.findAll(pageable).map(ProductMapper::returnDTO);
    }

    public ReturnProductDTO findById(Long id){
        return this.productRepository.findById(id).map(ProductMapper::returnDTO).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + id + " Não foi encontrado."));
    }

    public ReturnProductDTO findByName(String name){
        return this.productRepository.findByNameContainingIgnoreCase(name).map(ProductMapper::returnDTO).orElseThrow(() -> new ProductNotFoundException("O produto com o nome " + name + " Não foi encontrado."));
    }

    public ReturnProductDTO findBySkuCode(String skuCode){
        return this.productRepository.findBySkuCode(skuCode).map(ProductMapper::returnDTO).orElseThrow(() -> new ProductNotFoundException("O produto com o código " + skuCode + " Não foi encontrado."));
    }

    public ReturnProductDTO updateProduct(Long id, ProductDTO productDTO){
        ProductEntity productToUpdate = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + id + " Não foi encontrado."));

        if (productDTO.getName() != null && !productDTO.getName().isEmpty() && !productDTO.getName().equals(productToUpdate.getName())) {
            if (this.productRepository.existsByName(productDTO.getName())) {
                throw new DuplicateNameProductException("Nome do produto já existente na base de dados. Forneça um nome diferente.");
            }
            productToUpdate.setName(productDTO.getName());
        }

        if (productDTO.getSkuCode() != null && !productDTO.getSkuCode().isEmpty() && !productDTO.getSkuCode().equals(productToUpdate.getSkuCode())) {
            if (this.productRepository.existsBySkuCode(productDTO.getSkuCode())) {
                throw new DuplicateSkuCodeException("Código do produto já existente na base de dados. Forneça um código diferente.");
            }
            productToUpdate.setSkuCode(productDTO.getSkuCode());
        }

        // Atualizar outros campos da entidade com base no DTO
        productToUpdate = ProductMapper.toEntityUpdate(productDTO, productToUpdate);
        this.processProductAttributes(productDTO, productToUpdate);
        this.processProductCategories(productDTO, productToUpdate);

        ProductEntity productUpdated = this.productRepository.save(productToUpdate);

        return ProductMapper.returnDTO(productUpdated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + id + " não foi encontrado."));
        product.setDeletedAt(LocalDateTime.now());
        product.setProductStatus(ProductStatus.INACTIVE);
        this.productRepository.save(product);
    }

    @Transactional
    public void deleteProductsList(List<Long> idList){
        List<ProductEntity> products = this.productRepository.findByIdIn(idList);
        if(products.isEmpty()) throw new ProductNotFoundException("Nenhum produto foi encontrado.");
        products
                .stream()
                .forEach(product -> {
                    product.setProductStatus(ProductStatus.INACTIVE);
                    product.setDeletedAt(LocalDateTime.now());
                    this.productRepository.save(product);
                });
    }

    public Page<ProductCategoryDTO> returnAllCategories(ListCategoriesDTO listCategoriesDTO){
        Pageable pageable = PageRequest.of(listCategoriesDTO.getPage(), listCategoriesDTO.getSize());
        return this.productCategoryRepository.findAll(pageable).map(CategoriesMapper::toDTO);
    }

    public List<ProductCategoryDTO> returnAllCategoriesByProductId(Long productId){
        return this.productCategoryRepository.findByProductId(productId).stream().map(CategoriesMapper::toDTO).toList();
    }

    public ProductCategoryDTO createProductCategory(ProductCategoryDTO productCategoryDTO) {

        if(this.productCategoryRepository.existsByName(productCategoryDTO.getName())) throw new DuplicateCategoryException("Essa categoria já existe, portanto não pode ser cadastrada.");
        ProductCategoryEntity productCategory = CategoriesMapper.toEntity(productCategoryDTO);
        return CategoriesMapper.toDTO(this.productCategoryRepository.save(productCategory));
    }

    public void deleteCategory(Long categoryId){
        ProductCategoryEntity category = this.productCategoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("A Categoria com o ID " + categoryId + " não foi encontrada."));
        category.setDeletedAt(LocalDateTime.now());
        this.productCategoryRepository.save(category);
    }

    public Page<ProductAttributeDTO> returnAllAttributes(ListAttributesDTO listAttributesDTO){
        Pageable pageable = PageRequest.of(listAttributesDTO.getPage(), listAttributesDTO.getSize());
        return this.productAttributeRepository.findAll(pageable).map(AttributesMapper::toDTO);
    }

    public List<ProductAttributeDTO> returnAllAttributesByProductId(Long productId){
        return this.productAttributeRepository.findByProductId(productId).stream().map(AttributesMapper::toDTO).toList();
    }

    public ProductAttributeDTO createProductAttribute(ProductAttributeDTO productAttributeDTO) {
        if(this.productAttributeRepository.existsByAttributeNameAndAttributeValue(productAttributeDTO.getAttributeName(), productAttributeDTO.getAttributeValue())) throw new DuplicateAttributeException("Esse atributo já existe, portanto não pode ser cadastrado.");
        ProductAttributeEntity productAttribute = AttributesMapper.toEntity(productAttributeDTO);
        return AttributesMapper.toDTO(this.productAttributeRepository.save(productAttribute));
    }

    public void deleteAttribute(Long attributeId){
        ProductAttributeEntity attribute = this.productAttributeRepository.findById(attributeId).orElseThrow(() -> new AttributeNotFoundException("O atributo com o ID: " + attributeId + " não foi encontrado"));
        attribute.setDeletedAt(LocalDateTime.now());
        this.productAttributeRepository.save(attribute);
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    private void processProductAttributes(ProductDTO productDTO, ProductEntity productToSaved){
        if(productDTO.getAttributes() != null && !productDTO.getAttributes().isEmpty()) {
            List<ProductAttributeEntity> attributes = productDTO.getAttributes().stream()
                    .map(attributeDTO -> {

                        if(attributeDTO.getAttributeValue() == null || attributeDTO.getAttributeValue().isEmpty() || attributeDTO.getAttributeValue().isBlank()) throw new RuntimeException("O campo de valor do atributo deve possui um valor válido.");
                        if(attributeDTO.getAttributeName() == null || attributeDTO.getAttributeName().isEmpty() || attributeDTO.getAttributeName().isBlank()) throw new RuntimeException("O campo de nome do atributo deve possui um valor válido.");

                        return this.productAttributeRepository
                            .findByAttributeNameAndAttributeValue(attributeDTO.getAttributeName(), attributeDTO.getAttributeValue())
                            .orElseGet(() -> this.productAttributeRepository.save(AttributesMapper.toEntity(attributeDTO)));

                    })
                    .collect(Collectors.toList());
            productToSaved.setProductAttributes(attributes);
        }
    }

    private void processProductCategories(ProductDTO productDTO, ProductEntity productToSaved){
        if(productDTO.getCategories() != null && !productDTO.getCategories().isEmpty()) {
            List<ProductCategoryEntity> categories = productDTO.getCategories().stream()
                    .map(categoryDTO -> {

                        if(categoryDTO.getName() == null || categoryDTO.getName().isEmpty() || categoryDTO.getName().isBlank()) throw new RuntimeException("O campo de nome da categoria deve possui um valor válido.");

                        return this.productCategoryRepository
                            .findByNameContainingIgnoreCase(categoryDTO.getName())
                            .orElseGet(() -> this.productCategoryRepository.save(CategoriesMapper.toEntity(categoryDTO)));

                    })
                    .collect(Collectors.toList());
            productToSaved.setProductCategories(categories);
        }
    }

}
