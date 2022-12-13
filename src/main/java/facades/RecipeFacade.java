package facades;

import dtos.*;
import entities.*;
import errorhandling.API_Exception;
import utils.CallableHttpUtils;
import utils.Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
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

    public MealPlanDTO addMealPlan(MealPlanDTO mealPlanDTO) throws API_Exception {
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
            TypedQuery<MealPlan> queryM = em.createQuery("SELECT m FROM MealPlan m Join m.recipe r WHERE m.userName.userName = :userName AND m.date = :date", MealPlan.class);
            queryM.setParameter("userName",user.getUserName());
            queryM.setParameter("date",mealPlanDTO.getDate());
            List<MealPlan> mealPlansForThatDay = queryM.getResultList();

            for (MealPlan mealPlan : mealPlansForThatDay) {
                if (mealPlan.getType().getMeal().equals(mealPlanDTO.getType()))
                    throw new API_Exception("A mealplay with mealtype: " + mealPlanDTO.getType() + ", Already exist for date: " + mealPlanDTO.getDate());
            }

            MealPlan.MealType type;
            if (mealPlanDTO.getType().equals("DINNER"))
                type = MealPlan.MealType.DINNER;
            else if (mealPlanDTO.getType().equals("BREAKFAST"))
              type = MealPlan.MealType.BREAKFAST;
            else
                type = MealPlan.MealType.LUNCH;

            MealPlan mealPlan = new MealPlan(user, recipe, type, mealPlanDTO.getDate());
            em.persist(mealPlan);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return mealPlanDTO;
    }

    public List<MealPlanDTO> getAllMealPlans(String userName)
    {
        EntityManager em = emf.createEntityManager();

        try{
            TypedQuery<MealPlan> query = em.createQuery("SELECT p FROM MealPlan p JOIN p.userName ph WHERE ph.userName = :userName order by p.date", MealPlan.class);
            query.setParameter("userName",userName);
            List<MealPlan> mealPlans = query.getResultList();
            return MealPlanDTO.getDtos(mealPlans);
        } finally {
            em.close();
        }
    }

    public List<String> getMealplanRecipesJSONByUserAndDate(String userName, LocalDate date)
    {
        {
            EntityManager em = emf.createEntityManager();
            try{
                //SELECT r FROM Recipe r Join r.bookmarks bm Join bm.userName us WHERE us.userName = :userName
                TypedQuery<Recipe> query = em.createQuery("SELECT r FROM MealPlan m Join m.recipe r WHERE m.userName.userName = :userName AND m.date = :date", Recipe.class);
                query.setParameter("userName",userName);
                query.setParameter("date",date);
                List<Recipe> recipes = query.getResultList();
                List<String> recipeJSONList = new ArrayList<>();
                recipes.forEach(recipe -> recipeJSONList.add(recipe.getRecipeJson()));
                return recipeJSONList;
            } finally {
                em.close();
            }
        }
    }

    public List<SingleMealPlanDTO> getRecipeAndTypeByUsernameAndDate(String userName, LocalDate date)
    {
        {
            EntityManager em = emf.createEntityManager();
            try{
                //SELECT r FROM Recipe r Join r.bookmarks bm Join bm.userName us WHERE us.userName = :userName
                TypedQuery<Recipe> queryR = em.createQuery("SELECT r FROM MealPlan m Join m.recipe r WHERE m.userName.userName = :userName AND m.date = :date", Recipe.class);
                TypedQuery<MealPlan> queryM = em.createQuery("SELECT m FROM MealPlan m Join m.recipe r WHERE m.userName.userName = :userName AND m.date = :date", MealPlan.class);
                queryR.setParameter("userName",userName);
                queryM.setParameter("userName",userName);
                queryR.setParameter("date",date);
                queryM.setParameter("date",date);
                List<Recipe> recipes = queryR.getResultList();
                List<MealPlan> mealPlans = queryM.getResultList();
                List<SingleMealPlanDTO> dtos = SingleMealPlanDTO.getDtos(recipes, mealPlans);
//                List<SingleMealPlanDTO> dtos = query.getResultList();
//                List<String> recipeJSONList = new ArrayList<>();
//                dtos.forEach(mr -> recipeJSONList.add(mr.getRecipeJson()));
                return dtos;
            } finally {
                em.close();
            }
        }
    }

    public BookmarkDTO addBookmark(BookmarkDTO bookmarkDTO)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            User user = em.find(User.class, bookmarkDTO.getUsername());
            Recipe recipe = em.find(Recipe.class, bookmarkDTO.getRecipeId());
//            if (recipe == null)
//            {
//                recipe = new Recipe(singleRecipeDTO.getId(), singleRecipeDTO.toString());
//                em.persist(recipe);
//                // need commit here?
//            }



            Bookmark bookmark = new Bookmark(user, recipe);
            em.persist(bookmark);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return bookmarkDTO;
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



    public String getRecipeById(Integer id)
    {
        EntityManager em = emf.createEntityManager();
        Recipe recipe = em.find(Recipe.class, id);
        String recipeJSON = recipe.getRecipeJson();
        return recipeJSON;
    }

    public List<String> getBookmarkedRecipeJSONFromDbByUser(String userName)
    {
        {
            EntityManager em = emf.createEntityManager();
            try{
                TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipe r Join r.bookmarks bm Join bm.userName us WHERE us.userName = :userName", Recipe.class);
                query.setParameter("userName",userName);
                List<Recipe> recipes = query.getResultList();
                List<String> recipeJSONList = new ArrayList<>();
                recipes.forEach(recipe -> recipeJSONList.add(recipe.getRecipeJson()));
                return recipeJSONList;
            } finally {
                em.close();
            }
        }
    }

    public List<BookmarkDTO> getBookmarks(String userName) {
        {
            EntityManager em = emf.createEntityManager();

//            Query("SELECT p FROM MealPlan p JOIN p.userName ph WHERE ph.userName = :userName", MealPlan.class);

            try{
                TypedQuery<Bookmark> query = em.createQuery("SELECT b FROM Bookmark b Join b.userName bu WHERE bu.userName = :userName", Bookmark.class);
                query.setParameter("userName",userName);
                List<Bookmark> bookMarks = query.getResultList();
                return BookmarkDTO.getBookMarkDtos(bookMarks);
            } finally {
                em.close();
            }
        }
    }
}
