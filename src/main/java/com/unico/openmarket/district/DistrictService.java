package com.unico.openmarket.district;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.sql.In;
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

    public Optional<District> findDistrictByName(String name) {

        LOGGER.debug("Find by name {}", name);
        return repository.findByName(name);
    }

    public Optional<Integer> findDistrictIdByName(String name) {

        LOGGER.debug("Find districtId by name {}", name);
        final var districtOpt = findDistrictByName(name);
        if (districtOpt.isPresent()) {
            return Optional.of(districtOpt.get().getId());
        } else {
            return Optional.empty();
        }
    }
}
