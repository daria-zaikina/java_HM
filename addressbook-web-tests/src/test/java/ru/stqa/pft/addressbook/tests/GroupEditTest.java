package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupEditTest extends TestBase {

    @Test
    public void testGroupModify () throws Exception {

        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().editGroup();
        app.getGroupHelper().fillGroupForm(new GroupData("test_name","test_header", "test_footer"));
        app.getGroupHelper().updateGroup();
        app.getGroupHelper().returnToGroupPage();

    }
}
