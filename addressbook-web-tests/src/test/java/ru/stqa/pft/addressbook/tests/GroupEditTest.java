package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupEditTest extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
//        app.goTo().groupPage();
//        if (app.group().all().size() == 0) {
//            app.group().createGroup(new GroupData()
//                    .withName("test1")
//                    .withHeader("test2")
//                    .withFooter("test3"));
//        }
        if(app.db().groups().size() == 0) {
            app.group().createGroup(new GroupData()
                    .withName("test1")
                    .withHeader("test2")
                    .withFooter("test3"));
        }
    }

    @Test
    public void testGroupModify () throws Exception {
        Groups before = app.db().groups();
        GroupData modifyGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifyGroup.getId())
                .withName("test_name")
                .withHeader("test_header")
                .withFooter("test_footer");
        app.goTo().groupPage();
        app.group().modify(group);
        Groups after = app.db().groups();
        assertThat(app.group().count(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifyGroup).withAdded(group)));

    }

}
