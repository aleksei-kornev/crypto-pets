package com.myself.crypto.pets.repositories;

import com.myself.crypto.pets.entities.Portfolio;
import com.myself.crypto.pets.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionsRepository extends JpaRepository<Position, Long> {
}