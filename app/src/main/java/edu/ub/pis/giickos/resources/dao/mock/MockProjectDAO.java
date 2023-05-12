package edu.ub.pis.giickos.resources.dao.mock;

import java.time.LocalDateTime;
import java.util.UUID;

import edu.ub.pis.giickos.Utils;
import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.resources.dao.CachedProjectDAO;

// Implementation of the DAOProject using mock data
public class MockProjectDAO extends CachedProjectDAO {

    public MockProjectDAO() {
        super();
    }

    // Creates mock projects and tasks.
    private void addMockData() {
        Project project1 = new Project(UUID.randomUUID(), "Test Project 1");
        Project project2 = new Project(UUID.randomUUID(), "Test Project 2");
        Project project3 = new Project(UUID.randomUUID(), "Empty Test Project");
        Task task1 = new Task(UUID.randomUUID().toString(), "Task 1");
        Task task2 = new Task(UUID.randomUUID().toString(), "Task 2");
        Task task3 = new Task(UUID.randomUUID().toString(), "Task 3");

        // Set dummy tasks to begin today
        LocalDateTime now = LocalDateTime.now();
        task1.setStartTime(Utils.localDateTimeToUTC(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 1, 0)).toInstant().toEpochMilli());
        task2.setStartTime(Utils.localDateTimeToUTC(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 21, 0)).toInstant().toEpochMilli());
        task3.setStartTime(Utils.localDateTimeToUTC(LocalDateTime.now()).toInstant().toEpochMilli());

        task1.setDuration(60);
        task2.setDuration(30);
        task3.setDuration(120);

        addProject(project1);
        addTask(project1.getId(), task1);
        addTask(project1.getId(), task2);

        addProject(project2);
        addTask(project2.getId(), task3);

        // Test project with no tasks
        addProject(project3);
    }

    @Override
    public void loadDataForUser(User user, DataLoadedListener listener) {
        projects.clear();
        tasks.clear();

        addMockData();
        listener.onLoad(true);
    }
}
