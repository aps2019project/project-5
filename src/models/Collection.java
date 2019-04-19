package models;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Collection<E> {
    private List<E> members = new ArrayList<>();

    public List<E> getMembers() {
        return this.members;
    }

    public void addMember(E member) {
        this.members.add(member);
    }

    public List<E> filterByName(String pattern) throws InvalidCollectionTypeException {
        if(members.size() == 0)
            return members;
        if(!Marketable.class.isAssignableFrom(members.get(0).getClass()))
            throw new InvalidCollectionTypeException();
        return members.stream().filter(
                (member) -> ((Marketable) member).getName().matches(pattern)
        ).collect(Collectors.toList());
    }

    public static class InvalidCollectionTypeException extends Exception {
        InvalidCollectionTypeException() {
            super("This collection is not 'Marketable'");
        }
    }
}
