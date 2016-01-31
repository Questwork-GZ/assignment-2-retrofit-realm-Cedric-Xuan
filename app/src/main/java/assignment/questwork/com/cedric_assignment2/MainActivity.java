package assignment.questwork.com.cedric_assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import realm.datatables.Information;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText et;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Realm myRealm = Realm.getInstance(getBaseContext());


        submit = (Button) findViewById(R.id.submit_btn);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et = (EditText) findViewById(R.id.eT);


                    //remove all the data from the realm
                    RealmResults<Information> result = myRealm.where(Information.class).findAll();
                    if (result.size() > 0) {
                        myRealm.beginTransaction();
                        result.clear();
                        myRealm.commitTransaction();
                    }


                    Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.104:8080")
                            .baseUrl(et.getText().toString())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    APISevice service = retrofit.create(APISevice.class);
                    Call<List<FloorPlan>> floorPlans = service.ReadFloorPlans();
                    floorPlans.enqueue(new Callback<List<FloorPlan>>() {
                        @Override
                        public void onResponse(Response<List<FloorPlan>> response) {
//                Toast.makeText(getBaseContext(),"responde:"+response,Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Recived the response");
                            Toast.makeText(getBaseContext(), "Recived the response", Toast.LENGTH_LONG).show();


                            //call Realm
                            myRealm.beginTransaction();

                            for (int i = 0; i < response.body().size(); i++) {
//                            Toast.makeText(getBaseContext(), "response:" + response.body().get(i).getName(), Toast.LENGTH_SHORT).show();


                                //create a realm object
                                Information information = myRealm.createObject(Information.class);

                                //set its fields
                                information.setId(response.body().get(i).getId());
                                information.setName(response.body().get(i).getName());

                            }

                            //commit the Realm object
                            myRealm.commitTransaction();

                            //junmp to the activity to show the listview
                            Intent intent = new Intent(getBaseContext(), MyListView.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), "failure", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Failed to recive the response");

                        }
                    });
                }
            });


        }
    }
