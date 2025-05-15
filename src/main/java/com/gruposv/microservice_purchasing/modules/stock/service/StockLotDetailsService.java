package com.gruposv.microservice_purchasing.modules.stock.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.product.exception.IllegalStockMovementException;
import com.gruposv.microservice_purchasing.modules.product.exception.ProductNotFoundException;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductRepository;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_lot_details.ListStockLotDetailsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_lot_details.ReturnStockLotDetailsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_lot_details.StockLotDetailsDTO;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockLocationEntity;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockLotDetailsEntity;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_lot_details.DuplicateStockLotDetailsException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_lot_details.NotFoundStockLotDetailsException;
import com.gruposv.microservice_purchasing.modules.stock.mapper.stock_lot_details.StockLotDetailsMapper;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockBalanceRepository;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockLocationRepository;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockLotDetailsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StockLotDetailsService {

    private final StockLotDetailsRepository stockLotDetailsRepository;
    private final StockBalanceRepository balanceRepository;
    private final StockLocationRepository locationRepository;
    private final ProductRepository productRepository;

    public StockLotDetailsService(StockLotDetailsRepository stockLotDetailsRepository, StockBalanceRepository balanceRepository, StockLocationRepository locationRepository, ProductRepository productRepository) {
        this.stockLotDetailsRepository = stockLotDetailsRepository;
        this.balanceRepository = balanceRepository;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ReturnStockLotDetailsDTO createNewStockLotDetails (StockLotDetailsDTO stockLotDetailsDTO){
        StockLotDetailsEntity stockLotDetails = StockLotDetailsMapper.toCreateEntity(stockLotDetailsDTO);

        if(this.stockLotDetailsRepository.existsByProduct_IdAndLotNumberAndStockLocation_Id(stockLotDetailsDTO.getProductId(), stockLotDetailsDTO.getLotNumber(), stockLotDetailsDTO.getStockLocationId())){
            throw new DuplicateStockLotDetailsException("Esse registro já existe no banco de dados");
        }

        ProductEntity product = this.productRepository.findById(stockLotDetailsDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + stockLotDetailsDTO.getProductId() + " não foi encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(stockLotDetailsDTO.getStockLocationId()).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + stockLotDetailsDTO.getStockLocationId() + " não foi encontrado."));

        stockLotDetails.setProduct(product);
        stockLotDetails.setStockLocation(stockLocation);

        return StockLotDetailsMapper.toDTO(this.stockLotDetailsRepository.save(stockLotDetails));
    }

    public Page<ReturnStockLotDetailsDTO> findAllStockLotDetails(ListStockLotDetailsDTO listStockLotDetailsDTO){
        Pageable pageable = PageRequest.of(listStockLotDetailsDTO.getPage(), listStockLotDetailsDTO.getSize());
        return this.stockLotDetailsRepository.findAll(pageable).map(StockLotDetailsMapper::toDTO);
    }

    public ReturnStockLotDetailsDTO findStockLotDetailsById(Long id){
        return StockLotDetailsMapper.toDTO(this.stockLotDetailsRepository.findById(id).orElseThrow(() -> new NotFoundStockLotDetailsException("O Lote com o ID " + id + " não foi encontrado.")));
    }

    public ReturnStockLotDetailsDTO findByProductIdAndLotNumberAndLocationId(Long productId, String lotNumber, Long locationId){
        return StockLotDetailsMapper.toDTO(this.stockLotDetailsRepository.findByProduct_IdAndLotNumberAndStockLocation_Id(productId, lotNumber, locationId).orElseThrow(() -> new NotFoundStockLotDetailsException("O Lote não foi encontrado.")));
    }

    public void validateLotQuantityForOutbound(ReturnStockLotDetailsDTO lotDetails, Integer requestedQuantity) {
        if (requestedQuantity > lotDetails.getQuantity()) {
            throw new IllegalStockMovementException("Quantidade solicitada (" + requestedQuantity + ") excede a disponível no lote " + lotDetails.getLotNumber() + " (" + lotDetails.getQuantity() + ").");
        }
    }

    public void validateLotExpirationDate(ReturnStockLotDetailsDTO lotDetails) {
        if (lotDetails.getExpirationDate() != null && lotDetails.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalStockMovementException("Não é possível dar saída do lote " + lotDetails.getLotNumber() + ". Data de expiração (" + lotDetails.getExpirationDate() + ") está vencida.");
        }
    }

    public boolean existsByProductIdAndLotNumberAndLocationId(Long productId, String lotNumber, Long locationId) {
        return this.stockLotDetailsRepository.existsByProduct_IdAndLotNumberAndStockLocation_Id(productId, lotNumber, locationId);
    }

    @Transactional
    public void increaseLotQuantity(Long productId, String lotNumber, Long locationId, Integer quantity) {
        StockLotDetailsEntity lotDetailsEntity = this.stockLotDetailsRepository.findByProduct_IdAndLotNumberAndStockLocation_Id(productId, lotNumber, locationId)
                .orElseThrow(() -> new NotFoundStockLotDetailsException("Detalhes do lote não encontrados para atualização de quantidade."));
        lotDetailsEntity.setQuantity(lotDetailsEntity.getQuantity() + quantity);
        this.stockLotDetailsRepository.save(lotDetailsEntity);
    }

    @Transactional
    public void decreaseLotQuantity(Long lotDetailsId, Integer quantity) {
        StockLotDetailsEntity lotDetailsEntity = this.stockLotDetailsRepository.findById(lotDetailsId)
                .orElseThrow(() -> new NotFoundStockLotDetailsException("Detalhes do lote com ID " + lotDetailsId + " não encontrados."));
        lotDetailsEntity.setQuantity(lotDetailsEntity.getQuantity() - quantity);
        this.stockLotDetailsRepository.save(lotDetailsEntity);
    }

    public Page<ReturnStockLotDetailsDTO> findAllByProductIdAndLocationId(Long productId, String lotNumber, Long locationId, ListStockLotDetailsDTO listStockLotDetailsDTO){
        Pageable pageable = PageRequest.of(listStockLotDetailsDTO.getPage(), listStockLotDetailsDTO.getSize());
        return this.stockLotDetailsRepository.findAllByProduct_IdAndStockLocation_Id(productId, locationId, pageable).map(StockLotDetailsMapper::toDTO);
    }

    public Page<ReturnStockLotDetailsDTO> findByProductIdAndLocationIdAndExpirationDateBefore(Long productId, Long locationId, LocalDate expirationDate, ListStockLotDetailsDTO listStockLotDetailsDTO){
        Pageable pageable = PageRequest.of(listStockLotDetailsDTO.getPage(), listStockLotDetailsDTO.getSize());
        return this.stockLotDetailsRepository.findByProduct_IdAndStockLocation_IdAndExpirationDateBefore(productId, locationId, expirationDate, pageable).map(StockLotDetailsMapper::toDTO);
    }

    public Page<ReturnStockLotDetailsDTO> findByProductIdAndLocationIdAndQuantityGreaterThan(Long productId, Long locationId, Integer quantity, ListStockLotDetailsDTO listStockLotDetailsDTO){
        Pageable pageable = PageRequest.of(listStockLotDetailsDTO.getPage(), listStockLotDetailsDTO.getSize());
        return this.stockLotDetailsRepository.findByProduct_IdAndStockLocation_IdAndQuantityGreaterThan(productId, locationId, quantity, pageable).map(StockLotDetailsMapper::toDTO);
    }

    @Transactional
    public ReturnStockLotDetailsDTO updateStockLotDetails(Long id, StockLotDetailsDTO stockLotDetailsDTO){
        StockLotDetailsEntity stockLotDetails = this.stockLotDetailsRepository.findById(id).orElseThrow(() -> new NotFoundStockLotDetailsException("O Lote com o ID " + id + " não foi encontrado."));
        stockLotDetails = StockLotDetailsMapper.toUpdatedEntity(stockLotDetailsDTO, stockLotDetails);
        return StockLotDetailsMapper.toDTO(this.stockLotDetailsRepository.save(stockLotDetails));
    }

    public void DeleteStockLotDetails(Long id){
        StockLotDetailsEntity stockLotDetails = this.stockLotDetailsRepository.findById(id).orElseThrow(() -> new NotFoundStockLotDetailsException("O Lote com o ID " + id + " não foi encontrado."));
        stockLotDetails.setDeletedAt(LocalDateTime.now());
        this.stockLotDetailsRepository.save(stockLotDetails);
    }

}
