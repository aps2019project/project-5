package models;

import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.Spell;
import models.items.UsableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Collection {
    private List<Marketable> members = new ArrayList<>();

    public List<Marketable> getMembers() {
        return this.members;
    }

    public void addMember(Marketable member) {
        this.members.add(member);
    }

    public List<Marketable> filterByName(String pattern) {
        if(members.size() == 0)
            return members;
        return members.stream().filter(
                (member) -> member.getName().matches(pattern)
        ).collect(Collectors.toList());
    }

    public List<Minion> getMinions() {
        return members.stream().filter(
                (card) -> (card instanceof Minion)
        ).map(
                (card) -> ((Minion) card)
        ).collect(Collectors.toList());
    }

    public List<Hero> getHeroes() {
        return members.stream().filter(
                (card) -> (card instanceof Hero)
        ).map(
                (card) -> ((Hero) card)
        ).collect(Collectors.toList());
    }

    public List<UsableItem> getItems() {
        return members.stream().filter(
                (card) -> (card instanceof UsableItem)
        ).map(
                (card) -> ((UsableItem) card)
        ).collect(Collectors.toList());
    }

    public List<Spell> getSpells() {
        return members.stream().filter(
                (card) -> (card instanceof Spell)
        ).map(
                (card) -> ((Spell) card)
        ).collect(Collectors.toList());
    }
}
