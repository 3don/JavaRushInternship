package com.game.service;

import com.game.controller.BadRequestPlayerException;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PlayerSpecification {

    public static Specification<Player> getByName(String name){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return name == null ? null : cb.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    public static Specification<Player> getByTitle(String title){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return title == null ? null : cb.like(root.get("title"), "%" + title + "%");
            }
        };
    }

    public static Specification<Player> getByRace(Race race){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return race == null ? null : cb.equal(root.get("race"), race);
            }
        };
    }

    public static Specification<Player> getByProfession(Profession profession){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return profession == null ? null : cb.equal(root.get("profession"), profession);
            }
        };
    }

    public static Specification<Player> getByBirthdayHigherThan(Long after){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              //  if (!checkDate(after)) throw new BadRequestPlayerException();
                return after == null ? null : cb.greaterThanOrEqualTo(root.get("birthday"), new Date(after));
            }
        };
    }

    public static Specification<Player> getByBirthdayLowerThan(Long before){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            //    if (!checkDate(before)) throw new BadRequestPlayerException();
                return before == null ? null : cb.lessThanOrEqualTo(root.get("birthday"), new Date(before));
            }
        };
    }

    public static Specification<Player> getByExperienceHigherThan(Integer higher){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return higher == null ? null : cb.greaterThanOrEqualTo(root.get("experience"), higher);
            }
        };
    }

    public static Specification<Player> getByExperienceLowerThan(Integer lower){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return lower == null ? null : cb.lessThanOrEqualTo(root.get("experience"), lower);
            }
        };
    }

    public static Specification<Player> getByLevelHigherThan(Integer higher){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return higher == null ? null : cb.greaterThanOrEqualTo(root.get("level"), higher);
            }
        };
    }

    public static Specification<Player> getByLevelLowerThan(Integer lower){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return lower == null ? null : cb.lessThanOrEqualTo(root.get("level"), lower);
            }
        };
    }

    public static Specification<Player> getByAvailability(Boolean banned){
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return banned == null ? null : cb.equal(root.get("banned"), banned);
            }
        };
    }

    public static boolean checkDate(Long date){
        if (date < 946684800000L || date > 32535129600000L) return false;
        return true;
    }

}
