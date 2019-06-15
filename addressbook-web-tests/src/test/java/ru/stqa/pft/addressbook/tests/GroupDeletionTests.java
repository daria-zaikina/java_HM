package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePrecondition() {
    if(app.db().groups().size() == 0) {
      app.group().createGroup(new GroupData()
              .withName("test1")
              .withHeader("test2")
              .withFooter("test3"));
    }
  }

    @Test
    public void testGroupDeletion () throws Exception {
      Groups before = app.db().groups();
      GroupData deletedGroup = before.iterator().next();
      app.goTo().groupPage();
      app.group().delete(deletedGroup);
      Groups after = app.db().groups();
      assertThat(app.group().count(), equalTo(before.size() - 1));
      assertThat(after, equalTo(before.without(deletedGroup)));
    }

}

