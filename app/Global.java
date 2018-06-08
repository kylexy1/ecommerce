/**
 * Created by Baptiste on 6/6/2018.
 */
import com.avaje.ebean.Ebean;
import models.Market;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

public class Global extends GlobalSettings {
    public void onStart(Application app) {
        InitialData.insert(app);
    }
    static class InitialData {
        static void insert(Application app) {
            if (Ebean.find(User.class).findRowCount() == 0) {
                Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial.yml");
                Ebean.save(all.get("user"));
            }
            if (Ebean.find(Market.class).findRowCount() == 0) {
                Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial.yml");
                Ebean.save(all.get("market"));
            }
        }
    }
}
