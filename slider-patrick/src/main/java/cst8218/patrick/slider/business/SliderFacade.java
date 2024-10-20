/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package cst8218.patrick.slider.business;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import cst8218.patrick.slider.game.Slider;

/**
 * Copied from facade class from addressBook to be a subclass of the AbstractFacade
 * @author gemm0054
 */
@Stateless
public class SliderFacade extends AbstractFacade<Slider> {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SliderFacade() {
        super(Slider.class);
    }

}
