package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        if (description == null || description.length() == 0) {
            throw new EmptyStringException("Cannot construct a project with no description");
        }
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!this.equals(task) && !contains(task)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns the estimated time to complete project
    @Override
    public int getEstimatedTimeToComplete() {
        Integer est = 0;
        for (Todo t : tasks) {
            Integer timeToAdd = t.getEstimatedTimeToComplete();
            est = est + timeToAdd;
        }
        return est;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        Integer countTask = 0;
        Integer totalTasks = 0;
        Integer totalProject = 0;
        Integer countProject = 1;
        for (Todo t : tasks) {
            if (t.getClass().isInstance(new Task("compare"))) {
                countTask++;
                totalTasks = totalTask(t, totalTasks);
            }
            if (t.getClass().isInstance(new Project("compare"))) {
                countProject++;
                totalProject = t.getProgress();
            }
        }
        if (countTask != 0) {
            return someTasks(countTask, totalTasks, totalProject, countProject);
        }
        return 0;
    }

    private int someTasks(Integer countTask, Integer totalTasks, Integer totalProject, Integer countProject) {
        Integer avgerageThisProject = totalTasks / countTask;
        if (countProject == 0) {
            return avgerageThisProject;
        }
        Integer totalAvg = totalProject + avgerageThisProject;
        return totalAvg / countProject;
    }


    private Integer totalTask(Todo t, Integer totalTasks) {
        totalTasks += t.getProgress();
        return totalTasks;
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task in this project is completed, and false otherwise
    //     If this project has no tasks, return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    //EFFECTS: Iterator to iterate in first over priority 1 objects, then priority 2, then priority 3,
    //        and then priority 4
    @Override
    public Iterator<Todo> iterator() {
        return new PriorityIterator();
    }


    private class PriorityIterator implements Iterator<Todo> {
        int p1Index;
        int p2Index;
        int p3Index;
        int p4Index;
        int totalItemsIteratedOver;

        PriorityIterator() {
            p1Index = 0;
            p2Index = 0;
            p3Index = 0;
            p4Index = 0;
            totalItemsIteratedOver = 0;
        }

        @Override
        public boolean hasNext() {
            Integer total = totalItemsIteratedOver;
            return total < tasks.size();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Todo currentTodo = checkSizesP1P2();
            if (currentTodo != null) {
                return currentTodo;
            }
            Todo currentTodo2 = checkSizesP3P4();
            if (currentTodo2 != null) {
                return currentTodo2;
            }
            return null;
        }

        private Todo checkSizesP1P2() {
            if (p1Index < tasks.size()) {
                Todo currentTodo = searchForP1Matches();
                if (currentTodo != null) {
                    return currentTodo;
                }
            }
            if (p2Index < tasks.size()) {
                Todo currentTodo = searchForP2Matches();
                if (currentTodo != null) {
                    return currentTodo;
                }
            }
            return null;
        }

        private Todo checkSizesP3P4() {
            if (p3Index < tasks.size()) {
                Todo currentTodo = searchForP3Matches();
                if (currentTodo != null) {
                    return currentTodo;
                }
            }
            if (p4Index < tasks.size()) {
                Todo currentTodo = searchForP4Matches();
                if (currentTodo != null) {
                    return currentTodo;
                }
            }
            return null;
        }

        private Todo searchForP1Matches() {
            for (int i = p1Index; i < tasks.size(); i++) {
                Todo currentTodo = tasks.get(i);
                p1Index++;
                if (currentTodo.getPriority().equals(new Priority(1))) {
                    totalItemsIteratedOver++;
                    return currentTodo;
                }
            }
            return null;
        }

        private Todo searchForP2Matches() {
            for (int i = p2Index; i < tasks.size(); i++) {
                Todo currentTodo = tasks.get(i);
                p2Index++;
                if (currentTodo.getPriority().equals(new Priority(2))) {
                    totalItemsIteratedOver++;
                    return currentTodo;
                }
            }
            return null;
        }

        private Todo searchForP3Matches() {
            for (int i = p3Index; i < tasks.size(); i++) {
                Todo currentTodo = tasks.get(i);
                p3Index++;
                if (currentTodo.getPriority().equals(new Priority(3))) {
                    totalItemsIteratedOver++;
                    return currentTodo;
                }
            }
            return null;
        }

        private Todo searchForP4Matches() {
            for (int i = p4Index; i < tasks.size(); i++) {
                Todo currentTodo = tasks.get(i);
                p4Index++;
                if (currentTodo.getPriority().equals(new Priority(4))) {
                    totalItemsIteratedOver++;
                    return currentTodo;
                }
            }
            return null;
        }
    }
}