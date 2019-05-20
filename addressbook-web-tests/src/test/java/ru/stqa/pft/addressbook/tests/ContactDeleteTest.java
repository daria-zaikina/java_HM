package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeleteTest extends TestBase {

    @Test
    public void testContactDelete() throws Exception {

        app.getNavigationHelper().returnToContactPage();
        if (! app.getContactHelper().isThereContact()) {
            app.getContactHelper().contactCreation(new ContactData("Daria", "Zaikina", "89139330912", "blabla@gmail.com", "Novosibirsk Central st. 13", null) , true);
        }
        List<ContactData> before = app.getContactHelper().getContacList();
        app.getContactHelper().chooseContact(before.size()-1);
        app.getContactHelper().submitDeleteContact();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContacList();

        Assert.assertEquals(after.size(), before.size() - 1);

        Comparator<? super ContactData> byId = Comparator.comparingInt(g -> g.getId());
        before.remove(before.size()-1);
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);



    }
}
