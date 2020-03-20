package de.churl.feeder.gruppen2.event;

import de.churl.feeder.gruppen2.Group;
import de.churl.feeder.gruppen2.GroupType;
import de.churl.feeder.gruppen2.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // For Jackson
public class CreateGroupEvent extends Event {

    private Visibility groupVisibility;
    private Long groupParent;
    private GroupType groupType;
    private Long groupUserMaximum;

    public CreateGroupEvent(Long groupId, String userId, Long parent, GroupType type, Visibility visibility, Long userMaximum) {
        super(groupId, userId);
        this.groupParent = parent;
        this.groupType = type;
        this.groupVisibility = visibility;
        this.groupUserMaximum = userMaximum;
    }

    @Override
    public void applyEvent(Group group) {
        group.setId(this.groupId);
        group.setParent(this.groupParent);
        group.setType(this.groupType);
        group.setVisibility(this.groupVisibility);
        group.setUserMaximum(this.groupUserMaximum);
    }
}
