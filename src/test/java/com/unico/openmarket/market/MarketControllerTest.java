package com.unico.openmarket.market;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.unico.openmarket.OpenMarketApplication;
import com.unico.openmarket.district.DistrictRepository;
import com.unico.openmarket.subcityhall.SubCityHallRepository;
import com.unico.openmarket.util.EntityHelper;
import com.unico.openmarket.util.JsonHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpenMarketApplication.class)
@AutoConfigureMockMvc
class MarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketRepository repository;

    @MockBean
    private DistrictRepository districtRepository;

    @MockBean
    private SubCityHallRepository subCityHallRepository;

    @Test
    public void shouldCreateAMarketSuccessfully() throws Exception {

        long marketCode = 1;
        int districtCode = 2;
        int subCityHallCode = 3;

        when(repository.findByCode(marketCode)).thenReturn(Optional.empty());
        when(districtRepository.findByCode(districtCode)).thenReturn(Optional.empty());
        when(subCityHallRepository.findByCode(subCityHallCode)).thenReturn(Optional.empty());

        when(repository.save(Mockito.any())).thenReturn(EntityHelper.createNewMarket(marketCode));
        when(districtRepository.save(Mockito.any())).thenReturn(EntityHelper.createDistrict(districtCode));
        when(subCityHallRepository.save(Mockito.any())).thenReturn(EntityHelper.createSubCityHall(subCityHallCode));

        final var districtDto = EntityHelper.createNewDistrictDto(districtCode);
        final var subCityHallDto = EntityHelper.createNewSubCityHallDto(subCityHallCode);
        final var marketDto = EntityHelper.createNewMarketDto(marketCode, districtDto, subCityHallDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/markets")
                .content(JsonHelper.asJsonString(marketDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists());
    }

    @Test
    public void shouldUpdateWhenCreateAMarketWithAnExistentCodeSuccessfully() throws Exception {

        long marketCode = 1;
        int districtCode = 2;
        int subCityHallCode = 3;

        final var market = EntityHelper.createNewMarket(marketCode);
        when(repository.findByCode(marketCode)).thenReturn(Optional.of(market));

        when(districtRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EntityHelper.createDistrict(districtCode)));
        when(subCityHallRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EntityHelper.createSubCityHall(subCityHallCode)));

        when(districtRepository.findByCode(Mockito.anyLong())).thenReturn(Optional.of(EntityHelper.createDistrict(districtCode)));
        when(subCityHallRepository.findByCode(Mockito.anyLong())).thenReturn(Optional.of(EntityHelper.createSubCityHall(subCityHallCode)));

        when(repository.save(Mockito.any())).thenReturn(EntityHelper.createNewMarket(marketCode));

        final var districtDto = EntityHelper.createNewDistrictDto(districtCode);
        final var subCityHallDto = EntityHelper.createNewSubCityHallDto(subCityHallCode);
        final var marketDto = EntityHelper.createNewMarketDto(marketCode, districtDto, subCityHallDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/markets")
                .content(JsonHelper.asJsonString(marketDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists());
    }

    @Test
    public void shouldUpdateAMarketSuccessfully() throws Exception {

        long marketCode = 1;
        int districtCode = 2;
        int subCityHallCode = 3;

        final var market = EntityHelper.createNewMarket(marketCode);
        when(repository.findByCode(marketCode)).thenReturn(Optional.of(market));

        when(districtRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EntityHelper.createDistrict(districtCode)));
        when(subCityHallRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EntityHelper.createSubCityHall(subCityHallCode)));

        when(districtRepository.findByCode(Mockito.anyLong())).thenReturn(Optional.of(EntityHelper.createDistrict(districtCode)));
        when(subCityHallRepository.findByCode(Mockito.anyLong())).thenReturn(Optional.of(EntityHelper.createSubCityHall(subCityHallCode)));

        when(repository.save(Mockito.any())).thenReturn(EntityHelper.createNewMarket(marketCode));

        final var districtDto = EntityHelper.createNewDistrictDto(districtCode);
        final var subCityHallDto = EntityHelper.createNewSubCityHallDto(subCityHallCode);
        final var marketDto = EntityHelper.createNewMarketDto(marketCode, districtDto, subCityHallDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/markets/{code}", marketCode)
                .content(JsonHelper.asJsonString(marketDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists());
    }

    @Test
    public void shouldNotUpdateAMarketWhenItDoesNotExists() throws Exception {

        long marketCode = 1;
        int districtCode = 2;
        int subCityHallCode = 3;

        when(repository.findByCode(marketCode)).thenReturn(Optional.empty());

        final var districtDto = EntityHelper.createNewDistrictDto(districtCode);
        final var subCityHallDto = EntityHelper.createNewSubCityHallDto(subCityHallCode);
        final var marketDto = EntityHelper.createNewMarketDto(marketCode, districtDto, subCityHallDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/markets/{code}", marketCode)
                .content(JsonHelper.asJsonString(marketDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAnExistentMarket() throws Exception {

        long marketCode = 1;

        final var market = EntityHelper.createNewMarket(marketCode);
        when(repository.findByCode(marketCode)).thenReturn(Optional.of(market));

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/markets/{code}", marketCode))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotDeleteAMarketWhenItDoesNotExists() throws Exception {

        long marketCode = 1;

        when(repository.findByCode(marketCode)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/markets/{code}", marketCode))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAnExistentMarket() throws Exception {

        long marketCode = 1;
        int districtCode = 2;
        int subCityHallCode = 3;

        final var market = EntityHelper.createNewMarket(marketCode);
        when(repository.findByCode(marketCode)).thenReturn(Optional.of(market));
        when(districtRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EntityHelper.createDistrict(districtCode)));
        when(subCityHallRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EntityHelper.createSubCityHall(subCityHallCode)));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/markets/{code}", marketCode))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotGetAnExistentMarketWhenDistrictWasNotFound() throws Exception {

        long marketCode = 1;

        final var market = EntityHelper.createNewMarket(marketCode);
        when(repository.findByCode(marketCode)).thenReturn(Optional.of(market));
        when(districtRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/markets/{code}", marketCode))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldNotGetAnExistentMarketWhenSubCityHallWasNotFound() throws Exception {

        long marketCode = 1;
        int districtCode = 2;

        final var market = EntityHelper.createNewMarket(marketCode);
        when(repository.findByCode(marketCode)).thenReturn(Optional.of(market));
        when(districtRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EntityHelper.createDistrict(districtCode)));
        when(subCityHallRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/markets/{code}", marketCode))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldNotGetAMarketWhenDoesNotExists() throws Exception {

        long marketCode = 1;

        when(repository.findByCode(marketCode)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/markets/{code}", marketCode))
                .andExpect(status().isNotFound());
    }
}