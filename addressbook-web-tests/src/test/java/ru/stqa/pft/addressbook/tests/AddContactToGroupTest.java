package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class AddContactToGroupTest extends TestBase{

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
    public void testAddContactToGroup() {
        Contacts before = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData contactForAdd = before.iterator().next();
        int idContactForAdd = contactForAdd.getId();
        Groups contactGroupsBefore = contactForAdd.getGroups();
        groups.removeAll(contactGroupsBefore);
        while (groups.size() == 0){
            contactForAdd = before.iterator().next();
        }
        GroupData groupForAdd = groups.stream().iterator().next();
        app.goTo().returnToContactPage();
        app.contact().addToGroup(groupForAdd, contactForAdd);
        Contacts after = app.db().contacts();
        ContactData contactAfter = after.stream().filter(data -> Objects.equals(data.getId(), idContactForAdd)).findFirst().get();
        Groups contactGroupsAfter = contactAfter.getGroups();
        assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.withAdded(groupForAdd)));
        verifyContactsListInUi();

    }
}
