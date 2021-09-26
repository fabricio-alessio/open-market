package com.unico.openmarket.market;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketRepository extends CrudRepository<Market, Long> {

    Optional<Market> findByCode(long code);

    @Query("select * from market m \n" +
            "where\n" +
            "  1 = 1\n" +
            "  and m.district_id = :district_id\n" +
            "  and m.region5 = :region5\n" +
            "  and m.\"name\" like :name\n" +
            "  and m.neighborhood like :neighborhood")
    List<Market> findByFilters(@Param("district_id") Integer district_id,
                               @Param("region5") String region5,
                               @Param("name") String name,
                               @Param("neighborhood") String neighborhood);
}