package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.service.PlayerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }


    @RequestMapping
    public List<Player> findAllPlayers(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) Race race,
                                       @RequestParam(required = false) Profession profession,
                                       @RequestParam(required = false) Long after,
                                       @RequestParam(required = false) Long before,
                                       @RequestParam(required = false) Boolean banned,
                                       @RequestParam(required = false) Integer minExperience,
                                       @RequestParam(required = false) Integer maxExperience,
                                       @RequestParam(required = false) Integer minLevel,
                                       @RequestParam(required = false) Integer maxLevel,
                                       @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                       @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                       @RequestParam(required = false, defaultValue = "3") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        List<Player> temp = playerService.findAllByPage(Specification.where(
                        PlayerSpecification.getByName(name).
                                and(PlayerSpecification.getByTitle(title)).
                                and(PlayerSpecification.getByRace(race)).
                                and(PlayerSpecification.getByProfession(profession)).
                                and(PlayerSpecification.getByBirthdayHigherThan(after)).
                                and(PlayerSpecification.getByBirthdayLowerThan(before)).
                                and(PlayerSpecification.getByExperienceHigherThan(minExperience)).
                                and(PlayerSpecification.getByExperienceLowerThan(maxExperience)).
                                and(PlayerSpecification.getByLevelHigherThan(minLevel)).
                                and(PlayerSpecification.getByLevelLowerThan(maxLevel)).
                                and(PlayerSpecification.getByAvailability(banned))
                ), pageable).getContent();

        return temp;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ResponseStatus(HttpStatus.OK)
    public Long getCount(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String title,
                         @RequestParam(required = false) Race race,
                         @RequestParam(required = false) Profession profession,
                         @RequestParam(required = false) Long after,
                         @RequestParam(required = false) Long before,
                         @RequestParam(required = false) Boolean banned,
                         @RequestParam(required = false) Integer minExperience,
                         @RequestParam(required = false) Integer maxExperience,
                         @RequestParam(required = false) Integer minLevel,
                         @RequestParam(required = false) Integer maxLevel){

        return playerService.countAll(Specification.where(
                PlayerSpecification.getByName(name).
                        and(PlayerSpecification.getByTitle(title)).
                        and(PlayerSpecification.getByRace(race)).
                        and(PlayerSpecification.getByProfession(profession)).
                        and(PlayerSpecification.getByBirthdayHigherThan(after)).
                        and(PlayerSpecification.getByBirthdayLowerThan(before)).
                        and(PlayerSpecification.getByExperienceHigherThan(minExperience)).
                        and(PlayerSpecification.getByExperienceLowerThan(maxExperience)).
                        and(PlayerSpecification.getByLevelHigherThan(minLevel)).
                        and(PlayerSpecification.getByLevelLowerThan(maxLevel)).
                        and(PlayerSpecification.getByAvailability(banned))
                ));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Player createPlayer(@RequestBody Player player){
        if (!player.checkInputData()) {
            throw new BadRequestPlayerException();
        }
        else {
        player.setLevel(player.calculateLevel());
        player.setUntilNextLevel(player.calculateUntilNextLext());

           return playerService.createPlayer(player);
        }
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Player findPlayerByid(@PathVariable Long id) {
        if (id == 0) throw new BadRequestPlayerException();
        try {
            return playerService.getById(id);
        }
        catch (Exception exception){
            throw new NotFoundPlayerException();
        }
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public void deletePlayer(@PathVariable Long id) {
        findPlayerByid(id);
        playerService.deleteById(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        Player temp = findPlayerByid(id);
        player.setId(temp.getId());
        checkPlayerBodyRequest(player, temp);
        if (!player.checkInputData()) {
            throw new BadRequestPlayerException();
        }
        else {
            player.setLevel(player.calculateLevel());
            player.setUntilNextLevel(player.calculateUntilNextLext());
        return playerService.editPlayer(player);
        }
    }

    public void checkPlayerBodyRequest(Player player, Player basePlayer){
        if (player.getName() == null) player.setName(basePlayer.getName());
        if (player.getTitle() == null) player.setTitle(basePlayer.getTitle());
        if (player.getRace() == null) player.setRace(basePlayer.getRace());
        if (player.getProfession() == null) player.setProfession(basePlayer.getProfession());
        if (player.getBirthday() == null) player.setBirthday(basePlayer.getBirthday());
        if (player.getBanned() == null) player.setBanned(basePlayer.getBanned());
        if (player.getExperience() == null) player.setExperience(basePlayer.getExperience());
    }
}

