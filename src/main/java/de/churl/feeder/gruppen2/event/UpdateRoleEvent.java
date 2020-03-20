package de.churl.feeder.gruppen2.event;

import de.churl.feeder.gruppen2.Group;
import de.churl.feeder.gruppen2.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aktualisiert die Gruppenrolle eines Teilnehmers.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor // For Jackson
public class UpdateRoleEvent extends Event {

    private Role newRole;

    public UpdateRoleEvent(Long groupId, String userId, Role newRole) {
        super(groupId, userId);
        this.newRole = newRole;
    }

    @Override
    public void applyEvent(Group group) {
        if (group.getRoles().containsKey(this.userId)) {
            group.getRoles().put(this.userId, this.newRole);
        }
    }

}
