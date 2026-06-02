package com.saj.api.modules.process.domain.converters;

import com.saj.api.modules.process.domain.enums.LegalArea;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LegalAreaConverter implements AttributeConverter<LegalArea, String> {
    @Override
    public String convertToDatabaseColumn(LegalArea legalArea) {
        return legalArea == null ? null : legalArea.getValue();
    }

    @Override
    public LegalArea convertToEntityAttribute(String value) {
        if (value == null) return null;
        for (LegalArea legalArea : LegalArea.values()) {
            if (legalArea.getValue().equals(value)) return legalArea;
        }
        throw new IllegalArgumentException("Área jurídica inválida: " + value);
    }
}
