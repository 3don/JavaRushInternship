package com.game.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "player")
public class Player {

    public Player(String name, String title, Race race, Profession profession, Date birthday, Boolean banned, Integer experience) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.experience = experience;
        this.setLevel((int)(Math.sqrt(2500 + 200 * experience) - 50) / 100);
        this.setUntilNextLevel( 50 * (level + 1) * (level + 2) - experience);
        if (banned == null){ banned = false;}
    }

    public Player(Long id, String name, String title, Race race, Profession profession, Date birthday, Boolean banned, Integer experience, Integer level, Integer untilNextLevel) {

        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
        if (banned == null){ banned = false;}
    }

    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Date birthday;
    private Boolean banned;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;

    public Player() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    @Column(name = "name", length = 12)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "title", length = 30)
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    @Column (name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column (name = "banned")
    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Column (name = "experience")
    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
        this.setLevel(calculateLevel());
        this.setUntilNextLevel(calculateUntilNextLext());
    }

    @Column (name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column (name = "untilNextLevel")
    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Integer calculateLevel(){
    return (int)(Math.sqrt(2500 + 200 * experience) - 50) / 100;
    }

    public Integer calculateUntilNextLext(){
        return  50 * (level + 1 ) * (level + 2) - experience;
    }

    public boolean checkInputData(){
        if (this.banned == null) {
            setBanned(false);
        }
        if (birthday != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthday);
            if (cal.get(Calendar.YEAR) < 2000 || cal.get(Calendar.YEAR) > 3000 ) return false;
        }
        else {
            return false; }

        if (name.length() > 12 || name.length() == 0) return false;
        if (title.length() > 30) return false;
        if (experience < 0 || experience > 10000000) return false;

        return true;
    }
}

