package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookmarkDTO;
import dtos.MealPlanDTO;
import dtos.SingleRecipeDTO;
import entities.MealPlan;
import entities.Recipe;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class RecipeFacadeTest
{

    private static EntityManagerFactory emf;
    private static RecipeFacade facade;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    User user1, user2;
    MealPlanDTO mealPlan;
    BookmarkDTO bookmark;
    Role userRole;
    LocalDate date;
    Recipe recipe;
    SingleRecipeDTO singleRecipe;

    public RecipeFacadeTest()
    {
    }

    @BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RecipeFacade.getRecipeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass()
    {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp()
    {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("MealPlan.deleteAllRows").executeUpdate();
            em.createNamedQuery("Bookmark.deleteAllRows").executeUpdate();
            em.createNamedQuery("Rating.deleteAllRows").executeUpdate();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
            em.getTransaction().begin();
            user1 = new User("Bo Bobsen", "test123");
            user2 = new User("Ib Ibsen", "test123");
            date = LocalDate.now();
            String jsonRecipe = facade.fetchSingleRecipe("666959");
            singleRecipe = GSON.fromJson(jsonRecipe, SingleRecipeDTO.class);

            if (user1.getUserPass().equals("test"))
                throw new UnsupportedOperationException("You have not changed the password");


            userRole = new Role("user");
            user1.addRole(userRole);
            user2.addRole(userRole);
            em.persist(user1);
            em.persist(user2);
            em.persist(userRole);
            try {
                recipe = em.find(Recipe.class, singleRecipe.getId());
                if (recipe == null) {
                    recipe = new Recipe(singleRecipe.getId(), singleRecipe.toString());
                    em.persist(recipe);
                }
            } finally {
                em.getTransaction().commit();
                em.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    public void tearDown()
    {
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
    @Disabled
    void getAndAddMealPlansToAUser() throws API_Exception {
        // We could/should have split this test into 2 parts? An add and a get part.
        // checking to see the list is empty
        List<MealPlanDTO> actual = facade.getAllMealPlans(user1.getUserName());
        assertEquals(0, actual.size());
        // adding a mealPlan to a user
        mealPlan = new MealPlanDTO(user1.getUserName(), singleRecipe.getId(), "BREAKFAST", date);
        actual.add(facade.addMealPlan(mealPlan));
        // checking that the mealPlanList-size has increased
        assertEquals(1, actual.size());
        assertEquals(mealPlan, actual.get(0));
        //Checking that it indeed is Bo Bobsen's mealPlan we are looking at.
        for (MealPlanDTO mealPlanDTO : actual) {
            String expected = mealPlanDTO.getUserName();
            assertEquals(user1.getUserName(), expected); // I think this is the "more" right way to do it
            // assertEquals("Bo Bobsen",userName);      // This should be the "less" right way to do it
        }
    }

    @Test
    void complexSearch()
    {
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

    @Test
    @Disabled
    void AddBookmarkToAUser()
    {
        // checking to see the list is empty
        List<BookmarkDTO> actual = facade.getBookmarks(user1.getUserName());
        assertEquals(0,actual.size());
        // adding a bookmark to a user
        bookmark = facade.addBookmark(new BookmarkDTO(user1.getUserName(), recipe.getId()));
        actual.add(bookmark);
        // check if bookmark has been added to Bo Bobsen
        assertEquals(1,actual.size());
        assertEquals(actual.get(0).getRecipeId(), recipe.getId());
        assertEquals(actual.get(0).getUsername(), user1.getUserName());
    }

}
