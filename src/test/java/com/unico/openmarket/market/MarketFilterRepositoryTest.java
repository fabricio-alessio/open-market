package com.unico.openmarket.market;

import static org.junit.Assert.assertTrue;

import com.unico.openmarket.OpenMarketApplication;
import com.unico.openmarket.district.DistrictRepository;
import com.unico.openmarket.subcityhall.SubCityHallRepository;
import com.unico.openmarket.util.EntityHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpenMarketApplication.class)
public class MarketFilterRepositoryTest {

    @Autowired
    private MarketFilterRepository filterRepository;

    @Autowired
    private MarketRepository crudRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private SubCityHallRepository subCityHallRepository;

    @Test
    public void should() {

        int districtCode = 2;
        int subCityHallCode = 3;
        long marketCode = 2;
        String marketName = "Feira da Vila";
        String region5 = "Leste";
        String neighborhood = "Vila Mariana";

        final var district = districtRepository.save(EntityHelper.createNewDistrict(districtCode));
        final var subCityHall = subCityHallRepository.save(EntityHelper.createNewSubCityHall(subCityHallCode));

        final var market = EntityHelper.createNewMarket(marketCode,
                Map.of(EntityHelper.FIELD_DISTRICT_ID, "" + district.getId(),
                        EntityHelper.FIELD_SUB_CITY_HALL_ID, "" + subCityHall.getId(),
                        EntityHelper.FIELD_NAME, marketName,
                        EntityHelper.FIELD_NEIGHBORHOOD, neighborhood,
                        EntityHelper.FIELD_REGION5, region5));

        crudRepository.save(market);
        final var markets = filterRepository.findByFilters(
                Optional.of(district.getId()),
                Optional.of(region5),
                Optional.of(marketName),
                Optional.of(neighborhood));

        assertTrue(markets.size() == 1);
    }
}