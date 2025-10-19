package com.linktic.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linktic.inventario.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
  Optional<Inventario> findFirstByProductId(Long productId);
}