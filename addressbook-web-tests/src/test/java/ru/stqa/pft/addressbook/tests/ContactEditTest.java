package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactEditTest extends TestBase {

    @Test
    public void testContactEdit() throws Exception {
        app.getNavigationHelper().returnToContactPage();
        if (! app.getContactHelper().isThereContact()) {
            app.getContactHelper().contactCreation(new ContactData("Daria", "Zaikina", "89139330912", "blabla@gmail.com", "Novosibirsk Central st. 13", null) , true);
        }
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("Elena", "Kirova", "89139330933", "blabla@gmail.com", "Novosibirsk Central st. 15", null), false);
        app.getContactHelper().submitUpdateContact();
        app.getNavigationHelper().returnToContactPage();



    }
}
