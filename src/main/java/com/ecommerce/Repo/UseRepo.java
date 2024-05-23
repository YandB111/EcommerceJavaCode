package com.ecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.UseEntity;

@Repository
public interface UseRepo extends JpaRepository<UseEntity, Long>{

}
