package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {
    Project p1;
    Task testTask;
    Task testTask2;
    Task testTask3;



    @BeforeEach
    public void SetUp() {
        p1 = new Project("hi");
        testTask = new Task("hello world");
        testTask2 = new Task("hello worlds");
        testTask3 = new Task("hello hello");
    }

    @Test
    public void testProject(){
        assertEquals(0, p1.getTasks().size());
        assertEquals("hi", p1.getDescription());
    }

    @Test
    public void testThrowsNullString(){
        try {
            p1 = new Project(null);
            fail("Failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    public void testThrowsEmptyString(){
        try {
            p1 = new Project("");
            fail("Failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }




    @Test
    public void testAdd(){
        p1.add(testTask);
        p1.add(testTask2);
        assertEquals(2, p1.getTasks().size());
        p1.add(testTask);
        assertEquals(2, p1.getTasks().size());
        p1.add(testTask2);
        assertEquals(2, p1.getTasks().size());

    }

    @Test
    public void testAddNull(){
        try {
            p1.add(null);
            fail("Should have thrown Null Argument Exception");
        } catch (NullArgumentException e) {
            //expected
        }

    }

    @Test
    public void testRemove(){
        p1.add(testTask);
        p1.add(testTask2);
        assertEquals(2, p1.getTasks().size());
        p1.remove(testTask);
        assertEquals(1, p1.getTasks().size());
    }

    @Test
    public void testRemoveNull(){
        try {
            p1.remove(null);
            fail("Should have thrown Null Argument Exception");
        } catch (NullArgumentException e) {
            //expected
        }

    }

    @Test
    public void testRemoveTwo(){
        p1.add(testTask);
        p1.add(testTask2);
        assertEquals(2, p1.getTasks().size());
        p1.remove(testTask);
        assertEquals(1, p1.getTasks().size());
        p1.remove(testTask2);
        assertEquals(0, p1.getTasks().size());
    }

    @Test
    public void testRemoveNONE(){
        p1.remove(testTask);
        assertEquals(0, p1.getTasks().size());
    }

    @Test
    public void testGetDescription(){
        Project p1 = new Project("hi");
        assertEquals("hi", p1.getDescription());
    }

    @Test
    public void testGetTasks(){
        p1.add(testTask);
        p1.add(testTask2);
        assertEquals(2, p1.getTasks().size());
        assertTrue(p1.getTasks().contains(testTask));
    }

    @Test
    public void testGetProgressNoTasks(){
        Project p1 = new Project("hi");
        assertEquals(100, p1.getProgress());
    }

    @Test
    public void testGetProgressNoneDone(){
        testTask.setStatus(Status.TODO);
        testTask2.setStatus(Status.TODO);
        testTask2.setStatus(Status.UP_NEXT);
        p1.add(testTask);
        p1.add(testTask2);
        p1.add(testTask3);
        assertEquals(0, p1.getProgress());
    }
    @Test
    public void testGetProgressSomeDone(){
        testTask.setStatus(Status.TODO);
        testTask2.setStatus(Status.DONE);
        testTask3.setStatus(Status.UP_NEXT);
        p1.add(testTask);
        p1.add(testTask2);
        p1.add(testTask3);
        assertEquals(33, p1.getProgress());
    }

    @Test
    public void testGetProgressMostDone(){
        testTask.setStatus(Status.TODO);
        testTask2.setStatus(Status.DONE);
        testTask3.setStatus(Status.DONE);
        p1.add(testTask);
        p1.add(testTask2);
        p1.add(testTask3);
        assertEquals(66, p1.getProgress());
    }

    @Test
    public void testGetProgressAllDone(){
        testTask.setStatus(Status.DONE);
        testTask2.setStatus(Status.DONE);
        testTask3.setStatus(Status.DONE);
        p1.add(testTask);
        p1.add(testTask2);
        p1.add(testTask3);
        assertEquals(100, p1.getProgress());
    }

    @Test
    public void testGetNumberOfTasks(){
        p1.add(testTask);
        p1.add(testTask2);
        assertEquals(2, p1.getNumberOfTasks());
    }

    @Test

    public void testGetNumberOfTasksNone(){
        assertEquals(0, p1.getNumberOfTasks());
    }

    @Test
    public void testIsComplete(){
        testTask.setStatus(Status.DONE);
        p1.add(testTask);
        assertTrue(p1.isCompleted());
    }

    @Test
    public void testIsNotComplete(){
        testTask.setStatus(Status.TODO);
        p1.add(testTask);
        assertFalse(p1.isCompleted());
    }

    @Test
    public void testIsPartComplete(){
        testTask.setStatus(Status.DONE);
        p1.add(testTask);
        testTask2.setStatus(Status.UP_NEXT);
        p1.add(testTask2);
        assertFalse(p1.isCompleted());
    }

    @Test
    public void testContains(){
        p1.add(testTask);
        p1.add(testTask2);
        assertTrue(p1.contains(testTask));
    }

    @Test
    public void testContainsNull(){
        try {
            p1.contains(null);
            fail("Should have thrown Null Argument Exception");
        } catch (NullArgumentException e) {
            //expected
        }
    }



}