package com.unico.openmarket.market;

import com.unico.openmarket.commons.EntityNotFoundException;
import com.unico.openmarket.district.District;
import com.unico.openmarket.district.DistrictAdapter;
import com.unico.openmarket.district.DistrictDto;
import com.unico.openmarket.district.DistrictRepository;
import com.unico.openmarket.subcityhall.SubCityHall;
import com.unico.openmarket.subcityhall.SubCityHallAdapter;
import com.unico.openmarket.subcityhall.SubCityHallDto;
import com.unico.openmarket.subcityhall.SubCityHallRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MarketService {

    private MarketRepository repository;
    private DistrictRepository districtRepository;
    private SubCityHallRepository subCityHallRepository;

    public MarketService(MarketRepository repository, DistrictRepository districtRepository, SubCityHallRepository subCityHallRepository) {
        this.repository = repository;
        this.districtRepository = districtRepository;
        this.subCityHallRepository = subCityHallRepository;
    }

    public Optional<MarketDto> create(MarketDto marketDto) {

        return repository.findByCode(marketDto.getCode())
                .map(foundMarket -> {
                    return update(marketDto.getCode(), marketDto);
                }).orElseGet(() -> createNew(marketDto));
    }

    private Optional<MarketDto> createNew(MarketDto marketDto) {

        final var district = createNewDistrictIfDoesNotExists(marketDto.getDistrict());
        final var subCityHall = createNewSubCityHallIfDoesNotExists(marketDto.getSubCityHall());

        final var market = repository.save(MarketAdapter.adaptMarket(marketDto, district, subCityHall));
        final var districtDto = DistrictAdapter.adaptDistrictDto(district);
        final var subCityHallDto = SubCityHallAdapter.adaptSubCityHallDto(subCityHall);
        return Optional.of(MarketAdapter.adaptMarketDto(market, districtDto, subCityHallDto));
    }

    private District createNewDistrictIfDoesNotExists(DistrictDto districtDto) {

        return districtRepository.findByCode(districtDto.getCode())
                .orElseGet(() -> districtRepository.save(DistrictAdapter.adaptDistrict(districtDto)));
    }

    private SubCityHall createNewSubCityHallIfDoesNotExists(SubCityHallDto subCityHallDto) {

        return subCityHallRepository.findByCode(subCityHallDto.getCode())
                .orElseGet(() -> subCityHallRepository.save(SubCityHallAdapter.adaptSubCityHall(subCityHallDto)));
    }

    public List findAll() {

        return Collections.emptyList();
    }

    private MarketDto generateMarket(Market market) {

        final var districtOpt = districtRepository.findById(market.getDistrictId());
        if (!districtOpt.isPresent()) {
            throw new EntityNotFoundException("District id " + market.getDistrictId() + " not found!");
        }
        final var subCityHallOpt = subCityHallRepository.findById(market.getSubCityHallId());
        if (!subCityHallOpt.isPresent()) {
            throw new EntityNotFoundException("Sub City Hall id " + market.getSubCityHallId() + " not found!");
        }

        final var districtDto = DistrictAdapter.adaptDistrictDto(districtOpt.get());
        final var subCityHallDto = SubCityHallAdapter.adaptSubCityHallDto(subCityHallOpt.get());
        return MarketAdapter.adaptMarketDto(market, districtDto, subCityHallDto);
    }

    public Optional<MarketDto> findByCode(long code) {

        return repository.findByCode(code)
                .map(record -> {
                    return Optional.of(generateMarket(record));
                }).orElse(Optional.empty());
    }

    public Optional<MarketDto> update(long code, MarketDto marketDto) {

        return repository.findByCode(code)
                .map(record -> {
                    final var district = createNewDistrictIfDoesNotExists(marketDto.getDistrict());
                    final var subCityHall = createNewSubCityHallIfDoesNotExists(marketDto.getSubCityHall());

                    record.setLat(marketDto.getLat());
                    record.setLng(marketDto.getLng());
                    record.setAreap(marketDto.getAreap());
                    record.setDistrictId(district.getId());
                    record.setSubCityHallId(subCityHall.getId());
                    record.setName(marketDto.getName());
                    record.setNeighborhood(marketDto.getNeighborhood());
                    record.setNumber(marketDto.getNumber());
                    record.setReference(marketDto.getReference());
                    record.setRegion5(marketDto.getRegion5());
                    record.setRegion8(marketDto.getRegion8());
                    record.setRegistry(marketDto.getRegistry());
                    record.setSetcens(marketDto.getSetcens());
                    record.setStreet(marketDto.getStreet());
                    repository.save(record);

                    return findByCode(code);
                }).orElse(Optional.empty());
    }

    public boolean deleteByCode(long code) {

        return repository.findByCode(code)
                .map(record -> {
                    repository.deleteById(record.getId());
                    return true;
                }).orElse(false);
    }
}
