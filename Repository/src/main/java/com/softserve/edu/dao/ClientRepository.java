package com.softserve.edu.dao;

import com.softserve.edu.entity.user.Client;
import com.softserve.edu.entity.catalogue.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Region findByName(String name);
}