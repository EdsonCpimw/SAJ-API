package com.saj.api.modules.process.domain.converters;

import com.saj.api.modules.process.domain.enums.ProcessPriority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProcessPriorityConverter implements AttributeConverter<ProcessPriority, String> {

    @Override
    public String convertToDatabaseColumn(ProcessPriority priority) {
        return priority == null ? null : priority.getValue();
    }

    @Override
    public ProcessPriority convertToEntityAttribute(String value) {
        if (value == null) return null;
        for (ProcessPriority priority : ProcessPriority.values()) {
            if (priority.getValue().equals(value)) return priority;
        }
        throw new IllegalArgumentException("Prioridade inválido: " + value);
    }
}
