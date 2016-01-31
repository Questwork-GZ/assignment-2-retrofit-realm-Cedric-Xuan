package realm.datatables;

import io.realm.RealmObject;

/**
 * Created by xyz on 2016/1/26.
 */
public class Information extends RealmObject {
    private String id;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}