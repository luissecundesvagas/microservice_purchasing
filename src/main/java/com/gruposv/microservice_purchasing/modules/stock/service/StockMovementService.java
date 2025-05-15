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
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_lot_details.ReturnStockLotDetailsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_lot_details.StockLotDetailsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.ListStockMovementDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.RegisterStockMovementsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.RegisterStockTransferDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.StockMovementDTO;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockBalanceEntity;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockLocationEntity;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockMovementsEntity;
import com.gruposv.microservice_purchasing.modules.stock.enums.MovementType;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance.StockBalanceNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.mapper.stock_movements.StockMovementsMapper;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockBalanceRepository;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockLocationRepository;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockMovementRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final StockBalanceRepository balanceRepository;
    private final StockLocationRepository locationRepository;
    private final ProductRepository productRepository;
    private final StockLotDetailsService stockLotDetailsService;

    public StockMovementService(StockMovementRepository stockMovementRepository, StockBalanceRepository balanceRepository, StockLocationRepository locationRepository, ProductRepository productRepository, StockLotDetailsService stockLotDetailsService) {
        this.stockMovementRepository = stockMovementRepository;
        this.balanceRepository = balanceRepository;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
        this.stockLotDetailsService = stockLotDetailsService;
    }

    @Transactional
    public StockMovementDTO registerStockInbound(RegisterStockMovementsDTO registerStockMovementsDTO){
        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);

        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + registerStockMovementsDTO.getProductId() + " não foi encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId()).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + registerStockMovementsDTO.getLocationId() + " não foi encontrado."));

        // Verificando de registro em stock balance
        Optional<StockBalanceEntity> stockBalanceOptional = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId());
        if(stockBalanceOptional.isEmpty()){
            this.balanceRepository.save(new StockBalanceEntity(null, product, stockLocation, registerStockMovementsDTO.getQuantity(), 0, 1));
        } else {
            StockBalanceEntity stockBalance = stockBalanceOptional.get();
            stockBalance.setQuantityOnHand(stockBalance.getQuantityOnHand() + registerStockMovementsDTO.getQuantity());
            this.balanceRepository.save(stockBalance);
        }

        stockMovements.setMovementType(MovementType.ENTRADA);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setProduct(product);
        stockMovements.setStockLocation(stockLocation);

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public StockMovementDTO registerStockOutbound(RegisterStockMovementsDTO registerStockMovementsDTO){
        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);

        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + registerStockMovementsDTO.getProductId() + " não foi encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId()).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + registerStockMovementsDTO.getLocationId() + " não foi encontrado."));

        // Verificando de registro em stock balance para salvar a saída de estoque de um produto
        StockBalanceEntity stockBalance = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId())
                .orElseThrow(() -> new StockBalanceNotFoundException("Não foi encontrado um slado de estoque para o produto " + product.getSkuCode() + " no estoque " + stockLocation.getLocationName() + "."));

        Integer realProductStockValue = stockBalance.getQuantityOnHand() - stockBalance.getQuantityReserved();
        if(registerStockMovementsDTO.getQuantity() > realProductStockValue) throw new IllegalStockMovementException("Não é possível realizar essa retitrada de estoque por saldo insuficiente do produto " + product.getName() + " e do estoque " + stockLocation.getLocationName());
        stockBalance.setQuantityOnHand(stockBalance.getQuantityOnHand() - registerStockMovementsDTO.getQuantity());
        this.balanceRepository.save(stockBalance);

        stockMovements.setMovementType(MovementType.SAIDA);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setProduct(product);
        stockMovements.setStockLocation(stockLocation);

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public List<StockMovementDTO> registerStockTransfer(RegisterStockTransferDTO registerStockTransferDTO){
        StockMovementsEntity stockMovementsOut = StockMovementsMapper.toCreateTransferEntity(registerStockTransferDTO);
        StockMovementsEntity stockMovementsIn = StockMovementsMapper.toCreateTransferEntity(registerStockTransferDTO); // Criar outra entidade para a entrada

        ProductEntity product = this.productRepository.findById(registerStockTransferDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + registerStockTransferDTO.getProductId() + " não foi encontrado."));
        StockLocationEntity stockLocationOrigin = this.locationRepository.findById(registerStockTransferDTO.getLocationOriginId()).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + registerStockTransferDTO.getLocationOriginId() + " não foi encontrado."));
        StockLocationEntity stockLocationDestination = this.locationRepository.findById(registerStockTransferDTO.getLocationDestinationId()).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + registerStockTransferDTO.getLocationDestinationId() + " não foi encontrado."));

        // Verificar saldo de estoque na origem
        StockBalanceEntity stockBalanceOrigin = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocationOrigin.getId())
                .orElseThrow(() -> new StockBalanceNotFoundException("O saldo de estoque do produto " + product.getName() + " no estoque " + stockLocationOrigin.getLocationName() + " não foi encontrado."));

        // Registrar a saída do estoque de origem
        Integer realProductStockValue = stockBalanceOrigin.getQuantityOnHand() - stockBalanceOrigin.getQuantityReserved();
        if(registerStockTransferDTO.getQuantity() > realProductStockValue) throw new IllegalStockMovementException("Não é possível realizar essa transferência por saldo insuficiente do produto " + product.getName() + " no estoque " + stockLocationOrigin.getLocationName());
        stockBalanceOrigin.setQuantityOnHand(stockBalanceOrigin.getQuantityOnHand() - registerStockTransferDTO.getQuantity());
        this.balanceRepository.save(stockBalanceOrigin);

        // Registrar a entrada no estoque de destino
        Optional<StockBalanceEntity> stockBalanceDestinationOptional = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocationDestination.getId());
        StockBalanceEntity stockBalanceDestination;
        if(stockBalanceDestinationOptional.isEmpty()){
            stockBalanceDestination = new StockBalanceEntity(null, product, stockLocationDestination, registerStockTransferDTO.getQuantity(), 0, 1);
        } else {
            stockBalanceDestination = stockBalanceDestinationOptional.get();
            stockBalanceDestination.setQuantityOnHand(stockBalanceDestination.getQuantityOnHand() + registerStockTransferDTO.getQuantity());
        }
        this.balanceRepository.save(stockBalanceDestination);

        // Lista de movimentações de estoque
        List<StockMovementDTO> stockMovementList = new ArrayList<>();

        // Registrar a movimentação do estoque de origem (SAÍDA)
        stockMovementsOut.setProduct(product);
        stockMovementsOut.setStockLocation(stockLocationOrigin);
        stockMovementsOut.setMovementType(MovementType.SAIDA);
        stockMovementsOut.setMovementDate(LocalDateTime.now());
        stockMovementsOut.setRemarks("Transferencia de estoque. Saída: " + stockLocationOrigin.getLocationName() + ". Entrada: " + stockLocationDestination.getLocationName());
        stockMovementList.add(StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovementsOut)));

        // Registrar a movimentação do estoque de destino (ENTRADA)
        stockMovementsIn.setProduct(product);
        stockMovementsIn.setStockLocation(stockLocationDestination);
        stockMovementsIn.setMovementType(MovementType.ENTRADA);
        stockMovementsIn.setMovementDate(LocalDateTime.now());
        stockMovementsIn.setRemarks("Transferencia de estoque. Saída: " + stockLocationOrigin.getLocationName() + ". Entrada: " + stockLocationDestination.getLocationName());
        stockMovementList.add(StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovementsIn)));

        return stockMovementList;
    }

    @Transactional
    public StockMovementDTO registerStockAdjustment(RegisterStockMovementsDTO registerStockMovementsDTO) {
        if (registerStockMovementsDTO.getRemarks() == null || registerStockMovementsDTO.getRemarks().trim().isEmpty()) {
            throw new IllegalArgumentException("A justificativa (remarks) é obrigatória para um ajuste de estoque.");
        }

        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);

        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockMovementsDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque com ID " + registerStockMovementsDTO.getLocationId() + " não encontrado."));

        Optional<StockBalanceEntity> stockBalanceOptional = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId());
        StockBalanceEntity stockBalance;

        if (stockBalanceOptional.isEmpty()) {
            throw new StockBalanceNotFoundException("Não foi possível encontrar o estoque para o produto " + product.getName() + " no local " + stockLocation.getLocationName());
        } else {
            stockBalance = stockBalanceOptional.get();
            stockBalance.setQuantityOnHand(registerStockMovementsDTO.getQuantity());
        }
        this.balanceRepository.save(stockBalance);

        stockMovements.setMovementType(MovementType.AJUSTE);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setProduct(product);
        stockMovements.setStockLocation(stockLocation);
        stockMovements.setRemarks(registerStockMovementsDTO.getRemarks());

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public StockMovementDTO registerStockReservation(RegisterStockMovementsDTO registerStockMovementsDTO) {
        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);

        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockMovementsDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque com ID " + registerStockMovementsDTO.getLocationId() + " não encontrado."));

        StockBalanceEntity stockBalance = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId())
                .orElseThrow(() -> new StockBalanceNotFoundException("Saldo de estoque não encontrado para o produto " + product.getName() + " no estoque " + stockLocation.getLocationName() + "."));

        if (registerStockMovementsDTO.getQuantity() > (stockBalance.getQuantityOnHand() - stockBalance.getQuantityReserved())) {
            throw new IllegalStockMovementException("Não é possível reservar essa quantidade. Saldo disponível insuficiente para o produto " + product.getName() + " no estoque " + stockLocation.getLocationName() + ".");
        }

        if(registerStockMovementsDTO.getRemarks() == null || registerStockMovementsDTO.getRemarks().isEmpty()) throw new IllegalStockMovementException("Para fazer um reajuste de estoque, é necessário preencher o campo de observação!");

        stockBalance.setQuantityReserved(stockBalance.getQuantityReserved() + registerStockMovementsDTO.getQuantity());
        this.balanceRepository.save(stockBalance);

        stockMovements.setMovementType(MovementType.RESERVA);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setProduct(product);
        stockMovements.setStockLocation(stockLocation);
        stockMovements.setRemarks(registerStockMovementsDTO.getRemarks());

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public StockMovementDTO registerStockInboundWithLot(RegisterStockMovementsDTO registerStockMovementsDTO) {
        if (registerStockMovementsDTO.getLotNumber() == null || registerStockMovementsDTO.getLotNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("O número do lote é obrigatório para entrada com lote.");
        }

        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);
        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockMovementsDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque com ID " + registerStockMovementsDTO.getLocationId() + " não encontrado."));

        if (stockLotDetailsService.existsByProductIdAndLotNumberAndLocationId(product.getId(), registerStockMovementsDTO.getLotNumber(), stockLocation.getId())) {
            // Lote já existe, adicionar a quantidade ao lote existente
            stockLotDetailsService.increaseLotQuantity(
                    product.getId(),
                    registerStockMovementsDTO.getLotNumber(),
                    stockLocation.getId(),
                    registerStockMovementsDTO.getQuantity()
            );
        } else {
            // Lote não encontrado, criar um novo
            StockLotDetailsDTO newLotDTO = new StockLotDetailsDTO(
                    registerStockMovementsDTO.getProductId(),
                    registerStockMovementsDTO.getLocationId(),
                    registerStockMovementsDTO.getLotNumber(),
                    registerStockMovementsDTO.getExpirationDate(),
                    registerStockMovementsDTO.getQuantity()
            );
            stockLotDetailsService.createNewStockLotDetails(newLotDTO);
        }

        Optional<StockBalanceEntity> stockBalanceOptional = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId());
        StockBalanceEntity stockBalance;
        if (stockBalanceOptional.isEmpty()) {
            stockBalance = new StockBalanceEntity(null, product, stockLocation, registerStockMovementsDTO.getQuantity(), 0, 1);
        } else {
            stockBalance = stockBalanceOptional.get();
            stockBalance.setQuantityOnHand(stockBalance.getQuantityOnHand() + registerStockMovementsDTO.getQuantity());
        }
        this.balanceRepository.save(stockBalance);

        stockMovements.setMovementType(MovementType.ENTRADA);
        stockMovements.setProduct(product);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setStockLocation(stockLocation);
        stockMovements.setLotNumber(registerStockMovementsDTO.getLotNumber());

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public StockMovementDTO registerStockOutboundWithLot(RegisterStockMovementsDTO registerStockMovementsDTO) {
        if (registerStockMovementsDTO.getLotNumber() == null || registerStockMovementsDTO.getLotNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("O número do lote é obrigatório para saída com lote.");
        }

        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);
        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockMovementsDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque com ID " + registerStockMovementsDTO.getLocationId() + " não encontrado."));

        ReturnStockLotDetailsDTO lotDetails = stockLotDetailsService.findByProductIdAndLotNumberAndLocationId(
                product.getId(), registerStockMovementsDTO.getLotNumber(), stockLocation.getId()
        );

        stockLotDetailsService.validateLotQuantityForOutbound(lotDetails, registerStockMovementsDTO.getQuantity());
        stockLotDetailsService.validateLotExpirationDate(lotDetails);
        stockLotDetailsService.decreaseLotQuantity(lotDetails.getId(), registerStockMovementsDTO.getQuantity());

        StockBalanceEntity stockBalance = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId())
                .orElseThrow(() -> new StockBalanceNotFoundException("Saldo de estoque não encontrado para o produto " + product.getName() + " no estoque " + stockLocation.getLocationName() + "."));
        stockBalance.setQuantityOnHand(stockBalance.getQuantityOnHand() - registerStockMovementsDTO.getQuantity());
        this.balanceRepository.save(stockBalance);

        stockMovements.setMovementType(MovementType.SAIDA);
        stockMovements.setProduct(product);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setStockLocation(stockLocation);
        stockMovements.setLotNumber(registerStockMovementsDTO.getLotNumber());

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public List<StockMovementDTO> registerStockTransferWithLot(RegisterStockTransferDTO registerStockTransferDTO) {
        if (registerStockTransferDTO.getLotNumber() == null || registerStockTransferDTO.getLotNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("O número do lote é obrigatório para transferência com lote.");
        }

        if (registerStockTransferDTO.getExpirationDate() == null) {
            throw new IllegalArgumentException("A data de vencimento do lote é obrigatório para transferência com lote.");
        }

        StockMovementsEntity stockMovementsOut = StockMovementsMapper.toCreateTransferEntity(registerStockTransferDTO);
        StockMovementsEntity stockMovementsIn = StockMovementsMapper.toCreateTransferEntity(registerStockTransferDTO);

        ProductEntity product = this.productRepository.findById(registerStockTransferDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockTransferDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocationOrigin = this.locationRepository.findById(registerStockTransferDTO.getLocationOriginId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque de origem com ID " + registerStockTransferDTO.getLocationOriginId() + " não encontrado."));
        StockLocationEntity stockLocationDestination = this.locationRepository.findById(registerStockTransferDTO.getLocationDestinationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque de destino com ID " + registerStockTransferDTO.getLocationDestinationId() + " não encontrado."));

        ReturnStockLotDetailsDTO lotDetailsOrigin = stockLotDetailsService.findByProductIdAndLotNumberAndLocationId(
                product.getId(), registerStockTransferDTO.getLotNumber(), stockLocationOrigin.getId()
        );

        stockLotDetailsService.validateLotQuantityForOutbound(lotDetailsOrigin, registerStockTransferDTO.getQuantity());
        stockLotDetailsService.validateLotExpirationDate(lotDetailsOrigin);
        stockLotDetailsService.decreaseLotQuantity(lotDetailsOrigin.getId(), registerStockTransferDTO.getQuantity());

        Optional<StockBalanceEntity> stockBalanceOriginOptional = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocationOrigin.getId());
        StockBalanceEntity stockBalanceOrigin = stockBalanceOriginOptional.orElseThrow(() -> new StockBalanceNotFoundException("Saldo de estoque não encontrado para o produto no estoque de origem."));
        stockBalanceOrigin.setQuantityOnHand(stockBalanceOrigin.getQuantityOnHand() - registerStockTransferDTO.getQuantity());
        this.balanceRepository.save(stockBalanceOrigin);

        // Lógica para entrada no destino
        if (stockLotDetailsService.existsByProductIdAndLotNumberAndLocationId(product.getId(), registerStockTransferDTO.getLotNumber(), stockLocationDestination.getId())) {
            stockLotDetailsService.increaseLotQuantity(product.getId(), registerStockTransferDTO.getLotNumber(), stockLocationDestination.getId(), registerStockTransferDTO.getQuantity());
        } else {
            StockLotDetailsDTO newLotDTO = new StockLotDetailsDTO(
                    registerStockTransferDTO.getProductId(),
                    registerStockTransferDTO.getLocationDestinationId(),
                    registerStockTransferDTO.getLotNumber(),
                    registerStockTransferDTO.getExpirationDate(),
                    registerStockTransferDTO.getQuantity()
            );
            stockLotDetailsService.createNewStockLotDetails(newLotDTO);
        }

        Optional<StockBalanceEntity> stockBalanceDestinationOptional = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocationDestination.getId());
        StockBalanceEntity stockBalanceDestination;
        if (stockBalanceDestinationOptional.isEmpty()) {
            stockBalanceDestination = new StockBalanceEntity(null, product, stockLocationDestination, registerStockTransferDTO.getQuantity(), 0, 1);
        } else {
            stockBalanceDestination = stockBalanceDestinationOptional.get();
            stockBalanceDestination.setQuantityOnHand(stockBalanceDestination.getQuantityOnHand() + registerStockTransferDTO.getQuantity());
        }
        this.balanceRepository.save(stockBalanceDestination);

        List<StockMovementDTO> stockMovementList = new ArrayList<>();

        stockMovementsOut.setProduct(product);
        stockMovementsOut.setStockLocation(stockLocationOrigin);
        stockMovementsOut.setMovementType(MovementType.SAIDA);
        stockMovementsOut.setMovementDate(LocalDateTime.now());
        stockMovementsOut.setLotNumber(registerStockTransferDTO.getLotNumber());
        stockMovementsOut.setRemarks("Transferencia de estoque. Saída: " + stockLocationOrigin.getLocationName() + ". Entrada: " + stockLocationDestination.getLocationName());
        stockMovementList.add(StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovementsOut)));

        stockMovementsIn.setProduct(product);
        stockMovementsIn.setStockLocation(stockLocationDestination);
        stockMovementsIn.setMovementType(MovementType.ENTRADA);
        stockMovementsIn.setMovementDate(LocalDateTime.now());
        stockMovementsIn.setLotNumber(registerStockTransferDTO.getLotNumber());
        stockMovementsIn.setRemarks("Transferencia de estoque. Saída: " + stockLocationOrigin.getLocationName() + ". Entrada: " + stockLocationDestination.getLocationName());
        stockMovementList.add(StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovementsIn)));

        return stockMovementList;
    }

    @Transactional
    public StockMovementDTO registerStockAdjustmentWithLot(RegisterStockMovementsDTO registerStockMovementsDTO) {
        if (registerStockMovementsDTO.getLotNumber() == null || registerStockMovementsDTO.getLotNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("O número do lote é obrigatório para ajuste com lote.");
        }
        if (registerStockMovementsDTO.getRemarks() == null || registerStockMovementsDTO.getRemarks().trim().isEmpty()) {
            throw new IllegalArgumentException("A justificativa é obrigatória para um ajuste de estoque.");
        }

        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);
        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockMovementsDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque com ID " + registerStockMovementsDTO.getLocationId() + " não encontrado."));

        ReturnStockLotDetailsDTO lotDetails = stockLotDetailsService.findByProductIdAndLotNumberAndLocationId(
                product.getId(), registerStockMovementsDTO.getLotNumber(), stockLocation.getId()
        );

        stockLotDetailsService.increaseLotQuantity(product.getId(), registerStockMovementsDTO.getLotNumber(), stockLocation.getId(), registerStockMovementsDTO.getQuantity());

        Optional<StockBalanceEntity> stockBalanceOptional = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId());
        StockBalanceEntity stockBalance;
        if (stockBalanceOptional.isEmpty()) {
            stockBalance = new StockBalanceEntity(null, product, stockLocation, registerStockMovementsDTO.getQuantity(), null, 1);
        } else {
            stockBalance = stockBalanceOptional.get();
            stockBalance.setQuantityOnHand(registerStockMovementsDTO.getQuantity());
        }
        this.balanceRepository.save(stockBalance);

        stockMovements.setMovementType(MovementType.AJUSTE);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setProduct(product);
        stockMovements.setStockLocation(stockLocation);
        stockMovements.setRemarks(registerStockMovementsDTO.getRemarks());
        stockMovements.setLotNumber(registerStockMovementsDTO.getLotNumber());

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public StockMovementDTO registerStockReservationWithLot(RegisterStockMovementsDTO registerStockMovementsDTO) {
        if (registerStockMovementsDTO.getLotNumber() == null || registerStockMovementsDTO.getLotNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("O número do lote é obrigatório para reserva com lote.");
        }

        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);
        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockMovementsDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque com ID " + registerStockMovementsDTO.getLocationId() + " não encontrado."));

        ReturnStockLotDetailsDTO lotDetails = stockLotDetailsService.findByProductIdAndLotNumberAndLocationId(
                product.getId(), registerStockMovementsDTO.getLotNumber(), stockLocation.getId()
        );

        // Validar quantidade disponível para reserva (agora comparando com a quantity total do lote)
        if (registerStockMovementsDTO.getQuantity() > lotDetails.getQuantity()) {
            throw new IllegalStockMovementException("Não é possível reservar " + registerStockMovementsDTO.getQuantity() + " unidades do lote " + lotDetails.getLotNumber() + ". Disponível: " + lotDetails.getQuantity());
        }

        // Diminuir a quantidade disponível no lote para representar a reserva
        stockLotDetailsService.decreaseLotQuantity(lotDetails.getId(), registerStockMovementsDTO.getQuantity());

        StockBalanceEntity stockBalance = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId())
                .orElseThrow(() -> new StockBalanceNotFoundException("Saldo de estoque não encontrado para o produto " + product.getName() + " no estoque " + stockLocation.getLocationName() + "."));
        stockBalance.setQuantityReserved(stockBalance.getQuantityReserved() + registerStockMovementsDTO.getQuantity());
        this.balanceRepository.save(stockBalance);

        if (registerStockMovementsDTO.getQuantity() > (stockBalance.getQuantityOnHand() - stockBalance.getQuantityReserved())) {
            throw new IllegalStockMovementException("Não é possível reservar essa quantidade. Saldo disponível insuficiente para o produto " + product.getName() + " no estoque " + stockLocation.getLocationName() + ".");
        }

        if(registerStockMovementsDTO.getRemarks() == null || registerStockMovementsDTO.getRemarks().isEmpty()) throw new IllegalStockMovementException("Para fazer um reajuste de estoque, é necessário preencher o campo de observação!");

        stockMovements.setMovementType(MovementType.RESERVA);
        stockMovements.setProduct(product);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setStockLocation(stockLocation);
        stockMovements.setLotNumber(registerStockMovementsDTO.getLotNumber());
        stockMovements.setRemarks(registerStockMovementsDTO.getRemarks()); // Pode haver observações na reserva

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public StockMovementDTO stockReservationOutputRegister(RegisterStockMovementsDTO registerStockMovementsDTO){
        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);

        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId()).orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + registerStockMovementsDTO.getProductId() + " não foi encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId()).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + registerStockMovementsDTO.getLocationId() + " não foi encontrado."));

        // Verificando de registro em stock balance para salvar a saída de estoque de um produto
        StockBalanceEntity stockBalance = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId())
                .orElseThrow(() -> new StockBalanceNotFoundException("Não foi encontrado um saldo de estoque para o produto " + product.getSkuCode() + " no estoque " + stockLocation.getLocationName() + "."));


        if(registerStockMovementsDTO.getQuantity() > stockBalance.getQuantityReserved()) throw new IllegalStockMovementException("Não é possível realizar essa retirada de estoque por não existir uma quantidade reservada.");
        stockBalance.setQuantityOnHand(stockBalance.getQuantityOnHand() - registerStockMovementsDTO.getQuantity());
        stockBalance.setQuantityReserved(stockBalance.getQuantityReserved() - registerStockMovementsDTO.getQuantity());
        this.balanceRepository.save(stockBalance);

        stockMovements.setMovementType(MovementType.SAIDA);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setProduct(product);
        stockMovements.setStockLocation(stockLocation);

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    @Transactional
    public StockMovementDTO stockReservationOutputRegisterWithLot(RegisterStockMovementsDTO registerStockMovementsDTO){
        if (registerStockMovementsDTO.getLotNumber() == null || registerStockMovementsDTO.getLotNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("O número do lote é obrigatório para saída com lote.");
        }

        StockMovementsEntity stockMovements = StockMovementsMapper.toCreateEntity(registerStockMovementsDTO);
        ProductEntity product = this.productRepository.findById(registerStockMovementsDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com ID " + registerStockMovementsDTO.getProductId() + " não encontrado."));
        StockLocationEntity stockLocation = this.locationRepository.findById(registerStockMovementsDTO.getLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("Estoque com ID " + registerStockMovementsDTO.getLocationId() + " não encontrado."));

        ReturnStockLotDetailsDTO lotDetails = stockLotDetailsService.findByProductIdAndLotNumberAndLocationId(
                product.getId(), registerStockMovementsDTO.getLotNumber(), stockLocation.getId()
        );

        stockLotDetailsService.validateLotQuantityForOutbound(lotDetails, registerStockMovementsDTO.getQuantity());
        stockLotDetailsService.validateLotExpirationDate(lotDetails);
        stockLotDetailsService.decreaseLotQuantity(lotDetails.getId(), registerStockMovementsDTO.getQuantity());

        StockBalanceEntity stockBalance = this.balanceRepository.findByProduct_IdAndStockLocation_Id(product.getId(), stockLocation.getId())
                .orElseThrow(() -> new StockBalanceNotFoundException("Saldo de estoque não encontrado para o produto " + product.getName() + " no estoque " + stockLocation.getLocationName() + "."));
        stockBalance.setQuantityOnHand(stockBalance.getQuantityOnHand() - registerStockMovementsDTO.getQuantity());
        this.balanceRepository.save(stockBalance);

        if(registerStockMovementsDTO.getQuantity() > stockBalance.getQuantityReserved()) throw new IllegalStockMovementException("Não é possível realizar essa retirada de estoque por não existir uma quantidade reservada.");
        stockBalance.setQuantityReserved(stockBalance.getQuantityReserved() - registerStockMovementsDTO.getQuantity());

        stockMovements.setMovementType(MovementType.SAIDA);
        stockMovements.setProduct(product);
        stockMovements.setMovementDate(LocalDateTime.now());
        stockMovements.setStockLocation(stockLocation);
        stockMovements.setLotNumber(registerStockMovementsDTO.getLotNumber());

        return StockMovementsMapper.toDTO(this.stockMovementRepository.save(stockMovements));
    }

    public Page<StockMovementDTO> findAllStockMovements(ListStockMovementDTO listStockMovementDTO){
        Pageable pageable = PageRequest.of(listStockMovementDTO.getPage(), listStockMovementDTO.getSize());
        return this.stockMovementRepository.findAll(pageable).map(StockMovementsMapper::toDTO);
    }

    public Page<StockMovementDTO> findAllByProductIdOrderByMovementDateDesc(Long productId, ListStockMovementDTO listStockMovementDTO){
        Pageable pageable = PageRequest.of(listStockMovementDTO.getPage(), listStockMovementDTO.getSize());
        return this.stockMovementRepository.findAllByProduct_IdOrderByMovementDateDesc(productId, pageable).map(StockMovementsMapper::toDTO);
    }

    public Page<StockMovementDTO> findAllByLocationIdOrderByMovementDateDesc(Long locationId, ListStockMovementDTO listStockMovementDTO){
        Pageable pageable = PageRequest.of(listStockMovementDTO.getPage(), listStockMovementDTO.getSize());
        return this.stockMovementRepository.findAllByStockLocation_IdOrderByMovementDateDesc(locationId, pageable).map(StockMovementsMapper::toDTO);
    }

    public Page<StockMovementDTO> findAllByProductIdAndLocationIdOrderByMovementDateDesc(Long productId, Long locationId, ListStockMovementDTO listStockMovementDTO){
        Pageable pageable = PageRequest.of(listStockMovementDTO.getPage(), listStockMovementDTO.getSize());
        return this.stockMovementRepository.findAllByProduct_IdAndStockLocation_IdOrderByMovementDateDesc(productId, locationId, pageable).map(StockMovementsMapper::toDTO);
    }

    public Page<StockMovementDTO> findAllByMovementTypeAndMovementDateBetween(MovementType movementType, LocalDateTime startDate, LocalDateTime endDate, ListStockMovementDTO listStockMovementDTO){
        Pageable pageable = PageRequest.of(listStockMovementDTO.getPage(), listStockMovementDTO.getSize());
        return this.stockMovementRepository.findAllByMovementTypeAndMovementDateBetween(movementType, startDate, endDate, pageable).map(StockMovementsMapper::toDTO);
    }

}
