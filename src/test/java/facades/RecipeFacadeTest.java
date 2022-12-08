package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MealPlanDTO;
import dtos.SingleRecipeDTO;
import entities.MealPlan;
import entities.Recipe;
import entities.Role;
import entities.User;
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
//@Disabled
public class RecipeFacadeTest
{

    private static EntityManagerFactory emf;
    private static RecipeFacade facade;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    User user1, user2;
    MealPlanDTO mealPlan;
    List<MealPlanDTO> mealPlanDTOList = new ArrayList<>();
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
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("MealPlan.deleteAllRows").executeUpdate();
            em.createNamedQuery("Bookmark.deleteAllRows").executeUpdate();
            em.createNamedQuery("Rating.deleteAllRows").executeUpdate();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();
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
                // facade.addMealPlan(mealPlan);
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
    void getMealPlansByUN()
    {
        List<MealPlanDTO> actual = facade.getAllMealPlans(user1.getUserName());
        assertEquals(0, actual.size());
        // assertThat(actual, containsInAnyOrder(new PersonDTO(p1), new PersonDTO(p2)));
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
    void addMealPlanToDB()
    {
        List<MealPlanDTO> expected = mealPlanDTOList;
        Recipe newRecipe = new Recipe(664959,
                //region recipe
                "'{\"extendedIngredients\":[{\"id\":1034053, \"nameClean\":\"extra virgin olive oil\", \"amount\":3.0, \"unit\":\"tablespoons\"}, {\"id\":1102047, \"nameClean\":\"salt and pepper\", \"amount\":4.0, \"unit\":\"servings\"}, {\"id\":5006, \"nameClean\":\"whole chicken\", \"amount\":1.0, \"unit\":\"\"}], \"id\":666959, \"title\":\"The Minimalist: Simplest Roast Chicken\", \"readyInMinutes\":45, \"servings\":4, \"image\":\"https://spoonacular.com/recipeImages/666959-556x370.jpg\", \"diets\":[gluten free, dairy free, paleolithic, primal, fodmap friendly, whole 30], \"analyzedInstructions\":[{\"name\":\"\", \"steps\":[{\"number\":1, \"step\":\"Put a cast-iron skillet on a low rack in the oven and heat the oven to 500 degrees. Rub the chicken all over with the oil and sprinkle it generously with salt and pepper.\"}, {\"number\":2, \"step\":\"When the oven and skillet are hot, carefully put the chicken in the skillet, breast side up. Roast for 15 minutes, then turn the oven temperature down to 350 degrees. Continue to roast until the bird is golden brown and an instant-read thermometer inserted into the meaty part of the thigh reads 155 to 165 degrees.\"}, {\"number\":3, \"step\":\"Tip the pan to let the juices flow from the chickens cavity into the pan.\"}, {\"number\":4, \"step\":\"Transfer the chicken to a platter and let it rest for at least 5 minutes. Carve and serve.Variations:Source: The New York Times\"}]}], \"nutrition\":{\"nutrients\":[{\"name\":\"Calories\", \"amount\":502.27, \"unit\":\"kcal\"}, {\"name\":\"Fat\", \"amount\":39.18, \"unit\":\"g\"}, {\"name\":\"Saturated Fat\", \"amount\":9.66, \"unit\":\"g\"}, {\"name\":\"Carbohydrates\", \"amount\":0.0, \"unit\":\"g\"}, {\"name\":\"Net Carbohydrates\", \"amount\":0.0, \"unit\":\"g\"}, {\"name\":\"Sugar\", \"amount\":0.0, \"unit\":\"g\"}, {\"name\":\"Cholesterol\", \"amount\":142.83, \"unit\":\"mg\"}, {\"name\":\"Sodium\", \"amount\":327.31, \"unit\":\"mg\"}, {\"name\":\"Protein\", \"amount\":35.42, \"unit\":\"g\"}, {\"name\":\"Vitamin B3\", \"amount\":12.95, \"unit\":\"mg\"}, {\"name\":\"Selenium\", \"amount\":27.42, \"unit\":\"Âµg\"}, {\"name\":\"Vitamin B6\", \"amount\":0.67, \"unit\":\"mg\"}, {\"name\":\"Phosphorus\", \"amount\":279.95, \"unit\":\"mg\"}, {\"name\":\"Vitamin B5\", \"amount\":1.73, \"unit\":\"mg\"}, {\"name\":\"Zinc\", \"amount\":2.5, \"unit\":\"mg\"}, {\"name\":\"Vitamin E\", \"amount\":2.08, \"unit\":\"mg\"}, {\"name\":\"Vitamin B2\", \"amount\":0.23, \"unit\":\"mg\"}, {\"name\":\"Potassium\", \"amount\":360.08, \"unit\":\"mg\"}, {\"name\":\"Iron\", \"amount\":1.77, \"unit\":\"mg\"}, {\"name\":\"Vitamin B12\", \"amount\":0.59, \"unit\":\"Âµg\"}, {\"name\":\"Magnesium\", \"amount\":38.09, \"unit\":\"mg\"}, {\"name\":\"Vitamin K\", \"amount\":9.18, \"unit\":\"Âµg\"}, {\"name\":\"Vitamin B1\", \"amount\":0.11, \"unit\":\"mg\"}, {\"name\":\"Vitamin A\", \"amount\":266.62, \"unit\":\"IU\"}, {\"name\":\"Copper\", \"amount\":0.09, \"unit\":\"mg\"}, {\"name\":\"Vitamin C\", \"amount\":3.05, \"unit\":\"mg\"}, {\"name\":\"Folate\", \"amount\":11.43, \"unit\":\"Âµg\"}, {\"name\":\"Vitamin D\", \"amount\":0.38, \"unit\":\"Âµg\"}, {\"name\":\"Calcium\", \"amount\":21.17, \"unit\":\"mg\"}, {\"name\":\"Manganese\", \"amount\":0.04, \"unit\":\"mg\"}]}}'");
        //endregion
        facade.addMealPlan(new MealPlanDTO(user1.getUserName(), newRecipe.getId(), "DINNER", date));
        Integer actual = expected.size();
        assertEquals(actual, 2);

    }
}
