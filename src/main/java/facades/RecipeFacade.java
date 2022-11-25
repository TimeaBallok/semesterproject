package facades;

import utils.CallableHttpUtils;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
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
        String URL = BASE_URL + "complexSearch?query="+ recipeName + System.getenv("APIKEY");
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
}
