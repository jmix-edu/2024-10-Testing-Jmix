package com.company.jmixpmflowbase.integration;


import com.company.jmixpmflowbase.JmixpmFlowBaseApplication;
import com.company.jmixpmflowbase.app.TaskService;
import com.company.jmixpmflowbase.entity.Project;
import com.company.jmixpmflowbase.entity.Task;
import com.company.jmixpmflowbase.entity.User;
import com.company.jmixpmflowbase.test_support.extension.PostgreSqlExtension;
import com.company.jmixpmflowbase.test_support.initialyzer.TestContextInitializer;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith({SpringExtension.class, PostgreSqlExtension.class})
@ContextConfiguration(classes = JmixpmFlowBaseApplication.class, initializers = TestContextInitializer.class)
@TestPropertySource("classpath:application.properties")
public class DBIntegrationTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private SystemAuthenticator systemAuthenticator;

    @Test
    @DisplayName("Checks the computation of the least busy user")
    void checkLeastBusyUser() {
        // create test data
        User user1 = dataManager.create(User.class);
        user1.setUsername("user1");
        User user2 = dataManager.create(User.class);
        user2.setUsername("user2");

        Project project = dataManager.create(Project.class);
        project.setName("DB Integration test");
        project.setManager(user1);

        Task task1 = dataManager.create(Task.class);
        task1.setName("Write integration test");
        task1.setAssignee(user1);
        task1.setProject(project);
        task1.setEstimatedEfforts(5);

        Task task2 = dataManager.create(Task.class);
        task2.setName("Write documentation");
        task2.setAssignee(user2);
        task2.setProject(project);
        task2.setEstimatedEfforts(7);

        systemAuthenticator.runWithSystem(() -> {
            // save test data
            dataManager.save(user1, user2, project, task1, task2);
            // Assertion
            Assertions.assertEquals(user1, taskService.findLeastBusyUser());
        });
    }

}
