/**
 *
 *  @author Bożek Michał S24864
 *
 */

package zad1;


import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class Tools {
    public static Options createOptionsFromYaml(String file) throws Exception{
        try
        {
            Yaml yaml = new Yaml();
            String host ="host";
            String port ="port";
            String concurMode="concurMode";
            String showSendRes ="showSendRes";
            String clientsMap ="clientsMap";
            BufferedReader breader = new BufferedReader(new FileReader(file));
            String string="";
            String line;
            while((line=breader.readLine())!=null) {
                string = string + line + '\n';
            }
            Map map = yaml.load(string);
            breader.close();
            return new Options(map.get(host).toString(),(int)map.get(port),(boolean)map.get(concurMode),(boolean)map.get(showSendRes),(Map)map.get(clientsMap));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}