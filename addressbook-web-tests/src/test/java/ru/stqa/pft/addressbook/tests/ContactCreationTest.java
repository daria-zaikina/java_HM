package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().gotoContactCreationPage();
    app.getContactHelper().fillContactForm(new ContactData("Daria", "Zaikina", "89139330912", "blabla@gmail.com", "Novosibirsk Central st. 13", null) , true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToContactPage();
  }

}
