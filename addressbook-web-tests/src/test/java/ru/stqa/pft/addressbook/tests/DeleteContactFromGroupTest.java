package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class DeleteContactFromGroupTest extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0) {
            app.goTo().returnToContactPage();
            app.contact().contactCreation( new ContactData()
                    .withFirstName("Daria")
                    .withLastName("Zaikina")
                    .withMobile("89139330912")
                    .withEmail("blabla@gmail.com")
                    .withAdress("Novosibirsk Central st. 13"), true);
        }

        if(app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().createGroup(new GroupData()
                    .withName("test1")
                    .withHeader("test2")
                    .withFooter("test3"));
        }
    }

    @Test
    public void testDeleteContactFromGroup() {
        Contacts contacts = app.db().contacts();
        ContactData contactForDeleteGroup = contacts.iterator().next();
        int idContactForDeleteGroup= contactForDeleteGroup.getId();
        Groups contactsWithGroupsBefore = contactForDeleteGroup.getGroups();
        if(contactsWithGroupsBefore.size() == 0) {
            Groups groups = app.db().groups();
            GroupData groupForAdd = groups.stream().iterator().next();
            app.goTo().returnToContactPage();
            app.contact().addToGroup(groupForAdd, contactForDeleteGroup);
            app.db().contacts();
        }
        contactsWithGroupsBefore = contactForDeleteGroup.getGroups();
        GroupData group = contactsWithGroupsBefore.iterator().next();
        app.goTo().returnToContactPage();
        app.contact().deleteFromGroup(contactForDeleteGroup, group);

        Contacts after = app.db().contacts();
        ContactData contactsAfter = after.stream().filter(data -> Objects.equals(data.getId(), idContactForDeleteGroup)).findFirst().get();
        Groups contactWithGroupsAfter = contactsAfter.getGroups();
        assertThat(contactWithGroupsAfter, equalTo(contactsWithGroupsBefore.without(group)));
        verifyContactsListInUi();

    }

}
