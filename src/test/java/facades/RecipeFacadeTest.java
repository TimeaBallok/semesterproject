package facades;

import entities.Role;
import entities.User;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class RecipeFacadeTest
{

    private static EntityManagerFactory emf;
    private static RecipeFacade facade;

    User user;
    Role userRole;

    public RecipeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RecipeFacade.getRecipeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
//            em.createNamedQuery("User.deleteAllRows").executeUpdate();
//            user = new User("Bo Bobsen", "test1");
//
//            if(user.getUserPass().equals("test"))
//                throw new UnsupportedOperationException("You have not changed the password");
//
//            userRole = new Role("user");
//            user.addRole(userRole);
//            em.persist(user);
//            em.getTransaction().commit();
//
//        } finally {
//            em.close();
//        }
    }

    @AfterEach
    public void tearDown() {
////        Remove any data after each test was run
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            //Delete existing users and roles to get a "fresh" database
//            em.createQuery("delete from User").executeUpdate();
//            em.createQuery("delete from Role").executeUpdate();
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
    }

    // TODO: Delete or change this method
//    @Disabled


    @Test
    void complexSearch() {
        List<String> expected = new ArrayList<>();
        //region stupid long text
        expected.add("{" +
                "\"results\":[" +
                "{" +
                "\"id\":647661," +
                "\"title\":\"Hungarian Potato Soup\"," +
                "\"image\":\"https://spoonacular.com/recipeImages/647661-312x231.jpg\"," +
                "\"imageType\":\"jpg\"" +
                "}," +
                "{" +
                "\"id\":647645," +
                "\"title\":\"Hungarian Beef Goulash\"," +
                "\"image\":\"https://spoonacular.com/recipeImages/647645-312x231.jpg\"," +
                "\"imageType\":\"jpg\"" +
                "}," +
                "{" +
                "\"id\":647656," +
                "\"title\":\"Hungarian Goulash Soup\"," +
                "\"image\":\"https://spoonacular.com/recipeImages/647656-312x231.jpg\"," +
                "\"imageType\":\"jpg\"" +
                "}," +
                "{" +
                "\"id\":647659," +
                "\"title\":\"Hungarian Plum Dumplings\"," +
                "\"image\":\"https://spoonacular.com/recipeImages/647659-312x231.jpg\"," +
                "\"imageType\":\"jpg\"" +
                "}," +
                "{" +
                "\"id\":647654," +
                "\"title\":\"Hungarian Cottage-Cheese Biscuits (Túrós Pogácsa)\"," +
                "\"image\":\"https://spoonacular.com/recipeImages/647654-312x231.jpg\"," +
                "\"imageType\":\"jpg\"" +
                "}" +
                "]," +
                "\"offset\":0," +
                "\"number\":10," +
                "\"totalResults\":5" +
                "}");
        //endregion
        List<String> actual = facade.complexSearch("Hungarian");
        assertEquals(expected, actual);
    }
}
