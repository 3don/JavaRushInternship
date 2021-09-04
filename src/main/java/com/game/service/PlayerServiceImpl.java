package com.game.service;

import com.game.controller.BadRequestPlayerException;
import com.game.controller.NotFoundPlayerException;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService{


    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.playerRepository = repository;
    }

    @Override
    public List<Player> findAll(Specification specification) {
        return playerRepository.findAll(specification);
    }

    @Override
    public Page<Player> findAllByPage(Specification specification, Pageable pageable){
        return playerRepository.findAll(specification, pageable);
    }

    @Override
    public Long countAll(Specification specification) {
        return playerRepository.count(specification);
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.saveAndFlush(player);
    }

    @Override
    public Player editPlayer(Player player) {
        return playerRepository.saveAndFlush(player);
    }

    @Override
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Player getById(Long id) {
        return playerRepository.findById(id).get();
    }
}
