package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().returnToContactPage();
    Contacts before = app.contact().all();
    app.contact().gotoContactCreationPage();
    ContactData contact = new ContactData()
            .withFirstName("Daria")
            .withLastName("Zaikina")
            .withMobile("89139330912")
            .withEmail("blabla@gmail.com")
            .withAdress("Novosibirsk Central st. 13");
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()+1));
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }

}
