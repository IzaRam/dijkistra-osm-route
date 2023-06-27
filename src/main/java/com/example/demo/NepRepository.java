package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface NepRepository extends JpaRepository<Nep2po4pgr, Integer> {
    List<Nep2po4pgr> findBySource(Integer source);

    Optional<Nep2po4pgr> findBySourceAndTarget(Integer source, Integer target);
}
