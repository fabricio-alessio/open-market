package com.unico.openmarket.scripts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class ScriptLoadOpenMarkets {

    private static final String OPEN_MARKET_URL = "http://localhost:8095/markets";
    private static final String MARKETS_FILE_NAME = "data/DEINFO_AB_FEIRASLIVRES_2014.csv";

    private PostClient client = new PostClient();
    private ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws URISyntaxException {

        final var script = new ScriptLoadOpenMarkets();
        final InputStream stream = script.getFileFromResourceAsStream(MARKETS_FILE_NAME);
        script.execute(stream);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    private void execute(InputStream is) {

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                loadLine(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLine(String line) {
        System.out.println(line);

        final var marketDtoOpt = CsvLineOpenMarketAdapter.adaptFromLine(line);
        marketDtoOpt.ifPresent(marketDto -> {
            System.out.println(marketDto);
            try {
                String json = mapper.writeValueAsString(marketDto);
                String response = client.post(OPEN_MARKET_URL, json);
                System.out.println(response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
