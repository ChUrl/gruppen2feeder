package de.churl.feeder.gruppen2.event;

import de.churl.feeder.gruppen2.Group;
import de.churl.feeder.gruppen2.Role;
import de.churl.feeder.gruppen2.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Fügt einen einzelnen Nutzer einer Gruppe hinzu.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor // For Jackson
public class AddUserEvent extends Event {

    private String givenname;
    private String familyname;
    private String email;

    public AddUserEvent(Long groupId, String userId, String givenname, String familyname, String email) {
        super(groupId, userId);
        this.givenname = givenname;
        this.familyname = familyname;
        this.email = email;
    }

    @Override
    public void applyEvent(Group group) {
        User user = new User(this.userId, this.givenname, this.familyname, this.email);

        group.getMembers().add(user);
        group.getRoles().put(userId, Role.MEMBER);
    }
}
