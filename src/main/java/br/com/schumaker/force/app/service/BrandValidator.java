package br.com.schumaker.force.app.service;

import br.com.schumaker.force.framework.ioc.annotations.bean.Component;

import java.util.List;

@Component
public class BrandValidator implements Validator {

    private static final List<String> BRANDS = List.of("EMBRAER", "GAZPROM", "SINOPEC", "TATA");

    @Override
    public boolean validate(String brand) {
        if (brand == null) {
            return false;
        }

        return BRANDS.contains(brand);
    }
}
