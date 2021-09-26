package com.unico.openmarket.market;

import com.unico.openmarket.commons.EmptyQueryParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketFilterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Market> findByFilters(Optional<Integer> districtIdOpt, Optional<String> region5,
                                      Optional<String> name, Optional<String> neighborhood) {

        if (districtIdOpt.isEmpty() &&
            region5.isEmpty() &&
            name.isEmpty() &&
            neighborhood.isEmpty()) {

            throw new EmptyQueryParamException("No filter district_id, region5, name or neighborhood was informed");
        }

        return jdbcTemplate.query(
                createFilterQuery(districtIdOpt, region5, name, neighborhood),
                (rs, rowNum) -> buildMarket(rs),
                createFilterValues(districtIdOpt, region5, name, neighborhood)
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

    private String createFilterQuery(Optional<Integer> districtIdOpt, Optional<String> region5,
                                     Optional<String> name, Optional<String> neighborhood) {

        return "select * from market m \n" +
                "where\n" +
                "  1 = 1\n" +
                "  and m.district_id = ?\n" +
                "  and m.region5 = ?\n" +
                "  and m.market_name like ?\n" +
                "  and m.neighborhood like ?";
    }

    private Object[] createFilterValues(Optional<Integer> districtIdOpt, Optional<String> region5,
                                        Optional<String> name, Optional<String> neighborhood) {

        return new Object[]{districtIdOpt.get(), region5.get(), "%" + name.get() + "%", "%" + neighborhood.get() + "%"};
    }
}
