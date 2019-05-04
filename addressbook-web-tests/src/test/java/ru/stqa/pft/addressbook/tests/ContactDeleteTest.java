package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeleteTest extends TestBase {

    @Test
    public void testContactDelete() throws Exception {

        app.getNavigationHelper().returnToContactPage();
        app.getContactHelper().chooseContact();
        app.getContactHelper().submitDeleteContact();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().returnToContactPage();



    }
}
