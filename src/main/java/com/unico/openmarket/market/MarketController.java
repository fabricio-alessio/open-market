package com.unico.openmarket.market;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/markets"})
public class MarketController {

    private MarketService service;

    public MarketController(MarketService service) {
        this.service = service;
    }

    @GetMapping
    public List findByFilters(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String region5,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String neighborhood){

        return service.findByFilters(
                Optional.ofNullable(district),
                Optional.ofNullable(region5),
                Optional.ofNullable(name),
                Optional.ofNullable(neighborhood));
    }

    @GetMapping(path = {"/{code}"})
    public ResponseEntity findByCode(@PathVariable long code){
        return service.findByCode(code)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody MarketDto marketDto){
        return service.create(marketDto)
                .map(newMarket -> {
                    URI location = URI.create(String.format("/markets/%s", marketDto.getCode()));
                    return ResponseEntity.created(location).body(newMarket);
                })
                .orElse(ResponseEntity.unprocessableEntity().build());
    }

    @PutMapping(value="/{code}")
    public ResponseEntity update(@PathVariable("code") long code,
                                 @RequestBody MarketDto marketDto) {
        return service.update(code, marketDto)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{code}"})
    public ResponseEntity <?> delete(@PathVariable long code) {
        return service.deleteByCode(code) ? ResponseEntity.accepted().build() : ResponseEntity.notFound().build();
    }
}
