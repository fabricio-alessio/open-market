package com.unico.openmarket.district;

import com.unico.openmarket.market.Market;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends CrudRepository<District, Integer> {

    Optional<District> findByCode(long code);

    Optional<District> findByName(String name);
}