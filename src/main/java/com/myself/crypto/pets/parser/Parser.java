package com.myself.crypto.pets.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myself.crypto.pets.entities.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private static final String API_KEY = "3e88f4758c202f4681bee888084b05bb1818c4528db749e9002fbe15a812c3bd";
    private static final String API_URL = "https://min-api.cryptocompare.com/data/pricemulti";
    private static final List<String> listOfCurrencies = Arrays.asList("USD");  //пока что реализованы только USD

    /**
     * Создаем ссылку для получения цен всех пар по API
     * @param currencies - List<Currency>, список монет, для которых обновляем цену
     * @param listOfCurrencies - String, список валют, в которых получаем цены
     * @return String - url для получения данных по API
     * (пример: https://https://min-api.cryptocompare.com/data/price?tsyms=USD&e=Cexio&api_key=3e88f4758c202f4681bee888084b05bb1818c4528db749e9002fbe15a812c3bd&fsym=ETH )
     */
    public static String createApiUrl (List<Currency> currencies, List<String> listOfCurrencies){
        List<String> listOfCoins = new ArrayList<String>();
        for (Currency currency : currencies) {
            listOfCoins.add(currency.getTicker());
        }
        String returnUrl = API_URL;
        returnUrl += "?tsyms=";
        returnUrl += listOfCurrencies.stream().collect(Collectors.joining(","));
        returnUrl += "&fsyms=";
        returnUrl += listOfCoins.stream().collect(Collectors.joining(","));
        returnUrl += "&api_key=" + API_KEY;
        return returnUrl;
    }

    /**
     * Парсим значение цены для запрошенной монеты из json, полученного по API биржи
     * @param jsonObject - JsonObject, json с нужной монетой и ценой
     * @param currency - String, валюта, в которой получаем цену (напр, USD или BTC)
     * @return - FLOAT, полученное по API значение цены
     * @throws IOException - если нет монеты вылетит вот это.
     */
    public static Float getPriсeFromJson(JsonObject jsonObject, String currency) throws IOException {
        JsonElement price = jsonObject.get(currency);
        return price.getAsFloat();
    }

    /**
     * Парсим значение цены для запрошенной пары по API биржи
     * @param currencies - List<Currency>, список запрошенных к обновлению монет
     * @param apiUrl - String, url до API источника котировок
     * @return - List<Currency>, обновленные монеты
     * @throws IOException - если нет пары вылетит вот это.
     */
    public static List<Currency> updatePriсesFromExchangeApi(List<Currency> currencies, String apiUrl) throws IOException {
        String parsingData = getDataFromApi(apiUrl);

        JsonObject jsonObject = JsonParser.parseString(parsingData).getAsJsonObject();
        for (Currency currency : currencies) {
            JsonObject coinObject = jsonObject.getAsJsonObject(currency.getTicker());
            currency.setUSD(getPriсeFromJson(coinObject,"USD"));
        }

        return currencies;
    }

    /**
     * Получаем данные по API
     * @param url - ссылка на сервер источника данных
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

    /**
     * Обновляет все котировки
     * @param currencies - список всех имеющихся монет
     * @return List<Currency> - возвращает список монет с обновленными ценами
     */
    public static List<Currency> updateAllCurrenciesPricesFromApi (List<Currency> currencies) {
        //создать url api
        String apiUrl = new String(createApiUrl(currencies, listOfCurrencies));

        // получаем данные по api, парсим и обновляем цены
        try {
            return updatePriсesFromExchangeApi(currencies, apiUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return currencies;
        }

    }
}
