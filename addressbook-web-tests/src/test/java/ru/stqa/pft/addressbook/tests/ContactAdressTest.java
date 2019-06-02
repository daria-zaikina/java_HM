package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdressTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().returnToContactPage();
        if (app.contact().all().size() == 0) {
            app.contact().contactCreation(new ContactData()
                    .withFirstName("Daria")
                    .withLastName("Zaikina")
                    .withMobile("89139330912")
                    .withEmail("blabla@gmail.com")
                    .withAdress("Novosibirsk Central st. 13"), true);
        }
    }
    @Test
    public void testContactAdress() {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().InfoFromEditForm(contact);

        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));

    }

}
