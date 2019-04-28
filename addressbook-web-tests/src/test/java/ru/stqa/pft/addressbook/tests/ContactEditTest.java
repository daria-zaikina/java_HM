package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactEditTest extends TestBase {

    @Test
    public void testContactEdit() throws Exception {
        app.getContactHelper().returnToContactPage();
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("Elena", "Kirova", "89139330933", "blabla@gmail.com", "Novosibirsk Central st. 15"));
        app.getContactHelper().submitUpdateContact();
        app.getContactHelper().returnToContactPage();



    }
}
