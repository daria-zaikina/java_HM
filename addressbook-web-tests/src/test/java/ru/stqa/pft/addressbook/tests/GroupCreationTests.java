package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    System.out.println(new File(".").getAbsolutePath());
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml +=line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      List<GroupData> group = (List<GroupData>) xstream.fromXML(xml);
      return group.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
//    String xml = "";
//    String line = reader.readLine();
//    while (line != null) {
//      xml +=line;
//      line = reader.readLine();
//    }
//    XStream xstream = new XStream();
//    List<GroupData> group = (List<GroupData>) xstream.fromXML(xml);
//    return group.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    System.out.println(new File(".").getAbsolutePath());
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json +=line;
        line = reader.readLine();
      }
      Gson gson  = new Gson();
      List<GroupData> group = gson.fromJson(json, new TypeToken<List<GroupData>>() {}.getType());
      return group.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
//    String json = "";
//    String line = reader.readLine();
//    while (line != null) {
//      json +=line;
//      line = reader.readLine();
//    }
//    Gson gson  = new Gson();
//    List<GroupData> group = gson.fromJson(json, new TypeToken<List<GroupData>>() {}.getType());
//    return group.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test(dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().initGroupCreation();
//    GroupData group = new GroupData()
//            .withName("test1")
//            .withHeader("test2")
//            .withFooter("test3");
    app.group().create(group);
    Groups after = app.group().all();

    assertThat(after.size(), equalTo(before.size()+1));

    assertThat(after, equalTo(before
            .withAdded( group.withId(after
            .stream()
            .mapToInt((g) -> g.getId())
            .max()
            .getAsInt()))));

  }

  @Test(enabled = false)
  public void testBadGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().initGroupCreation();
    GroupData group = new GroupData()
            .withName("test1'")
            .withHeader("test2")
            .withFooter("test3");
    app.group().create(group);
    Groups after = app.group().all();

    assertThat(app.group().count(), equalTo(before.size()));

    assertThat(after, equalTo(before));

  }

}
