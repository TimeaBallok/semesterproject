package facades;

import utils.CallableHttpUtils;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JokeFacade
{


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
