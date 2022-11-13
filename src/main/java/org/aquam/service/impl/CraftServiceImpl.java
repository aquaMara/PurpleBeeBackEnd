package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Craft;
import org.aquam.model.dto.CraftDto;
import org.aquam.repository.CraftRepository;
import org.aquam.service.CraftService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CraftServiceImpl implements CraftService {

    private final CraftRepository craftRepository;
    private final ModelMapper modelMapper;

    @Override
    public CraftDto findByName(String name) {
        return craftRepository.findByName(name).map(this::mapToDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Craft with name " + name + " does not exist"));
    }

    @Override
    public Boolean exists(String name) {
        return craftRepository.findByName(name).isPresent();
    }

    @Override
    public List<CraftDto> readAll() {
        List<Craft> crafts = craftRepository.findAll();
        if (crafts.isEmpty())
            throw new EntityNotFoundException("No crafts");
        return crafts.stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CraftDto create(Craft craft) {
        if (exists(craft.getName()))
            throw new EntityExistsException("Craft with name " + craft.getName() + " exists");
        Craft saved = craftRepository.save(craft);
        return mapToDto(saved);
    }

    @Override
    public CraftDto mapToDto(Craft craft) {
        return modelMapper.map(craft, CraftDto.class);
    }

    @Override
    public Craft mapFromDto(CraftDto craftDto) {
        return modelMapper.map(craftDto, Craft.class);
    }
}
