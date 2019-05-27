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
        app.goTo().returnToContactPage();
        if (app.contact().all().size() == 0) {
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
        Contacts before = app.contact().all();
        ContactData modifyedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifyedContact.getId())
                .withFirstName("Elena")
                .withLastName("Kirova")
                .withMobile("89139330933")
                .withEmail("blabla@gmail.com")
                .withAdress("Novosibirsk Central st. 15");
        app.contact().modifyed(contact);
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifyedContact).withAdded(contact)));


    }

}
