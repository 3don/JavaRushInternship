package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    List<Player> findAll(Specification specification);
    Page<Player> findAllByPage(Specification specification, Pageable pageable);
    Long countAll(Specification specification);
    Player createPlayer(Player player);
    Player editPlayer(Player player);
    void deleteById(Long id);
    Player getById(Long id);


}
