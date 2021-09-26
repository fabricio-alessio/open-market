package com.unico.openmarket.market;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.unico.openmarket.OpenMarketApplication;
import com.unico.openmarket.commons.EmptyQueryParamException;
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

    @Test(expected = EmptyQueryParamException.class)
    public void shouldThrowAnExceptionWhenTryToFilterWithOnlyEmptyParameters() {

        filterRepository.findByFilters(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());
    }

    @Test
    public void shouldCreateAndRetrieveAMarketUsingAllFilters() {

        final var district = districtRepository.save(EntityHelper.createNewDistrict(2));
        final var subCityHall = subCityHallRepository.save(EntityHelper.createNewSubCityHall(3));
        createAndSaveMarket(1, district.getId(), subCityHall.getId(), "Feira da Vila", "Vila Mariana", "Leste");

        final var markets = filterRepository.findByFilters(
                Optional.of(district.getId()),
                Optional.of("Leste"),
                Optional.of("Vila"),
                Optional.of("Mariana"));

        assertTrue(markets.size() == 1);
        var foundMarket = markets.get(0);
        assertEquals("Feira da Vila", foundMarket.getMarketName());
    }

    @Test
    public void shouldCreateAndRetrieveMarketsWithNameLikeVilaUsingOneFilter() {

        final var district = districtRepository.save(EntityHelper.createNewDistrict(2));
        final var subCityHall = subCityHallRepository.save(EntityHelper.createNewSubCityHall(3));

        createAndSaveMarket(1, district.getId(), subCityHall.getId(), "Feira da Vila", "Vila Mariana", "Leste");
        createAndSaveMarket(2, district.getId(), subCityHall.getId(), "Vila do Pastel", "Jabaquara", "Sul");
        createAndSaveMarket(3, district.getId(), subCityHall.getId(), "Feira de Santana", "Jabaquara", "Sul");

        final var markets = filterRepository.findByFilters(
                Optional.empty(),
                Optional.empty(),
                Optional.of("Vila"),
                Optional.empty());

        assertTrue(markets.size() == 2);
    }

    private Market createAndSaveMarket(long marketCode, int districtId, int subCityHallId, String marketName, String neighborhood, String region5) {

        final var market = EntityHelper.createNewMarket(marketCode,
                Map.of(EntityHelper.FIELD_DISTRICT_ID, "" + districtId,
                        EntityHelper.FIELD_SUB_CITY_HALL_ID, "" + subCityHallId,
                        EntityHelper.FIELD_NAME, marketName,
                        EntityHelper.FIELD_NEIGHBORHOOD, neighborhood,
                        EntityHelper.FIELD_REGION5, region5));

        return crudRepository.save(market);
    }
}