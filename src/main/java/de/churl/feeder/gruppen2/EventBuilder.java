package de.churl.feeder.gruppen2;

import com.github.javafaker.Faker;
import de.churl.feeder.gruppen2.event.AddUserEvent;
import de.churl.feeder.gruppen2.event.CreateGroupEvent;
import de.churl.feeder.gruppen2.event.DeleteUserEvent;
import de.churl.feeder.gruppen2.event.Event;
import de.churl.feeder.gruppen2.event.UpdateGroupDescriptionEvent;
import de.churl.feeder.gruppen2.event.UpdateGroupTitleEvent;
import de.churl.feeder.gruppen2.event.UpdateRoleEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventBuilder {

    private static final Faker faker = new Faker();

    private static String firstname() {
        return clean(faker.name().firstName());
    }

    private static String lastname() {
        return clean(faker.name().lastName());
    }

    private static String champion() {
        return clean(faker.leagueOfLegends().champion());
    }

    private static String quote() {
        return clean(faker.leagueOfLegends().quote());
    }

    private static String clean(String string) {
        return string.replaceAll("['\";,]", "");
    }

    /**
     * Generiert ein EventLog mit mehreren Gruppen und Usern.
     *
     * @param count       Gruppenanzahl
     * @param membercount Gesamte Mitgliederanzahl
     * @return Eventliste
     */
    public static List<Event> completeGroups(int count, int membercount) {
        int memPerGroup = membercount / count;

        return IntStream.rangeClosed(0, count)
                        .parallel()
                        .mapToObj(i -> completeGroup(memPerGroup))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
    }

    public static List<Event> completeGroup(int membercount) {
        List<Event> eventList = new ArrayList<>();
        long groupId = faker.random().nextInt(Integer.MAX_VALUE);

        eventList.add(createGroupEvent(groupId));
        eventList.add(updateGroupTitleEvent(groupId));
        eventList.add(updateGroupDescriptionEvent(groupId));
        eventList.addAll(addUserEvents(membercount, groupId));

        return eventList;
    }

    /**
     * Generiert mehrere CreateGroupEvents, 1 <= groupId <= count.
     *
     * @param count Anzahl der verschiedenen Gruppen
     * @return Eventliste
     */
    public static List<CreateGroupEvent> createGroupEvents(int count) {
        return IntStream.rangeClosed(0, count)
                        .parallel()
                        .mapToObj(EventBuilder::createGroupEvent)
                        .collect(Collectors.toList());
    }

    public static CreateGroupEvent createGroupEvent(long groupId) {
        return new CreateGroupEvent(
                groupId,
                faker.random().hex(),
                null,
                GroupType.SIMPLE,
                Visibility.PUBLIC,
                10000000L
        );
    }

    /**
     * Generiert mehrere AddUserEvents für eine Gruppe, 1 <= user_id <= count.
     *
     * @param count   Anzahl der Mitglieder
     * @param groupId Gruppe, zu welcher geaddet wird
     * @return Eventliste
     */
    public static List<Event> addUserEvents(int count, long groupId) {
        return IntStream.rangeClosed(1, count)
                        .parallel()
                        .mapToObj(i -> addUserEvent(groupId, String.valueOf(i)))
                        .collect(Collectors.toList());
    }

    public static AddUserEvent addUserEvent(long groupId, String userId) {
        String firstname = firstname();
        String lastname = lastname();

        return new AddUserEvent(
                groupId,
                userId,
                firstname,
                lastname,
                firstname + "." + lastname + "@mail.de"
        );
    }

    public static List<Event> deleteUserEvents(int count, List<Event> eventList) {
        List<Event> removeEvents = new ArrayList<>();
        List<Event> shuffle = eventList.parallelStream()
                                       .filter(event -> event instanceof AddUserEvent)
                                       .collect(Collectors.toList());

        Collections.shuffle(shuffle);

        for (Event event : shuffle) {
            removeEvents.add(new DeleteUserEvent(event.getGroupId(), event.getUserId()));

            if (removeEvents.size() >= count) {
                break;
            }
        }

        return removeEvents;
    }

    /**
     * Erzeugt mehrere DeleteUserEvents, sodass eine Gruppe komplett geleert wird.
     *
     * @param group Gruppe welche geleert wird
     * @return Eventliste
     */
    public static List<DeleteUserEvent> deleteUserEvents(Group group) {
        return group.getMembers().parallelStream()
                    .map(user -> deleteUserEvent(group.getId(), user.getId()))
                    .collect(Collectors.toList());
    }

    public static DeleteUserEvent deleteUserEvent(long groupId, String userId) {
        return new DeleteUserEvent(
                groupId,
                userId
        );
    }

    public static UpdateGroupDescriptionEvent updateGroupDescriptionEvent(long groupId) {
        return new UpdateGroupDescriptionEvent(
                groupId,
                faker.random().hex(),
                quote()
        );
    }

    public static UpdateGroupTitleEvent updateGroupTitleEvent(long groupId) {
        return new UpdateGroupTitleEvent(
                groupId,
                faker.random().hex(),
                champion()
        );
    }

    public static UpdateRoleEvent randomUpdateRoleEvent(long groupId, String userId, Role role) {
        return new UpdateRoleEvent(
                groupId,
                userId,
                role
        );
    }
}
