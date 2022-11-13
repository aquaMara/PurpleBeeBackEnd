package org.aquam.service;

import org.aquam.model.Craft;

import java.util.List;

public interface CraftService {

    Craft findByName(String name);
    Boolean exists(String name);
    List<Craft> readAll();
    Craft create(Craft craft);
}
