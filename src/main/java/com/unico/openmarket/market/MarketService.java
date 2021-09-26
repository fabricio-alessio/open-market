package com.unico.openmarket.market;

import com.unico.openmarket.commons.EntityNotFoundException;
import com.unico.openmarket.district.DistrictAdapter;
import com.unico.openmarket.district.DistrictService;
import com.unico.openmarket.subcityhall.SubCityHallAdapter;
import com.unico.openmarket.subcityhall.SubCityHallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketService.class);

    private MarketRepository repository;
    private MarketFilterRepository filterRepository;
    private DistrictService districtService;
    private SubCityHallService subCityHallService;

    public MarketService(MarketRepository repository, MarketFilterRepository filterRepository,
                         DistrictService districtService, SubCityHallService subCityHallService) {
        this.repository = repository;
        this.filterRepository = filterRepository;
        this.districtService = districtService;
        this.subCityHallService = subCityHallService;
    }

    public Optional<MarketDto> create(MarketDto marketDto) {

        LOGGER.debug("Try to create new market {}", marketDto);
        return repository.findByCode(marketDto.getCode())
                .map(market -> update(marketDto.getCode(), marketDto)
                ).orElseGet(() -> createNew(marketDto));
    }

    private Optional<MarketDto> createNew(MarketDto marketDto) {

        LOGGER.debug("Create new market {}", marketDto);
        final var district = districtService.createNewDistrictIfDoesNotExists(marketDto.getDistrict());
        final var subCityHall = subCityHallService.createNewSubCityHallIfDoesNotExists(marketDto.getSubCityHall());

        final var market = repository.save(MarketAdapter.adaptMarket(marketDto, district, subCityHall));
        final var districtDto = DistrictAdapter.adaptDistrictDto(district);
        final var subCityHallDto = SubCityHallAdapter.adaptSubCityHallDto(subCityHall);
        return Optional.of(MarketAdapter.adaptMarketDto(market, districtDto, subCityHallDto));
    }

    public List<MarketDto> findByFilters(int districtId, String region5, String name, String neighborhood) {

        LOGGER.debug("Find markets by filters: districtId [{}], region5 [{}], name[{}], neighborhood [{}]",
                districtId, region5, name, neighborhood);
        final var markets = filterRepository.findByFilters(
                Optional.of(districtId),
                Optional.of(region5),
                Optional.of(name),
                Optional.of(neighborhood));
     /*   final var markets = filterRepository.findByFilters(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());*/

        return markets.stream().map(this::generateMarketDto).collect(Collectors.toList());
    }

    private String like(String value) {

        return "%" + value + "%";
    }

    private MarketDto generateMarketDto(Market market) {

        LOGGER.debug("Generate a marketDto from a market {}", market);
        final var districtDtoOpt = districtService.findById(market.getDistrictId());
        if (districtDtoOpt.isEmpty()) {
            throw new EntityNotFoundException("District id " + market.getDistrictId() + " not found!");
        }
        final var subCityHallDtoOpt = subCityHallService.findById(market.getSubCityHallId());
        if (subCityHallDtoOpt.isEmpty()) {
            throw new EntityNotFoundException("Sub City Hall id " + market.getSubCityHallId() + " not found!");
        }

        return MarketAdapter.adaptMarketDto(market, districtDtoOpt.get(), subCityHallDtoOpt.get());
    }

    public Optional<MarketDto> findByCode(long code) {

        LOGGER.debug("Find market by code {}", code);
        return repository.findByCode(code).map(this::generateMarketDto);
    }

    public Optional<MarketDto> update(long code, MarketDto marketDto) {

        LOGGER.debug("Update market code {} - {}", code, marketDto);
        return repository.findByCode(code)
                .map(market -> {
                    final var district = districtService.createNewDistrictIfDoesNotExists(marketDto.getDistrict());
                    final var subCityHall = subCityHallService.createNewSubCityHallIfDoesNotExists(marketDto.getSubCityHall());

                    market.setLat(marketDto.getLat());
                    market.setLng(marketDto.getLng());
                    market.setAreap(marketDto.getAreap());
                    market.setDistrictId(district.getId());
                    market.setSubCityHallId(subCityHall.getId());
                    market.setMarketName(marketDto.getName());
                    market.setNeighborhood(marketDto.getNeighborhood());
                    market.setNumber(marketDto.getNumber());
                    market.setReference(marketDto.getReference());
                    market.setRegion5(marketDto.getRegion5());
                    market.setRegion8(marketDto.getRegion8());
                    market.setRegistry(marketDto.getRegistry());
                    market.setSetcens(marketDto.getSetcens());
                    market.setStreet(marketDto.getStreet());
                    repository.save(market);

                    return findByCode(code);
                }).orElse(Optional.empty());
    }

    public boolean deleteByCode(long code) {

        LOGGER.debug("Delete market code {}", code);
        return repository.findByCode(code)
                .map(market -> {
                    repository.deleteById(market.getId());
                    return true;
                }).orElse(false);
    }
}
