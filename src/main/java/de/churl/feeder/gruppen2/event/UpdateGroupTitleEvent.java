package de.churl.feeder.gruppen2.event;

import de.churl.feeder.gruppen2.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Ändert nur den Gruppentitel.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor // For Jackson
public class UpdateGroupTitleEvent extends Event {

    private String newGroupTitle;

    public UpdateGroupTitleEvent(Long groupId, String userId, String newGroupTitle) {
        super(groupId, userId);
        this.newGroupTitle = newGroupTitle;
    }

    @Override
    public void applyEvent(Group group) {
        group.setTitle(this.newGroupTitle);
    }

}
