package com.saj.api.modules.process.domain.converters;

import com.saj.api.modules.process.domain.enums.ProcessStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProcessStatusConverter implements AttributeConverter<ProcessStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProcessStatus status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public ProcessStatus convertToEntityAttribute(String value) {
        if (value == null) return null;
        for (ProcessStatus status : ProcessStatus.values()) {
            if (status.getValue().equals(value)) return status;
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }
}
