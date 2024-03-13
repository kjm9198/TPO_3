/**
 *
 *  @author Bożek Michał S24864
 *
 */

package zad1;


import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
public class ClientTask extends FutureTask<String> {
    public Client client = new Client();
    static StringBuffer sbLog = new StringBuffer();
    public ClientTask(Callable<String> stringCallable) {
        super(stringCallable);
    }
    public Client append(){
        return client ;
    }
    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes){
        return new ClientTask(()->{
            c.connect();
            c.send("login " + c.id);
            reqs.forEach((string)->{
                String sent = c.send(string);
                if(showSendRes)
                    System.out.println(sent);
            });
            sbLog.append(c.send("bye and log transfer"));
            return sbLog.toString();
        });
    }
}