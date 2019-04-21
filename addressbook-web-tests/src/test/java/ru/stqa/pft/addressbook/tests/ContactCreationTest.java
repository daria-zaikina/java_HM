package ru.stqa.pft.addressbook.tests;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.gotoContactCreationPage();
    app.fillContactForm(new ContactData("Daria", "Zaikina", "89139330912", "blabla@gmail.com", "Novosibirsk Central st. 13"));
    app.submitContactCreation();
    app.returnToContactPage();
  }

}
