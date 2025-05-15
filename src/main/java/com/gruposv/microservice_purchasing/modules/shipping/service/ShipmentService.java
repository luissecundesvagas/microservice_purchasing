package com.gruposv.microservice_purchasing.modules.shipping.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gruposv.microservice_purchasing.modules.product.entity.ProductEntity;
import com.gruposv.microservice_purchasing.modules.product.exception.IllegalStockMovementException;
import com.gruposv.microservice_purchasing.modules.product.exception.ProductNotFoundException;
import com.gruposv.microservice_purchasing.modules.product.repository.ProductRepository;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ListShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ReturnShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment.ShipmentDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment_item.ReturnShipmentItemDTO;
import com.gruposv.microservice_purchasing.modules.shipping.dto.shipment_item.ShipmentItemDTO;
import com.gruposv.microservice_purchasing.modules.shipping.entity.ShipmentEntity;
import com.gruposv.microservice_purchasing.modules.shipping.entity.ShipmentItemsEntity;
import com.gruposv.microservice_purchasing.modules.shipping.enums.ShipmentStatus;
import com.gruposv.microservice_purchasing.modules.shipping.exception.*;
import com.gruposv.microservice_purchasing.modules.shipping.mapper.ShipmentItemsMapper;
import com.gruposv.microservice_purchasing.modules.shipping.mapper.ShipmentMapper;
import com.gruposv.microservice_purchasing.modules.shipping.repository.ShipmentItemRepository;
import com.gruposv.microservice_purchasing.modules.shipping.repository.ShipmentRepository;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_movements.RegisterStockMovementsDTO;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockBalanceEntity;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockLocationEntity;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance.InsufficientStockException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance.StockBalanceNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockBalanceRepository;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockLocationRepository;
import com.gruposv.microservice_purchasing.modules.stock.service.StockMovementService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentItemRepository shipmentItemRepository;
    private final StockBalanceRepository stockBalanceRepository;
    private final ProductRepository productRepository;
    private final StockLocationRepository stockLocationRepository;
    private final StockMovementService stockMovementService;

    public ShipmentService(ShipmentRepository shipmentRepository, ShipmentItemRepository shipmentItemRepository, StockBalanceRepository stockBalanceRepository, ProductRepository productRepository, StockLocationRepository stockLocationRepository, StockMovementService stockMovementService) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentItemRepository = shipmentItemRepository;
        this.stockBalanceRepository = stockBalanceRepository;
        this.productRepository = productRepository;
        this.stockLocationRepository = stockLocationRepository;
        this.stockMovementService = stockMovementService;
    }

    @Transactional
    public ReturnShipmentDTO createNewShipment(ShipmentDTO shipmentDTO) {
        ShipmentEntity shipmentEntity = ShipmentMapper.toCreateEntity(shipmentDTO);
        shipmentEntity.setShipmentStatus(ShipmentStatus.EM_SEPARACAO);

        StockLocationEntity shipmentStockLocation = stockLocationRepository.findById(shipmentDTO.getStockLocationId())
                .orElseThrow(() -> new StockLocationNotFoundException("A localização do estoque com ID " + shipmentDTO.getStockLocationId() + " não foi encontrada."));

        List<ShipmentItemsEntity> shipmentItemsEntities = new ArrayList<>();

        for (ShipmentItemDTO itemDTO : shipmentDTO.getShipmentItems()) {
            ProductEntity product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + itemDTO.getProductId() + " não foi encontrado"));

            if (itemDTO.getPickedQuantity() > itemDTO.getQuantity()) {
                throw new InvalidPickedQuantityException("A quantidade separada (" + itemDTO.getPickedQuantity() + ") excede a quantidade pedida (" + itemDTO.getQuantity() + ")");
            }

            RegisterStockMovementsDTO stockMovementDTO = buildStockMovementDTO(
                    itemDTO.getProductId(),
                    shipmentDTO.getStockLocationId(),
                    itemDTO.getPickedQuantity() != null ? itemDTO.getPickedQuantity() : 0,
                    shipmentDTO.getSaleOrderId(),
                    "Reserva de estoque para o pedido de venda com o ID " + shipmentDTO.getSaleOrderId(),
                    itemDTO.getLotNumber()
            );
            registerReservation(stockMovementDTO);

            ShipmentItemsEntity shipmentItemEntity = ShipmentItemsMapper.toCreateEntity(itemDTO);
            shipmentItemEntity.setProduct(product);
            shipmentItemEntity.setStockLocation(shipmentStockLocation);
            shipmentItemEntity.setShipment(shipmentEntity);
            shipmentItemsEntities.add(shipmentItemEntity);
        }

        List<ShipmentItemsEntity> savedItems = shipmentItemRepository.saveAll(shipmentItemsEntities);
        shipmentEntity.setShipmentItemsList(savedItems);

        return ShipmentMapper.toDTO(shipmentRepository.save(shipmentEntity));
    }

    @Transactional
    public ReturnShipmentItemDTO updatePickedQuantity(Long shipmentItemId, ShipmentItemDTO itemDTO) {
        ShipmentItemsEntity shipmentItem = shipmentItemRepository.findById(shipmentItemId)
                .orElseThrow(() -> new ShipmentItemNotFoundException("Item de expedição com ID " + shipmentItemId + " não encontrado."));

        if (itemDTO.getPickedQuantity() > shipmentItem.getQuantity()) {
            throw new InvalidPickedQuantityException("A quantidade separada (" + itemDTO.getPickedQuantity() + ") excede a quantidade pedida (" + shipmentItem.getQuantity() + ")");
        }

        RegisterStockMovementsDTO stockMovementDTO = buildStockMovementDTO(
                itemDTO.getProductId(),
                shipmentItem.getStockLocation().getId(),
                itemDTO.getPickedQuantity(),
                shipmentItem.getShipment().getSaleOrderId(),
                "Reserva de estoque para o pedido de venda com o ID " + shipmentItem.getShipment().getSaleOrderId(),
                itemDTO.getLotNumber()
        );
        registerReservation(stockMovementDTO);

        Integer existingPicked = shipmentItem.getPickedQuantity() != null ? shipmentItem.getPickedQuantity() : 0;
        shipmentItem.setPickedQuantity(itemDTO.getPickedQuantity() + existingPicked);

        return ShipmentItemsMapper.toDTO(shipmentItemRepository.save(shipmentItem));
    }

    @Transactional
    public ReturnShipmentDTO dispatchShipment(Long shipmentId, String lotNumber) {
        ShipmentEntity shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException("Expedição com ID " + shipmentId + " não encontrada."));

        if (shipment.getShipmentStatus() == ShipmentStatus.DESPACHADO || shipment.getShipmentStatus() == ShipmentStatus.ENTREGUE) {
            throw new InvalidShipmentStatusException("A expedição com ID " + shipmentId + " já foi despachada ou entregue.");
        }

        for (ShipmentItemsEntity item : shipment.getShipmentItemsList()) {
            if (item.getPickedQuantity() == null || item.getPickedQuantity() < item.getQuantity()) {
                throw new ItemsNotFullyPickedException("Item com ID " + item.getId() + " não foi totalmente separado.");
            }

            RegisterStockMovementsDTO stockMovementDTO = buildStockMovementDTO(
                    item.getProduct().getId(),
                    item.getStockLocation().getId(),
                    item.getPickedQuantity(),
                    shipment.getSaleOrderId(),
                    "Saída de estoque para o pedido de venda com o ID " + shipment.getSaleOrderId(),
                    lotNumber
            );
            registerDispatch(stockMovementDTO);
        }

        shipment.setShipmentStatus(ShipmentStatus.DESPACHADO);
        return ShipmentMapper.toDTO(shipmentRepository.save(shipment));
    }

    @Transactional
    public ReturnShipmentDTO concludeShipment(Long shipmentId) {
        ShipmentEntity shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException("Expedição com ID " + shipmentId + " não encontrada."));

        if (ShipmentStatus.ENTREGUE.equals(shipment.getShipmentStatus())) {
            return ShipmentMapper.toDTO(shipment);
        }

        shipment.setShipmentStatus(ShipmentStatus.ENTREGUE);
        return ShipmentMapper.toDTO(shipmentRepository.save(shipment));
    }

    public Page<ReturnShipmentDTO> findAllShipments(ListShipmentDTO listShipmentDTO){
        Pageable pageable = PageRequest.of(listShipmentDTO.getPage(), listShipmentDTO.getSize());
        return this.shipmentRepository.findAll(pageable).map(ShipmentMapper::toDTO);
    }

    public Page<ReturnShipmentDTO> findAllByShipmentStatus(ShipmentStatus shipmentStatus, ListShipmentDTO listShipmentDTO){
        Pageable pageable = PageRequest.of(listShipmentDTO.getPage(), listShipmentDTO.getSize());
        return this.shipmentRepository.findAllByShipmentStatus(shipmentStatus, pageable).map(ShipmentMapper::toDTO);
    }

    public Page<ReturnShipmentDTO> findAllByShipmentDateBetween(LocalDateTime startDate, LocalDateTime endDate, ListShipmentDTO listShipmentDTO){
        Pageable pageable = PageRequest.of(listShipmentDTO.getPage(), listShipmentDTO.getSize());
        return this.shipmentRepository.findAllByShipmentDateBetween(startDate, endDate, pageable).map(ShipmentMapper::toDTO);
    }

    public ReturnShipmentDTO findShipmentById(Long id){
        return ShipmentMapper.toDTO(this.shipmentRepository.findById(id).orElseThrow(() -> new ShipmentNotFoundException("A expedição com o ID " + id + " não foi encontrada.")));
    }

    public ReturnShipmentDTO findBySaleOrderId(Long saleOrderId){
        return ShipmentMapper.toDTO(this.shipmentRepository.findBySaleOrderId(saleOrderId).orElseThrow(() -> new ShipmentNotFoundException("A expedição com o ID do pedido de venda " + saleOrderId + " não foi encontrada.")));
    }

    public ReturnShipmentDTO findByTrackingNumber(String trackingNumber){
        return ShipmentMapper.toDTO(this.shipmentRepository.findByTrackingNumber(trackingNumber).orElseThrow(() -> new ShipmentNotFoundException("A expedição com o ID do pedido de venda " + trackingNumber + " não foi encontrada.")));
    }

    @Transactional
    public ReturnShipmentDTO updateShipment(Long id, ShipmentDTO shipmentDTO){

        ShipmentEntity shipmentEntity = this.shipmentRepository.findById(id).orElseThrow(() -> new ShipmentNotFoundException("A expedição com o ID " + id + " não foi encontrada."));

        shipmentEntity = ShipmentMapper.toUpdateEntity(shipmentDTO, shipmentEntity);

        shipmentEntity.setShipmentItemsList(shipmentDTO.getShipmentItems()
                .stream()
                .map(shipmentItemDTO -> {
                    ShipmentItemsEntity shipmentItemsEntity = ShipmentItemsMapper.toUpdateEntity(shipmentItemDTO);
                    shipmentItemsEntity.setProduct(this.productRepository
                            .findById(shipmentItemDTO.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException("O produto com o ID " + shipmentItemDTO.getProductId() + " não foi encontrado")));
                    return shipmentItemsEntity;
                })
                .toList());

        return ShipmentMapper.toDTO(this.shipmentRepository.save(shipmentEntity));
    }

    public void delete(Long id){
        ShipmentEntity shipmentEntity = this.shipmentRepository.findById(id).orElseThrow(() -> new ShipmentNotFoundException("A expedição com o ID " + id + " não foi encontrada."));
        shipmentEntity.setDeletedAt(LocalDateTime.now());
        this.shipmentRepository.save(shipmentEntity);
    }

    private RegisterStockMovementsDTO buildStockMovementDTO(Long productId, Long locationId, Integer quantity, Long referenceId, String remarks, String lotNumber) {
        RegisterStockMovementsDTO dto = new RegisterStockMovementsDTO();
        dto.setProductId(productId);
        dto.setLocationId(locationId);
        dto.setQuantity(quantity);
        dto.setReferenceId(referenceId);
        dto.setRemarks(remarks);
        if (lotNumber != null && !lotNumber.isBlank()) {
            dto.setLotNumber(lotNumber);
        }
        return dto;
    }

    private void registerReservation(RegisterStockMovementsDTO dto) {
        if (dto.getLotNumber() == null || dto.getLotNumber().isBlank()) {
            stockMovementService.registerStockReservation(dto);
        } else {
            stockMovementService.registerStockReservationWithLot(dto);
        }
    }

    private void registerDispatch(RegisterStockMovementsDTO dto) {
        if (dto.getLotNumber() == null || dto.getLotNumber().isBlank()) {
            stockMovementService.stockReservationOutputRegister(dto);
        } else {
            stockMovementService.stockReservationOutputRegisterWithLot(dto);
        }
    }

}
