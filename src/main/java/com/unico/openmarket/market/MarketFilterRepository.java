package com.unico.openmarket.market;

import com.unico.openmarket.commons.EmptyQueryParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketFilterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketFilterRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Market> findByFilters(Optional<Integer> districtIdOpt, Optional<String> region5Opt,
                                      Optional<String> nameOpt, Optional<String> neighborhoodOpt) {

        LOGGER.debug("Find by filters: districtId [{}], region5 [{}], name[{}], neighborhood [{}]",
                districtIdOpt, region5Opt, nameOpt, neighborhoodOpt);

        if (districtIdOpt.isEmpty() &&
                region5Opt.isEmpty() &&
                nameOpt.isEmpty() &&
                neighborhoodOpt.isEmpty()) {
            return Collections.emptyList();
        }

        return jdbcTemplate.query(
                createFilterQuery(districtIdOpt, region5Opt, nameOpt, neighborhoodOpt),
                (rs, rowNum) -> buildMarket(rs),
                createFilterValues(districtIdOpt, region5Opt, nameOpt, neighborhoodOpt)
        );
    }

    private Market buildMarket(ResultSet rs) throws SQLException {

        return Market.builder()
                .code(rs.getLong("code"))
                .areap(rs.getLong("areap"))
                .lat(rs.getLong("lat"))
                .lng(rs.getLong("lng"))
                .marketName(rs.getString("market_name"))
                .neighborhood(rs.getString("neighborhood"))
                .street(rs.getString("street"))
                .number(rs.getString("number"))
                .reference(rs.getString("reference"))
                .region5(rs.getString("region5"))
                .region8(rs.getString("region8"))
                .registry(rs.getString("registry"))
                .setcens(rs.getLong("setcens"))
                .districtId(rs.getInt("district_id"))
                .subCityHallId(rs.getInt("sub_city_hall_id"))
                .build();
    }

    private String createFilterQuery(Optional<Integer> districtIdOpt, Optional<String> region5Opt,
                                     Optional<String> nameOpt, Optional<String> neighborhoodOpt) {

        String sql = "select * from market m \n" +
                "where\n" +
                "  1 = 1\n";

        if (districtIdOpt.isPresent()) {
            sql += "  and m.district_id = ?\n";
        }
        if (region5Opt.isPresent()) {
            sql += "  and m.region5 = ?\n";
        }
        if (nameOpt.isPresent()) {
            sql += "  and m.market_name like ?\n";
        }
        if (neighborhoodOpt.isPresent()) {
            sql += "  and m.neighborhood like ?";
        }
        return sql;
    }

    private Object[] createFilterValues(Optional<Integer> districtIdOpt, Optional<String> region5Opt,
                                        Optional<String> nameOpt, Optional<String> neighborhoodOpt) {

        final var objs = new ArrayList<>();

        districtIdOpt.ifPresent(objs::add);
        region5Opt.ifPresent(objs::add);
        nameOpt.ifPresent(val -> objs.add("%" + val + "%"));
        neighborhoodOpt.ifPresent(val -> objs.add("%" + val + "%"));

        return objs.toArray();
    }
}
