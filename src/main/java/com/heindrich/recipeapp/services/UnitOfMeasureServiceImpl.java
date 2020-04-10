package com.heindrich.recipeapp.services;

import com.heindrich.recipeapp.commands.UnitOfMeasureCommand;
import com.heindrich.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final ConversionService conversionService;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, ConversionService conversionService) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.conversionService = conversionService;
    }

    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasure -> conversionService.convert(unitOfMeasure, UnitOfMeasureCommand.class))
                .collect(Collectors.toSet());
    }
}
