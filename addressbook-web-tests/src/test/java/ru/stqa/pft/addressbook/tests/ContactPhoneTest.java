package ru.stqa.pft.addressbook.tests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTest extends TestBase {
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
    public void testContactPhone() {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().InfoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));

    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork()).stream()
                .filter((s) -> ! s.equals(""))
                .map(ContactPhoneTest::clearned)
                .collect(Collectors.joining("\n"));

    }

    public static String clearned(String phone) {
       return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
