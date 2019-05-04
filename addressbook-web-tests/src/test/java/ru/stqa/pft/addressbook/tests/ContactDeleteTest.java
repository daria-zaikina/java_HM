package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeleteTest extends TestBase {

    @Test
    public void testContactDelete() throws Exception {

        app.getNavigationHelper().returnToContactPage();
        if (! app.getContactHelper().isThereContact()) {
            app.getContactHelper().contactCreation(new ContactData("Daria", "Zaikina", "89139330912", "blabla@gmail.com", "Novosibirsk Central st. 13", null) , true);
        }
        app.getContactHelper().chooseContact();
        app.getContactHelper().submitDeleteContact();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().returnToContactPage();



    }
}
