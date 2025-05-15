package com.gruposv.microservice_purchasing.modules.stock.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gruposv.microservice_purchasing.modules.stock.dto.stock_locations.ListStockLocationsDTO;
import com.gruposv.microservice_purchasing.modules.stock.dto.stock_locations.StockLocationDTO;
import com.gruposv.microservice_purchasing.modules.stock.entity.StockLocationEntity;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationCodeDuplicateException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.mapper.stock_locations.StockLocationsMapper;
import com.gruposv.microservice_purchasing.modules.stock.repository.StockLocationRepository;

import java.time.LocalDateTime;

@Service
public class StockLocationService {

    private final StockLocationRepository stockLocationRepository;

    public StockLocationService(StockLocationRepository stockLocationRepository) {
        this.stockLocationRepository = stockLocationRepository;
    }

    @Transactional
    public StockLocationDTO createNewStockLocation(StockLocationDTO stockLocationDTO){
        if(this.stockLocationRepository.existsByLocationCode(stockLocationDTO.getLocationCode())) throw new StockLocationCodeDuplicateException("Esse código de local de estoque já existe.");
        if(this.stockLocationRepository.existsByLocationName(stockLocationDTO.getLocationName())) throw new StockLocationCodeDuplicateException("Nome do local de estoque já existe.");
        StockLocationEntity stockLocation = StockLocationsMapper.toEntity(stockLocationDTO);
        return StockLocationsMapper.toDTO(this.stockLocationRepository.save(stockLocation));
    }

    public Page<StockLocationDTO> findAllStockLocations(ListStockLocationsDTO listStockLocationsDTO){
        Pageable pageable = PageRequest.of(listStockLocationsDTO.getPage(), listStockLocationsDTO.getSize());
        return this.stockLocationRepository.findAll(pageable).map(StockLocationsMapper::toDTO);
    }

    public StockLocationDTO findByLocationCode(String locationCode){
        return StockLocationsMapper.toDTO(this.stockLocationRepository.findByLocationCode(locationCode).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o código " + locationCode + " não foi encontrado...")));
    }

    public StockLocationDTO findByLocationName(String locationName){
        return StockLocationsMapper.toDTO(this.stockLocationRepository.findByLocationNameContainingIgnoreCase(locationName).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o nome " + locationName + " não foi encontrado...")));
    }

    @Transactional
    public StockLocationDTO updateStockLocations(Long locationId, StockLocationDTO stockLocationDTO){

        StockLocationEntity stockLocation = this.stockLocationRepository.findById(locationId).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + locationId + " não foi encontrado..."));

        if(stockLocationDTO.getLocationCode() != null && !stockLocationDTO.getLocationCode().equalsIgnoreCase(stockLocation.getLocationCode())) {
            if(this.stockLocationRepository.existsByLocationCode(stockLocationDTO.getLocationCode())) throw new StockLocationCodeDuplicateException("Esse código de local de estoque já existe.");
        }

        if(stockLocationDTO.getLocationCode() != null && !stockLocationDTO.getLocationName().equalsIgnoreCase(stockLocation.getLocationName())) {
            if(this.stockLocationRepository.existsByLocationName(stockLocationDTO.getLocationName())) throw new StockLocationCodeDuplicateException("Nome do local de estoque já existe.");
        }

        StockLocationEntity stockLocationUpdated = StockLocationsMapper.toEntityUpdate(stockLocationDTO, stockLocation);

        return StockLocationsMapper.toDTO(this.stockLocationRepository.save(stockLocationUpdated));
    }

    public void deleteStockLocation(Long id){
        StockLocationEntity stockLocation = this.stockLocationRepository.findById(id).orElseThrow(() -> new StockLocationNotFoundException("O estoque com o ID " + id + " não foi encontrado..."));
        stockLocation.setDeletedAt(LocalDateTime.now());
        this.stockLocationRepository.save(stockLocation);
    }

}
