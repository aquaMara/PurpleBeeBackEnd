package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Craft;
import org.aquam.repository.CraftRepository;
import org.aquam.service.CraftService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CraftServiceImpl implements CraftService {

    private final CraftRepository craftRepository;

    @Override
    public Craft findByName(String name) {
        return craftRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Such craft does not exist"));
    }

    @Override
    public Boolean exists(String name) {
        if (craftRepository.findByName(name).isPresent())
            return true;
        return false;
    }

    @Override
    public List<Craft> readAll() {
        List<Craft> crafts = craftRepository.findAll();
        if (crafts.isEmpty())
            throw new EntityNotFoundException("No crafts");
        return crafts;
    }

    @Override
    public Craft create(Craft craft) {
        if (exists(craft.getName()))
            throw new EntityExistsException("Such craft exists");
        return craftRepository.save(craft);
    }
}
