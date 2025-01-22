package com.mindhub.ms_user.services;

import java.util.List;

public interface GenericService<E> {

    E findById(Long Id) throws Exception;
    List<E> findAll();
    void deleteById(long id);
    E save(E entity);
}
