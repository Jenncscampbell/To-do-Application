import model.*;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {


    @Test
    public void testConstructor(){
        Task testTask = new Task("hello world");
        assertEquals(0, testTask.getTags().size());
        assertEquals(null, testTask.getDueDate());
        Assertions.assertEquals(Status.valueOf("TODO"), testTask.getStatus());
        Priority test = new Priority(4);
        assertEquals(test.toString(), testTask.getPriority().toString());
        assertEquals(0, testTask.getProgress());
        assertEquals(0, testTask.getEstimatedTimeToComplete());

    }

    @Test
    public void testConstructorThrowExceptions(){
        try {
            Task emptyTask = new Task("");
            fail("Should have thrown empty string exception");
        } catch (EmptyStringException e) {
            // expected
        }
        try {
            Task emptyTask = new Task(null);
            fail("Should have thrown empty string exception");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testConstructorShouldNotThrowExceptions(){
        try {
            Task emptyTask = new Task("hi");
        } catch (EmptyStringException e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testEmptyStringConstuctor(){
        try {
            Task testTask = new Task("");
            fail("Should have thrown Empty String Exception");
        } catch (EmptyStringException e){
            //expected
        }
    }

    @Test
    public void testNullStringConstuctor(){
        try {
            Task testTask = new Task(null);
            fail("Should have thrown Empty String Exception");
        } catch (EmptyStringException e){
            //expected
        }
    }

    @Test
    public void testAddEmptyTag(){
        try {
            Task testTask = new Task("describing");
            testTask.addTag("");
            fail("Cannot add a tag with no name");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testAddNullTag(){
        try {
            Task testTask = new Task("describing");
            testTask.addTag((String) null);
            fail("Cannot add a tag with no name");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testRemoveEmptyTag(){
        try {
            Task testTask = new Task("describing");
            testTask.addTag("taggy");
            testTask.removeTag("");
            fail("Cannot remove a tag with no name");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testRemoveNullTag(){
        try {
            Task testTask = new Task("describing");
            testTask.addTag("taggy");
            testTask.removeTag((String) null);
            fail("Cannot remove a tag with no name");
        } catch (EmptyStringException e) {
            //expected
        }
    }


    @Test
    public void testTaskAddDiffTags(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggiest");
        assertEquals(2, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        assertTrue(testTask.containsTag("taggiest"));
        assertFalse(testTask.containsTag("not here"));
        }

    @Test
    public void testTaskAddSameTags(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggy tag");
        assertTrue(testTask.containsTag("taggy tag"));
        assertEquals(1, testTask.getTags().size());

    }

    @Test
    public void testDueDate(){
        Task t1 = new Task("hi");
        Date dueDate = Calendar.getInstance().getTime();
        t1.setDueDate(new DueDate(dueDate));
        t1.addTag("hello");
        t1.setPriority(new Priority(1));
        Task t2 = new Task("hi");
        t2.setDueDate(new DueDate(dueDate));
        t2.addTag("hello");
        t2.setPriority(new Priority(1));
        assertTrue(t1.equals(t2));
        Task t3 = new Task("hi");
        Calendar date2 = Calendar.getInstance();
        date2.set(Calendar.MONTH,1);
        DueDate dueDate2 = new DueDate(date2.getTime());
        t3.setDueDate(dueDate2);
        t3.addTag("bye");
        t3.setPriority(new Priority(2));
        assertFalse(t1.equals(t3));
    }

    @Test
    public void testTaskAddManyTags(){
        Task testTask = new Task("describing");
        testTask.addTag("tag1");
        testTask.addTag("tag2");
        testTask.addTag("tag3");
        testTask.addTag("tag4");
        assertEquals("\n" +
                "{\n" +
                "\tDescription: describing\n" +
                "\tDue date: \n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1, #tag2, #tag3, #tag4\n" +
                "}", testTask.toString());
    }

    @Test
    public void testTaskRemoveOneTag(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.removeTag("taggy tag");
        assertEquals(0, testTask.getTags().size());
        assertFalse(testTask.containsTag("taggy tag"));
    }

    @Test
    public void testTaskRemoveNewestTag(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggiest");
        assertEquals(2, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggiest"));
        testTask.removeTag("taggiest");
        assertEquals(1, testTask.getTags().size());
        assertFalse(testTask.containsTag("taggiest"));
    }

    @Test
    public void testTaskRemoveFirstTag(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggiest");
        assertEquals(2, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggiest"));
        testTask.removeTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertFalse(testTask.containsTag("taggy tag"));
    }

    @Test
    public void testTaskRemoveSecondTag(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggiest");
        testTask.addTag("taggy taggy");
        assertEquals(3, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggiest"));
        testTask.removeTag("taggiest");
        assertEquals(2, testTask.getTags().size());
        assertFalse(testTask.containsTag("taggiest"));
    }

    @Test
    public void testTaskRemoveTwoTags(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggiest");
        testTask.addTag("taggy taggy");
        assertEquals(3, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggiest"));
        testTask.removeTag("taggiest");
        testTask.removeTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertFalse(testTask.containsTag("taggiest"));
        assertFalse(testTask.containsTag("taggy tag"));
        assertTrue(testTask.containsTag("taggy taggy"));

    }

    @Test
    public void testTaskRemoveNonExistantTag(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggiest");
        assertEquals(2, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggiest"));
        testTask.removeTag("taggyyy");
        assertEquals(2, testTask.getTags().size());
        assertFalse(testTask.containsTag("taggyyy"));
    }


    @Test
    public void testGetTags(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        testTask.addTag("taggiest");
        testTask.addTag("taggy taggy");
        assertEquals(3, testTask.getTags().size());
        Set testSet = testTask.getTags();
        Tag testTag = new Tag("taggyyy tag");
        assertFalse(testSet.contains(testTag));
    }

    @Test
    public void testGetPriority(){
        Task testTask = new Task("describing");
        Priority p1 = new Priority(2);
        testTask.setPriority(p1);
        assertEquals(p1.toString(),testTask.getPriority().toString());
    }

    @Test
    public void testSetPriority(){
        Task testTask = new Task("describing");
        Priority p1 = new Priority(2);
        testTask.setPriority(p1);
        assertEquals(p1.toString(),testTask.getPriority().toString());
    }

    @Test
    public void testSetPriorityNull(){
        try {
            Task testTask = new Task("describing");
            testTask.setPriority(null);
            fail("Should have thrown Null Argument Exception");
        } catch (NullArgumentException e){
            //expected
        }
    }

    @Test
    public void testGetStatus(){
        Task testTask = new Task("describing");
        Status s1 = Status.IN_PROGRESS;
        testTask.setStatus(s1);
        assertEquals(s1.toString(),testTask.getStatus().toString());
    }

    @Test
    public void testGetStatusTODO(){
        Task testTask = new Task("describing");
        Status s1 = Status.TODO;
        testTask.setStatus(s1);
        assertEquals(s1.toString(),testTask.getStatus().toString());
    }


    @Test
    public void testSetStatus(){
        Task testTask = new Task("describing");
        Status s1 = Status.UP_NEXT;
        testTask.setStatus(s1);
        assertEquals(s1.toString(),testTask.getStatus().toString());
        assertEquals( "UP NEXT",s1.getDescription());
    }

    @Test
    public void testSetStatusNull(){
        try {
            Task testTask = new Task("describing");
            testTask.setStatus(null);
            fail("Should have thrown Null Argument Exception");
        } catch (NullArgumentException e){
            //expected
        }
    }

    @Test
    public void testSetStatusINPROG(){
        Task testTask = new Task("describing");
        Status s1 = Status.IN_PROGRESS;
        testTask.setStatus(s1);
        assertEquals(s1.toString(),testTask.getStatus().toString());
        assertEquals( "IN PROGRESS",s1.getDescription());
    }

    @Test
    public void testGetDescription(){
        Task testTask = new Task("describing");
        assertEquals("describing", testTask.getDescription());

    }

    @Test
    public void testSetDescription(){
        Task testTask = new Task("describing");
        testTask.setDescription("more talk");
        assertEquals("more talk", testTask.getDescription());
    }

    @Test
    public void testSetDescriptionParsing(){
        Task testTask = new Task("describing");
        testTask.setDescription("more talk##hello");
        assertEquals("more talk", testTask.getDescription());
    }

    @Test
    public void testSetDescriptionParsing2(){
        Task testTask = new Task("describing");
        testTask.setDescription("more talk##hello;hi");
        assertEquals("more talk", testTask.getDescription());
    }


    @Test
    public void testSetDescriptionParsing3(){
        Task testTask = new Task("describing");
        testTask.setDescription("more talk##hello;hi;hiAgain");
        assertEquals("more talk", testTask.getDescription());
    }

    @Test
    public void testSetDescriptionThrowNull(){
        try {
            Task testTask = new Task("describing");
            testTask.setDescription(null);
            fail("Cannot change description to null");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testSetDescriptionThrowEmpty(){
        try {
            Task testTask = new Task("describing");
            testTask.setDescription("");
            fail("Cannot change description to null");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testSetDueDate(){
        Task testTask = new Task("describing");
        Calendar myCal1 = Calendar.getInstance();
        DueDate testDue = new DueDate(myCal1.getTime());
        testTask.setDueDate(testDue);
        assertEquals(testDue, testTask.getDueDate());
    }

    @Test
    public void testContainsTag(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        assertEquals(1, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        testTask.addTag("taggiest");
        assertEquals(2, testTask.getTags().size());
        assertTrue(testTask.containsTag("taggy tag"));
        assertFalse(testTask.containsTag("not here"));
    }

    @Test
    public void testNewTagException() {
        try {
            Task testTask = new Task("hi");
            testTask.addTag((String) null);
        } catch (EmptyStringException e) {
            //expected
        }
        try {
            Task testTask = new Task("hi");
            testTask.addTag((Tag) null);
        } catch (NullArgumentException e) {
            //expected
        }
        try {
            Task testTask = new Task("hi");
            testTask.addTag("");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testContainsTagException() {
        try {
            Task testTask = new Task("hi");
            testTask.addTag("beep boop");
            testTask.containsTag((String) null);
        } catch (EmptyStringException e) {
            //expected
        }
        try {
            Task testTask = new Task("hi");
            testTask.containsTag((Tag) null);
        } catch (NullArgumentException e) {
            //expected
        }
        try {
            Task testTask = new Task("hi");
            testTask.containsTag("");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testToString(){
        Task testTask = new Task("describing");
        testTask.addTag("taggy tag");
        testTask.addTag("taggiest");
        System.out.println(testTask.toString());
        assertEquals("\n" +
                "{\n" +
                "\tDescription: describing\n" +
                "\tDue date: \n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #taggy tag, #taggiest\n" +
                "}", testTask.toString());
    }

    @Test
    public void testToStringNoTag(){
        Task testTask = new Task("describing");
        System.out.println(testTask.toString());
        assertEquals("\n" +
                "{\n" +
                "\tDescription: describing\n" +
                "\tDue date: \n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags:  \n" +
                "}", testTask.toString());
    }


    @Test
    public void testSetProgress() {
        Task t1 = new Task("t1");
        t1.setProgress(50);
        assertEquals(50, t1.getProgress());
        t1.setEstimatedTimeToComplete(30);
    }
    @Test
    public void testInvalidProjectTooLow(){
        Task t1 = new Task("t1");
        try {
            t1.setProgress(-1);
            fail("Should have thrown InvalidProgressException");
        } catch(InvalidProgressException e){
            // expected
        }
    }

    @Test
    public void testInvalidProjectTooHigh(){
        Task t1 = new Task("t1");
        try {
            t1.setProgress(101);
            fail("Should have thrown InvalidProgressException");
        } catch(InvalidProgressException e){
            // expected
        }
    }

    @Test
    public void testSetETC() {
        Task t1 = new Task("t1");
        t1.setProgress(50);
        t1.setEstimatedTimeToComplete(30);
        assertEquals(30, t1.getEstimatedTimeToComplete());
    }

    @Test
    public void testInvalidETC(){
        Task t1 = new Task("t1");
        try {
            t1.setEstimatedTimeToComplete(-40);
            fail("Should have thrown NegativeInputException");
        } catch(NegativeInputException e){
            // expected
        }
    }













}