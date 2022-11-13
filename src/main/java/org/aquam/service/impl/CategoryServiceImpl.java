package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
}
