package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTest extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContactFromJson() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    System.out.println(new File(".").getAbsolutePath());
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
    String json = "";
    String line = reader.readLine();
    while (line != null) {
      json +=line;
      line = reader.readLine();
    }
    Gson gson  = new Gson();
    List<ContactData> contact = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
    return contact.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test(enabled = false)
  public void testContactCreation() throws Exception {
    app.goTo().returnToContactPage();
    Contacts before = app.contact().all();
    app.contact().gotoContactCreationPage();
    File photo = new File("src/test/resources/contact.jpg");
    ContactData contact = new ContactData()
            .withFirstName("Daria")
            .withLastName("Zaikina")
            .withMobile("89139330912")
            .withPhoto(photo)
            .withEmail("blabla@gmail.com")
            .withAdress("Novosibirsk Central st. 13");
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(app.contact().count(), equalTo(before.size()+1));
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }
  @Test(dataProvider = "validContactFromJson")
  public void testContactCreationFromJson(ContactData contact) throws Exception {
    app.goTo().returnToContactPage();
    Contacts before = app.contact().all();
    app.contact().gotoContactCreationPage();
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(app.contact().count(), equalTo(before.size()+1));
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }

//  @Test
//  public void currentDir() {
//    File currentDir = new File(".");
//    System.out.println(currentDir.getAbsolutePath());
//
//  }

}
