package assignment.questwork.com.cedric_assignment2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import realm.datatables.Information;

public class MyListView extends AppCompatActivity {
    private android.widget.ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView =new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData()));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setContentView(listView);
    }

    private ArrayList<String> getData(){
        ArrayList<String> data =new ArrayList<String>();

        Realm myRealm2=Realm.getInstance(getBaseContext());

        //findAll information from the realm datatable
        RealmResults<Information> results= myRealm2.where(Information.class).findAll();
        for(Information info:results){
            data.add("id:"+info.getId()+"\nname:"+info.getName());
        }

        return data;
    }


}
