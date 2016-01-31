package assignment.questwork.com.cedric_assignment2;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by xyz on 2016/1/27.
 */
public interface APISevice {
@GET("/questcms/floorPlan")
Call<List<FloorPlan>> ReadFloorPlans();
}

