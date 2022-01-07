package generalTests;

import edu.touro.mco152.bm.persist.EM;
import jakarta.persistence.EntityManager;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class TestEM {

    static long initialDuration;
    static EntityManager em;

    /**
     * This method tests for TIME in CORRECT and Performance in RIGHT-BICEP. It checks to see that when an instance of the singleton class
     * EntityManager does not exist, it should take longer than a second to create. The time comes from the getEM method which is run before all tests.
     */
    @Test
    public void testEMDoesNotExistAlready(){
        Assertions.assertTrue(initialDuration>1000);
    }

    /**
     * This method tests for TIME in CORRECT and Performance in RIGHT-BICEP. It checks to see that when an instance of the singleton class
     * EntityManager does exist, it should take less than a second to create.
     */
    @Test
    public void testEMExistsAlready(){
        long startTime = System.currentTimeMillis();
        EM.getEntityManager();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        Assertions.assertTrue(duration<initialDuration);
    }

    /**
     * This method tests that EM returns the SAME instance of the EM class because it is a singleton.
     */
    @Test
    public void testSingleton(){
        Assertions.assertEquals(em, EM.getEntityManager());
    }

    /**
     * This is an Arrange for all test methods. It times the initial duration of creating the EntityManager and initializes a stati variable.
     */

    @BeforeAll
    public static void getEM(){
        long startTime = System.currentTimeMillis();
        em=EM.getEntityManager();
        long endTime = System.currentTimeMillis();
        initialDuration = endTime - startTime;
    }


}
