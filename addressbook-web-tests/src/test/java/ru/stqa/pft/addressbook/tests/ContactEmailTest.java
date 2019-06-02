package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {
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
    public void  testContactEmail() {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().InfoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).stream()
                .filter((s) -> ! s.equals(""))
                .map(ContactEmailTest::clearned)
                .collect(Collectors.joining("\n"));

    }

    public static String clearned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
