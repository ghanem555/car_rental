package com.example.car_rental_system.Services;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;

@Component
public class ExchangeRateClient {
    private static final String APP_ID = "633940f3702446bbab153652b41e2847";

    public BigDecimal getExchangeRate(String from, String to) {
        if (from.equalsIgnoreCase(to)) return BigDecimal.ONE;

        String url = "https://openexchangerates.org/api/latest.json?app_id=" + APP_ID;
        RestTemplate rt = new RestTemplate();
        var resp = rt.getForObject(url, Map.class);
        Map<String, Object> rates = (Map<String, Object>) resp.get("rates");

        BigDecimal rateToUSD = new BigDecimal(rates.get(from).toString());
        BigDecimal rateEGP = new BigDecimal(rates.get(to).toString());

        return rateEGP.divide(rateToUSD, 6, BigDecimal.ROUND_HALF_UP);
    }
}
