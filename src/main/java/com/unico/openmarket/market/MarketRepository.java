package com.unico.openmarket.market;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketRepository extends CrudRepository<Market, Long> {

    Optional<Market> findByCode(long code);
}