package de.churl.feeder.gruppen2.event;

import de.churl.feeder.gruppen2.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Ändert nur die Gruppenbeschreibung.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor // For Jackson
public class UpdateGroupDescriptionEvent extends Event {

    private String newGroupDescription;

    public UpdateGroupDescriptionEvent(Long groupId, String userId, String newGroupDescription) {
        super(groupId, userId);
        this.newGroupDescription = newGroupDescription;
    }

    @Override
    public void applyEvent(Group group) {
        group.setDescription(this.newGroupDescription);
    }
}
