package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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

    public void editContact(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void submitDeleteContact() { click((By.xpath("//input[@value='Delete']"))); }

    public void acceptAlert() { alert(); }


    public void submitUpdateContact() { click(By.name("update")); }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"),contactData.getLastname());
        type(By.name("mobile"),contactData.getMobile());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address"), contactData.getAddress());

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
        contactCache = null;
        submitContactCreation();

    }

    public void create(ContactData contact) {
        fillContactForm(contact, true);
        submitContactCreation();
        contactCache = null;
        returnToContactPage();
    }

    public void delete(ContactData contact) {
        chooseContactById(contact.getId());
        submitDeleteContact();
        contactCache = null;
        acceptAlert();
        returnToContactPage();
    }


    public void modifyed(ContactData contact) {
//      chooseContactById(contact.getId());
        editContact(contact.getId());
        fillContactForm(contact, false);
        submitUpdateContact();
        contactCache = null;
        returnToContactPage();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if(contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        List<WebElement>  elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
        for (WebElement element: elements) {
            List<WebElement> elem = element.findElements(By.tagName("td"));
            String last_name = elem.get(1).getText();
            String first_name = elem.get(2).getText();
            String allPhones = elem.get(5).getText();
            String address = elem.get(3).getText();
            String allEmails = elem.get(4).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData().withId(id).withFirstName(first_name).withLastName(last_name)
                    .withAllEmails(allEmails)
                    .withAdress(address)
                    .withAllPhones(allPhones);
            contactCache.add(contact);
        }
        return new Contacts(contactCache);

    }

    public ContactData InfoFromEditForm(ContactData contact) {
        editContact(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String secondname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();

       return new ContactData().withId(contact.getId()).withFirstName(firstname)
               .withLastName(secondname)
               .withAdress(address)
               .withHomePhone(home)
               .withMobile(mobile)
               .withWorkPhone(work)
               .withEmail(email)
               .withEmail2(email2)
               .withEmail3(email3)
               ;



    }
}
