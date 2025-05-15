package com.gruposv.microservice_stock.modules.stock.service;

import com.gruposv.microservice_stock.modules.product.entity.ProductEntity;
import com.gruposv.microservice_stock.modules.product.exception.ProductNotFoundException;
import com.gruposv.microservice_stock.modules.product.repository.ProductRepository;
import com.gruposv.microservice_stock.modules.stock.dto.stock_balance.ListStockBalanceDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_balance.ReturnStockBalanceDTO;
import com.gruposv.microservice_stock.modules.stock.dto.stock_balance.StockBalanceDTO;
import com.gruposv.microservice_stock.modules.stock.entity.StockBalanceEntity;
import com.gruposv.microservice_stock.modules.stock.entity.StockLocationEntity;
import com.gruposv.microservice_stock.modules.stock.exception.stock_balance.DuplicateStockBalanceException;
import com.gruposv.microservice_stock.modules.stock.exception.stock_balance.StockBalanceNotFoundException;
import com.gruposv.microservice_stock.modules.stock.exception.stock_location.StockLocationNotFoundException;
import com.gruposv.microservice_stock.modules.stock.mapper.stock_balance.StockBalanceMapper;
import com.gruposv.microservice_stock.modules.stock.repository.StockBalanceRepository;
import com.gruposv.microservice_stock.modules.stock.repository.StockLocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StockBalanceService {

    private final StockBalanceRepository stockBalanceRepository;
    private final ProductRepository productRepository;
    private final StockLocationRepository stockLocationRepository;

    public StockBalanceService(StockBalanceRepository stockBalanceRepository, ProductRepository productRepository, StockLocationRepository stockLocationRepository) {
        this.stockBalanceRepository = stockBalanceRepository;
        this.productRepository = productRepository;
        this.stockLocationRepository = stockLocationRepository;
    }

    @Transactional
    public ReturnStockBalanceDTO createNewStockBalance(StockBalanceDTO stockBalanceDTO){
        if(this.stockBalanceRepository.existsByProduct_IdAndStockLocation_Id(stockBalanceDTO.getProductId(), stockBalanceDTO.getStockLocationId()))
            throw new DuplicateStockBalanceException("Esse balanço de estoque já existe no banco de dados.");

        // Buscar produto e local de estoque antes de cadastrar o saldo de estoque
        ProductEntity product = this.productRepository.findById(stockBalanceDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + stockBalanceDTO.getProductId() + " não foi encontrado."));
        StockLocationEntity stockLocation = this.stockLocationRepository.findById(stockBalanceDTO.getStockLocationId()).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + stockBalanceDTO.getStockLocationId() + " não foi encontrado."));

        StockBalanceEntity stockBalance = StockBalanceMapper.toEntity(stockBalanceDTO);
        stockBalance.setProduct(product);
        stockBalance.setStockLocation(stockLocation);
        return StockBalanceMapper.toDTO(this.stockBalanceRepository.save(stockBalance));
    }

    public Page<ReturnStockBalanceDTO> findAllStockBalance(ListStockBalanceDTO listStockBalanceDTO){
        Pageable pageable = PageRequest.of(listStockBalanceDTO.getPage(), listStockBalanceDTO.getSize());
        return this.stockBalanceRepository.findAll(pageable).map(StockBalanceMapper::toDTO);
    }

    public ReturnStockBalanceDTO findByProductIdAndLocationId(Long productId, Long locationId){
        return StockBalanceMapper.toDTO(this.stockBalanceRepository.findByProduct_IdAndStockLocation_Id(productId, locationId).orElseThrow(() -> new StockBalanceNotFoundException("O saldo de estoque do produto com o id: " + productId + " e do local de estoque com o id " + locationId + " não foi encontrado.")));
    }

    public Page<ReturnStockBalanceDTO> findAllByProductId(Long productId, ListStockBalanceDTO listStockBalanceDTO){
        Pageable pageable = PageRequest.of(listStockBalanceDTO.getPage(), listStockBalanceDTO.getSize());
        return this.stockBalanceRepository.findAllByProduct_Id(productId, pageable).map(StockBalanceMapper::toDTO);
    }

    public ReturnStockBalanceDTO findByProductIdAndLocationIdAndQuantityOnHandGreaterThanEqual(Long productId, Long locationId, Integer quantity){
        return StockBalanceMapper.toDTO(this.stockBalanceRepository.findByProduct_IdAndStockLocation_IdAndQuantityOnHandGreaterThanEqual(productId, locationId, quantity).orElseThrow(() -> new StockBalanceNotFoundException("O saldo de estoque do produto com o id: " + productId + " e do local de estoque com o id " + locationId + " E a quantidade igual ou maior que " + quantity + " não foi encontrado.")));
    }

    public ReturnStockBalanceDTO findByProductIdAndLocationIdAndQuantityOnHandGreaterThan(Long productId, Long locationId, Integer quantity){
        return StockBalanceMapper.toDTO(this.stockBalanceRepository.findByProduct_IdAndStockLocation_IdAndQuantityOnHandGreaterThan(productId, locationId, quantity).orElseThrow(() -> new StockBalanceNotFoundException("O saldo de estoque do produto com o id: " + productId + " e do local de estoque com o id " + locationId + " E a quantidade maior que " + quantity + " não foi encontrado.")));
    }

    public ReturnStockBalanceDTO findByProductIdAndLocationIdAndQuantityOnHandLessThanOrEqualTo(Long productId, Long locationId, Integer quantity){
        return StockBalanceMapper.toDTO(this.stockBalanceRepository.findByProduct_IdAndStockLocation_IdAndQuantityOnHandLessThanEqual(productId, locationId, quantity).orElseThrow(() -> new StockBalanceNotFoundException("O saldo de estoque do produto com o id: " + productId + " e do local de estoque com o id " + locationId + " E a quantidade igual ou menor que " + quantity + " não foi encontrado.")));
    }

    @Transactional
    public ReturnStockBalanceDTO updateStockBalance(StockBalanceDTO stockBalanceDTO){
        StockBalanceEntity stockBalance = this.stockBalanceRepository.findByProduct_IdAndStockLocation_Id(stockBalanceDTO.getProductId(), stockBalanceDTO.getStockLocationId())
                .orElseThrow(() -> new StockBalanceNotFoundException("O saldo de estoque do produto com o id: " + stockBalanceDTO.getProductId() + " e do local de estoque com o id " + stockBalanceDTO.getStockLocationId() + " não foi encontrado."));
        StockBalanceEntity stockBalanceUpdated = StockBalanceMapper.toEntityUpdate(stockBalance, stockBalanceDTO);
        return StockBalanceMapper.toDTO(this.stockBalanceRepository.save(stockBalanceUpdated));
    }

    public void deleteStockBalance(Long productId, Long locationId){
        StockBalanceEntity stockBalance = this.stockBalanceRepository.findByProduct_IdAndStockLocation_Id(productId, locationId)
                .orElseThrow(() -> new StockBalanceNotFoundException("O saldo de estoque do produto com o id: " + productId + " e do local de estoque com o id " + locationId + " não foi encontrado."));
        stockBalance.setDeletedAt(LocalDateTime.now());
        this.stockBalanceRepository.save(stockBalance);
    }
}
