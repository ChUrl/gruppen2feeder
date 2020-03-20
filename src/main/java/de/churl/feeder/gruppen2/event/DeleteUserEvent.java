package de.churl.feeder.gruppen2.event;

import de.churl.feeder.gruppen2.Group;
import de.churl.feeder.gruppen2.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entfernt ein einzelnes Mitglied einer Gruppe.
 */
@Getter
@NoArgsConstructor // For Jackson
public class DeleteUserEvent extends Event {

    public DeleteUserEvent(Long groupId, String userId) {
        super(groupId, userId);
    }

    @Override
    public void applyEvent(Group group) {
        for (User user : group.getMembers()) {
            if (user.getId().equals(this.userId)) {
                group.getMembers().remove(user);
                group.getRoles().remove(user.getId());
                return;
            }
        }
    }
}
