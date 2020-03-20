package de.churl.feeder.gruppen2.event;

import de.churl.feeder.gruppen2.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // For Jackson
public class DeleteGroupEvent extends Event {

    public DeleteGroupEvent(Long groupId, String userId) {
        super(groupId, userId);
    }

    @Override
    public void applyEvent(Group group) {
        group.getRoles().clear();
        group.getMembers().clear();
        group.setTitle(null);
        group.setDescription(null);
        group.setVisibility(null);
        group.setType(null);
        group.setParent(null);
    }
}
