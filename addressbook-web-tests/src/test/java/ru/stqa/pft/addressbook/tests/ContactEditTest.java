package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactEditTest extends TestBase {

    @Test
    public void testContactEdit() throws Exception {
        app.getNavigationHelper().returnToContactPage();
        List<ContactData> before = app.getContactHelper().getContacList();
        if (! app.getContactHelper().isThereContact()) {
            app.getContactHelper().contactCreation(new ContactData("Daria", "Zaikina", "89139330912", "blabla@gmail.com", "Novosibirsk Central st. 13", null) , true);
        }
        app.getContactHelper().editContact(before.size()-1);
        ContactData contact = new ContactData(before.get(before.size()-1).getId(),"Elena", "Kirova", "89139330933", "blabla@gmail.com", "Novosibirsk Central st. 15", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitUpdateContact();
        app.getNavigationHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContacList();

        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size()-1);
        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(g -> g.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);



    }
}
