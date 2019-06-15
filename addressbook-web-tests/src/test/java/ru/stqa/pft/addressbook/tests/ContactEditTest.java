package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactEditTest extends TestBase {


    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0) {
            app.contact().contactCreation( new ContactData()
                    .withFirstName("Daria")
                    .withLastName("Zaikina")
                    .withMobile("89139330912")
                    .withEmail("blabla@gmail.com")
                    .withAdress("Novosibirsk Central st. 13"), true);
        }
    }

    @Test
    public void testContactEdit() throws Exception {
        Contacts before = app.db().contacts();
        ContactData modifyedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifyedContact.getId())
                .withFirstName("Elena")
                .withLastName("Kirova")
                .withMobile("89139330933")
                .withEmail("blabla@gmail.com")
                .withAdress("Novosibirsk Central st. 15");
        app.goTo().returnToContactPage();
        app.contact().modifyed(contact);
        Contacts after = app.db().contacts();
        assertThat(app.contact().count(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifyedContact).withAdded(contact)));


    }

}
