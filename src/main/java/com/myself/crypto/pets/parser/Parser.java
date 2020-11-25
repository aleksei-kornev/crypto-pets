package com.myself.crypto.pets.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {

    private static final String PRISE_BY_PAIR = "https://min-api.cryptocompare.com/data/price?tsyms=USD&e=Cexio&api_key=3e88f4758c202f4681bee888084b05bb1818c4528db749e9002fbe15a812c3bd&fsym=";

    /**
     * Парсим значение цены для запрошенной пары по API биржи
     * @param pairName - String, запрошенная пара
     * @return - FLOAT, полученное по API значение цены
     * @throws IOException - если нет пары вылетит вот это.
     */
    public static Float getLastPriseByPairs(String pairName) throws IOException {
        String url = PRISE_BY_PAIR + pairName;

        String parsingData = getDataFromApi(url);

        JsonObject jsonObject = JsonParser.parseString(parsingData).getAsJsonObject();
        JsonElement price = jsonObject.get("USD");

        return price.getAsFloat();
    }

    /**
     * Получаем данные по API по заданному запросу
     * @param url - запрос на сервер в виде url
     * @return String, результат
     * @throws IOException вылетит если запрос не верный
     */
    public static String getDataFromApi(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
