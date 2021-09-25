package com.unico.openmarket.subcityhall;

import com.unico.openmarket.district.District;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCityHallRepository extends CrudRepository<SubCityHall, Integer> {

    Optional<SubCityHall> findByCode(long code);
}
