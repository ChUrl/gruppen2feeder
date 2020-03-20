package de.churl.feeder.gruppen2.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.churl.feeder.gruppen2.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@SuppressWarnings("ClassReferencesSubclass")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
                      @JsonSubTypes.Type(value = AddUserEvent.class, name = "AddUserEvent"),
                      @JsonSubTypes.Type(value = CreateGroupEvent.class, name = "CreateGroupEvent"),
                      @JsonSubTypes.Type(value = DeleteUserEvent.class, name = "DeleteUserEvent"),
                      @JsonSubTypes.Type(value = UpdateGroupDescriptionEvent.class, name = "UpdateGroupDescriptionEvent"),
                      @JsonSubTypes.Type(value = UpdateGroupTitleEvent.class, name = "UpdateGroupTitleEvent"),
                      @JsonSubTypes.Type(value = UpdateRoleEvent.class, name = "UpdateRoleEvent"),
              })
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {

    protected Long groupId;
    protected String userId;

    public void apply(Group group) {
        applyEvent(group);
    }

    protected abstract void applyEvent(Group group);
}
