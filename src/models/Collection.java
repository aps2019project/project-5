package models;

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
}
