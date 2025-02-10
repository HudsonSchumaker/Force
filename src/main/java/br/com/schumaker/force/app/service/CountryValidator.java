package br.com.schumaker.force.app.service;

import br.com.schumaker.force.framework.ioc.annotations.bean.Component;

import java.util.List;

@Component
public class CountryValidator implements Validator {

    private static final List<String> COUNTRIES = List.of("BR", "RU", "CN", "IN");

    @Override
    public boolean validate(String country) {
        if (country == null) {
            return false;
        }

        return COUNTRIES.contains(country);
    }
}
