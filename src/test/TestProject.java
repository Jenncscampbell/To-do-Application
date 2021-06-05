import model.*;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {
    Project p1;
    Task testTask;
    Task testTask2;
    Task testTask3;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;



    @BeforeEach
    public void SetUp() {
        p1 = new Project("hi");
        testTask = new Task("hello world");
        testTask2 = new Task("hello worlds");
        testTask3 = new Task("hello hello");
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

    }

    @Test
    public void testProject(){
        assertEquals(0, p1.getNumberOfTasks());
        assertEquals("hi", p1.getDescription());
        assertEquals(0, p1.getProgress());
        assertEquals(0, p1.getEstimatedTimeToComplete());
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
        assertEquals(2, p1.getNumberOfTasks());
        p1.add(testTask);
        assertEquals(2, p1.getNumberOfTasks());
        p1.add(p1);
        assertEquals(2, p1.getNumberOfTasks());
        p1.add(testTask2);
        assertEquals(2, p1.getNumberOfTasks());
        Project p2 = new Project("p2");
        p1.add(p2);
        assertEquals(3, p1.getNumberOfTasks());

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
        assertEquals(2, p1.getNumberOfTasks());
        p1.remove(testTask);
        assertEquals(1, p1.getNumberOfTasks());
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
        assertEquals(2, p1.getNumberOfTasks());
        p1.remove(testTask);
        assertEquals(1, p1.getNumberOfTasks());
        p1.remove(testTask2);
        assertEquals(0, p1.getNumberOfTasks());
    }

    @Test
    public void testRemoveNONE(){
        p1.remove(testTask);
        assertEquals(0, p1.getNumberOfTasks());
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
        assertEquals(2, p1.getNumberOfTasks());
        assertTrue(p1.contains(testTask));
    }

    @Test
    public void testGetProgressNoTasks(){
        Project p1 = new Project("hi");
        assertEquals(0, p1.getProgress());
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
    public void testGetNumberOfTasks(){
        p1.add(testTask);
        p1.add(testTask2);
        assertEquals(2, p1.getNumberOfTasks());
        Project p2 = new Project("p2");
        p1.add(p2);
        p2.add(new Task("t4"));
        assertEquals(3, p1.getNumberOfTasks());

    }

    @Test

    public void testGetNumberOfTasksNone(){
        assertEquals(0, p1.getNumberOfTasks());
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



    @Test
    public void testAddRemoveProjects(){
        Project p0 = new Project("Mama");
        Project p1 = new Project("baby");
        Project p2 = new Project("spare baby");
        p0.add(p1);
        p0.add(p2);
        assertTrue(p0.contains(p1));
        assertEquals(2,p0.getNumberOfTasks());
        p0.remove(p1);
        assertFalse(p0.contains(p1));
    }

    @Test
    public void testAddRemoveTasks(){
        Project p0 = new Project("Mama");
        Task t1 = new Task("baby");
        Task t2 = new Task("spare baby");
        Project p2 = new Project("spare baby");
        p0.add(t1);
        p0.add(t2);
        p0.add(p2);
        assertTrue(p0.contains(t2));
        assertEquals(3,p0.getNumberOfTasks());
        p0.remove(t1);
        assertFalse(p0.contains(t1));
    }


    @Test
    public void testGetETCTasks(){
        Project p0 = new Project("testing");
        assertEquals(0, p0.getEstimatedTimeToComplete());
        Task t1 = new Task("t1");
        t1.setEstimatedTimeToComplete(30);
        t1.setProgress(50);
        p0.add(t1);
        assertEquals(30, p0.getEstimatedTimeToComplete());
        assertEquals(50, p0.getProgress());
        Project p1 = new Project("test2");
        Task t2 = new Task("t2");
        t2.setProgress(0);
        t2.setEstimatedTimeToComplete(100);
        p1.add(t2);
        p0.add(p1);

    }





    @Test
    public void testGetProgress() {
        assertEquals(0, p1.getProgress());
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        p1.add(t1);
        p1.add(t2);
        p1.add(t3);
        assertEquals(0, p1.getProgress());
        t1.setProgress(100);
        p1.getProgress();
        assertEquals(33, p1.getProgress());
        t2.setProgress(50);
        t3.setProgress(25);
        assertEquals(58, p1.getProgress());
        Project p2 = new Project("p2");
        p1.add(p2);
        assertEquals(29, p1.getProgress());
        Project p3 = new Project("p3");
        p3.add(t3);
        p1.add(p3);
        p1.getProgress();
        assertEquals(27, p1.getProgress());
    }

    @Test
    public void testIsCompleted() {
        assertFalse(p1.isCompleted());
        Task t1 = new Task("hi");
        p1.add(t1);
        assertFalse(p1.isCompleted());
        t1.setProgress(50);
        assertFalse(p1.isCompleted());
        t1.setProgress(100);
        assertTrue(p1.isCompleted());

    }

    @Test
    public void testGetEstimatedTimeToComplete() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        p1.add(t1);
        p1.add(t2);
        p1.add(t3);
        assertEquals(0, p1.getEstimatedTimeToComplete());
        t1.setEstimatedTimeToComplete(8);
        t2.setEstimatedTimeToComplete(2);
        t3.setEstimatedTimeToComplete(10);
        assertEquals(20, p1.getEstimatedTimeToComplete());
        Project p2 = new Project("p2");
        Task t4 = new Task("t4");
        p2.add(t4);
        t4.setEstimatedTimeToComplete(4);
        p1.add(p2);
        assertEquals(24, p1.getEstimatedTimeToComplete());
    }

    @Test
    public void iteratorManyProjectsManyTasks() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        Task t4 = new Task("t4");
        Task t5 = new Task("t5");
        t1.setPriority(new Priority(1));
        t2.setPriority(new Priority(2));
        t3.setPriority(new Priority(3));
        t4.setPriority(new Priority(4));
        Project p0 = new Project("hi");
        Project p1 = new Project("p1");
        p1.setPriority(new Priority(1));
        p0.add(t3);
        p1.add(t5); // should not be on list
        p0.add(p1);
        p0.add(t2);
        p0.add(t4);
        p0.add(t1);
        for (Todo t : p0) {
            System.out.println(t.getDescription());
        }
        assertEquals("p1\n" +
                "t1\n" +
                "t2\n" +
                "t3\n" +
                "t4\n", outContent.toString());

    }

     @Test
    public void testIteratorOneProject() {
         Project p0 = new Project("hi");
         Project p1 = new Project("p1");
         p1.setPriority(new Priority(4));
         p0.add(p1);
         for (Todo t : p0) {
             System.out.println(t.getDescription());
         }
         assertEquals("p1\n", outContent.toString());
     }

    @Test
    public void testIteratorOneProjectHigh() {
        Project p0 = new Project("hi");
        Project p1 = new Project("p1");
        p1.setPriority(new Priority(1));
        p0.add(p1);
        for (Todo t : p0) {
            System.out.println(t.getDescription());
        }
        assertEquals("p1\n", outContent.toString());
    }

    @Test
    public void testIteratorFewTasks() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        Task t4 = new Task("t4");
        t1.setPriority(new Priority(1));
        t2.setPriority(new Priority(2));
        t3.setPriority(new Priority(3));
        t4.setPriority(new Priority(4));
        Project p0 = new Project("hi");
        p0.add(t3);
        p0.add(t2);
        p0.add(t4);
        p0.add(t1);
        for (Todo t : p0) {
            System.out.println(t.getDescription());
        }
        assertEquals("t1\n" +
                "t2\n" +
                "t3\n" +
                "t4\n", outContent.toString());
        Iterator projectIter = p0.iterator();
        assertEquals(t1, projectIter.next());
        assertEquals(t2, projectIter.next());
        assertEquals(t3, projectIter.next());
        assertEquals(t4, projectIter.next());
        try {
            projectIter.next();
            fail();
        } catch (NoSuchElementException e) {
            //expected
        }
    }

     @Test
     public void testIteratorManyProjects() {
         Project p0 = new Project("hi");
         Project p1 = new Project("p1");
         Project p3 = new Project("p3");
         p3.setPriority(new Priority(3));
         p1.setPriority(new Priority(1));
         p0.add(p3);
         p0.add(p1);
         for (Todo t : p0) { System.out.println(t.getDescription());
         }
         assertEquals("p1\n" +
                 "p3\n", outContent.toString());
     }

     @Test
     void testIteratorZeroProjects() {
        Project p1 = new Project("some project");
        for (Todo t : p1) {
            System.out.println("Should not print anything");
            fail();
        }
     }

     @Test
     void _testIteratorOneProject() {
        Project p1 = new Project("Please throw Exception");
        Todo t = new Task("Some task");
        t.setPriority(new Priority(1));
        p1.add(t);
        Iterator projectIter = p1.iterator();
        assertEquals(t, projectIter.next());
        assertFalse(projectIter.hasNext());
        assertThrows(NoSuchElementException.class, () -> {
            projectIter.next();
        });
     }

     @Test
     void testIteratorAllPriorities() {
        Project p1 = new Project("Please throw an exception");
        Project sub = new Project("subproject");
        sub.setPriority(new Priority(4));
        p1.add(sub);
        Task t = new Task("some task");
        t.setPriority(new Priority(1));
        p1.add(t);
        Iterator projectIter = p1.iterator();
        assertEquals(t, projectIter.next());
        assertEquals(sub, projectIter.next());
        assertThrows(NoSuchElementException.class, () -> {
             projectIter.next();
        });
        assertFalse(projectIter.hasNext());
    }


}