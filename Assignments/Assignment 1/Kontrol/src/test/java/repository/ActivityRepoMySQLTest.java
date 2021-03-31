package repository;

import launcher.ContainerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import repository.activity.ActivityRepository;

public class ActivityRepoMySQLTest {
    private static ActivityRepository activityRepository;

    @BeforeClass
    public static void setupClass() {
        ContainerFactory containerFactory = ContainerFactory.instance(true);

        activityRepository = containerFactory.getActivityRepository();
    }

    @Before
    public void cleanUp() {
        activityRepository.removeAll();
    }

}
