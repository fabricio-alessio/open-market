package com.unico.openmarket.district;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistrictService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistrictService.class);

    private DistrictRepository repository;

    public DistrictService(DistrictRepository repository) {
        this.repository = repository;
    }

    public District createNewDistrictIfDoesNotExists(DistrictDto districtDto) {

        LOGGER.debug("Create new district if not exists {}", districtDto);
        return repository.findByCode(districtDto.getCode())
                .orElseGet(() -> repository.save(DistrictAdapter.adaptDistrict(districtDto)));
    }

    public Optional<DistrictDto> findById(int id) {

        return repository.findById(id)
                .map(DistrictAdapter::adaptDistrictDto);
    }
}
