package models.cards;

public class Deck extends Collection {
    public String name;

    @Override
    public boolean add(Card card) {
        System.out.println("deck " + name + " size is: " + size());
        if (size() >= 20) {
            System.out.println("deck is full.");
            return false;
        } else if (getHeroCount() > 0 && card instanceof Hero) {
            System.out.println("can't add more than one hero to deck.");
            return false;
        } else if (getHeroCount() == 0 && !(card instanceof Hero) && size() == 19) {
            System.out.println("can't add more than one hero to deck.");
            return false;
        }
        return super.add(card);
    }

    public boolean isValid() {
        return size() == 20 && getHeroCount() == 1;
    }

    public Deck(String name) {
        this.name = name;
    }

    public int getHeroCount() {
        return filter(Hero.class, "").size();
    }
}
