package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends BaseHelper{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }


    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void chooseContact() { click(By.xpath("(//input[@type='checkbox'])[1]")); }

    public void submitDeleteContact() { click((By.xpath("//input[@value='Delete']"))); }

    public void acceptAlert() { alert(); }

    public void editContact() { click(By.xpath("(//img[@title='Edit'])[1]")); }

    public void submitUpdateContact() { click(By.name("update")); }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"),contactData.getLastname());
        type(By.name("mobile"),contactData.getMobile());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address2"), contactData.getAddress());

        if(creation && !notExistGroupForContact()) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else if (!creation) {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public boolean isThereContact() { return isElementPresent(By.name("selected[]")); }

    public boolean notExistGroupForContact() {
        try {
            wd.findElement(By.xpath("//select[@name='new_group']/option[@value='[none]']"));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }

    }

    public void gotoContactCreationPage() {
        click(By.linkText("add new"));
    }

    public void contactCreation(ContactData contact, boolean isCreation) {
        gotoContactCreationPage();
        fillContactForm(contact, isCreation);
        submitContactCreation();

    }
}
