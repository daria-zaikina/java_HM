package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().returnToContactPage();
    List<ContactData> before = app.getContactHelper().getContacList();
    app.getContactHelper().gotoContactCreationPage();
    ContactData contact = new ContactData("Daria", "Zaikina", "89139330912", "blabla@gmail.com", "Novosibirsk Central st. 13", null);
    app.getContactHelper().fillContactForm(contact, true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToContactPage();
    List<ContactData> after = app.getContactHelper().getContacList();

    Assert.assertEquals(after.size(), before.size()+1);

    Comparator<? super ContactData> byId = Comparator.comparingInt(g -> g.getId());
    before.add(contact);
    after.sort(byId);
    before.sort(byId);

    Assert.assertEquals(before, after);

  }

}
