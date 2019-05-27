package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;

public class ContactHelper extends BaseHelper{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToContactPage() {
        click(By.linkText("home"));
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void chooseContact(int index) { wd.findElements(By.cssSelector("[name='selected[]']")).get(index).click(); }

    public void chooseContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void submitDeleteContact() { click((By.xpath("//input[@value='Delete']"))); }

    public void acceptAlert() { alert(); }

    public void editContact(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

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

    public void create(ContactData contact) {
        fillContactForm(contact, true);
        submitContactCreation();
        returnToContactPage();
    }

    public void delete(ContactData contact) {
        chooseContactById(contact.getId());
        submitDeleteContact();
        acceptAlert();
        returnToContactPage();
    }


    public void modifyed(ContactData contact) {
        chooseContactById(contact.getId());
        editContact(contact.getId());
        fillContactForm(contact, false);
        submitUpdateContact();
        returnToContactPage();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement>  elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
        for (WebElement element: elements) {
            List<WebElement> elem = element.findElements(By.tagName("td"));
            String last_name = elem.get(1).getText();
            String first_name = elem.get(2).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData().withId(id).withFirstName(first_name).withLastName(last_name);
            contacts.add(contact);
        }
        return contacts;

    }

}
