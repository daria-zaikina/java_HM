package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import ru.stqa.pft.addressbook.model.Contacts;


public class ContactDeleteTest extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0) {
            app.contact().contactCreation(new ContactData()
                    .withFirstName("Daria")
                    .withLastName("Zaikina")
                    .withMobile("89139330912")
                    .withEmail("blabla@gmail.com")
                    .withAdress("Novosibirsk Central st. 13"), true);
        }
    }

    @Test
    public void testContactDelete() throws Exception {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.goTo().returnToContactPage();
        app.contact().delete(deletedContact);
        Contacts after = app.db().contacts();
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        assertThat(after, equalTo(before.without(deletedContact)));


    }

}
