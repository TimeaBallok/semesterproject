package facades;

import dtos.MealPlanDTO;
import dtos.RecipeDTO;
import dtos.RecipeJSONDTO;
import dtos.SingleRecipeDTO;
import entities.MealPlan;
import entities.Recipe;
import entities.User;
import utils.CallableHttpUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RecipeFacade
{
    private final String BASE_URL = "https://api.spoonacular.com/recipes/";
    private static EntityManagerFactory emf;
    private static RecipeFacade instance;

    private RecipeFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static RecipeFacade getRecipeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RecipeFacade();
        }
        return instance;
    }

    public List<String> complexSearch(String recipeName)
    {
        String URL = BASE_URL + "complexSearch?query=" + recipeName + System.getenv("APIKEY");
        List<String> urls = new ArrayList<>();
        urls.add(URL);
        try {
            List<String> jsonList = parallelRun(urls);
            return jsonList;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String singleRecipePage(int id)
    {
        // https://api.spoonacular.com/recipes/716429/information?includeNutrition=false
        String URL = BASE_URL +  id + "/information?includeNutrition=false" + System.getenv("APIKEY");
        List<String> urls = new ArrayList<>();
        urls.add(URL);
        try {
            List<String> jsonList = parallelRun(urls);
            return jsonList.get(0);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: tractor
    public String fetchSingleRecipe (String id) throws IOException
    {
        URL apiURL = new URL(BASE_URL+id+"/information?includeNutrition=true" + System.getenv("APIKEY"));
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(connection.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

    public String fetchRecipes (String url) throws IOException
    {
        URL apiURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(connection.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

//    public List<String> complexSearch(String recipeName, List<String> filterType, List<String> filterMin, List<String> filterMax)
//    {
//        String URL = BASE_URL + "complexSearch?query="+ recipeName + System.getenv("APIKEY");
//        List<String> urls = new ArrayList<>();
//        urls.add(URL);
//        try {
//            List<String> jsonList = parallelRun(urls);
//            return jsonList;
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public List<String> parallelRun(List<String> urls) throws ExecutionException, InterruptedException
    {
        ExecutorService es = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        List<String> results = new ArrayList<>();

        for (String url : urls) {
            Future<String> temp = es.submit(new CallableHttpUtils(url));
            futures.add(temp);
        }
        for (Future<String> f : futures) {
            String temp = f.get();
            results.add(temp);
        }
        return results;
    }

    public MealPlanDTO addMealPlan(MealPlanDTO mealPlanDTO)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            User user = em.find(User.class, mealPlanDTO.getUserName());
            Recipe recipe = em.find(Recipe.class, mealPlanDTO.getRecipeId());
//            if (recipe == null)
//            {
//                recipe = new Recipe(singleRecipeDTO.getId(), singleRecipeDTO.toString());
//                em.persist(recipe);
//                // need commit here?
//            }

            MealPlan mealPlan = new MealPlan(user, recipe, MealPlan.MealType.DINNER, LocalDate.now());
            em.persist(mealPlan);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return mealPlanDTO;
    }

    public Recipe saveRecipe(SingleRecipeDTO singleRecipeDTO)
    {
        EntityManager em = emf.createEntityManager();
        Recipe recipe;
        try
        {
            em.getTransaction().begin();
            recipe = em.find(Recipe.class, singleRecipeDTO.getId());
            if (recipe == null)
            {
                recipe = new Recipe(singleRecipeDTO.getId(), singleRecipeDTO.toString());
                em.persist(recipe);
                em.getTransaction().commit();
            }

        } finally {
            em.close();
        }
        return recipe;
    }

    public Recipe getRecipe (Integer id)
    {
        EntityManager em = emf.createEntityManager();
        Recipe recipe = em.find(Recipe.class, id);
//
        return recipe;

    }

}
