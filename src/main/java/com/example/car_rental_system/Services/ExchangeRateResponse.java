package com.example.car_rental_system.Services;

import java.math.BigDecimal;

public class ExchangeRateResponse {
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public static class Info {
        private BigDecimal rate;

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }
    }
}

