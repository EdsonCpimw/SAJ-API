package com.saj.api.modules.process.domain.converters;

import com.saj.api.modules.process.domain.enums.TypeMovements;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeMovementsConverter implements AttributeConverter<TypeMovements, String> {

    @Override
    public String convertToDatabaseColumn(TypeMovements typeMovements) {
        return typeMovements == null ? null : typeMovements.getValue();
    }

    @Override
    public TypeMovements convertToEntityAttribute(String value) {
        if (value == null) return null;
        for (TypeMovements typeMovements : TypeMovements.values()) {
            if (typeMovements.getValue().equals(value)) return typeMovements;
        }
        throw new IllegalArgumentException("Movimentação de processo inválido: " + value);
    }
}
