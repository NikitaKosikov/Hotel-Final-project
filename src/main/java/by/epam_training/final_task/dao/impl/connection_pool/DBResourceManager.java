package by.epam_training.final_task.dao.impl.connection_pool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private static final String BUNDLE_NAME = "db";
    private static final DBResourceManager instance= new DBResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    public static DBResourceManager getInstance(){
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
