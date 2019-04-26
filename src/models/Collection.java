package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Collection {
    private List<MarketObject> cards = new ArrayList<>();

    public List<MarketObject> getCards() {
        return this.cards;
    }

    public void addMember(MarketObject member) {
        this.cards.add(member);
    }

    public List<MarketObject> filterByName(String pattern) {
        if(cards.size() == 0)
            return cards;
        return cards.stream().filter(
                (member) -> member.getName().matches(pattern)
        ).collect(Collectors.toList());
    }
}
