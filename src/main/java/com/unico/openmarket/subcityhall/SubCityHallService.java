package com.unico.openmarket.subcityhall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubCityHallService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubCityHallService.class);

    private SubCityHallRepository repository;

    public SubCityHallService(SubCityHallRepository repository) {
        this.repository = repository;
    }

    public SubCityHall createNewSubCityHallIfDoesNotExists(SubCityHallDto subCityHallDto) {

        LOGGER.debug("Create new SubCityHall if not exists {}", subCityHallDto);
        return repository.findByCode(subCityHallDto.getCode())
                .orElseGet(() -> repository.save(SubCityHallAdapter.adaptSubCityHall(subCityHallDto)));
    }

    public Optional<SubCityHallDto> findById(int id) {

        return repository.findById(id)
                .map(SubCityHallAdapter::adaptSubCityHallDto);
    }
}
