package ayudec.ayudec;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Alumno _alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_layout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Alumno _alumno = new Gson().fromJson(getIntent().getStringExtra("alumno"), Alumno.class);
        ((TextView)findViewById(R.id.userName)).setText(_alumno.get_nombre());
        ((TextView)findViewById(R.id.userCar)).setText(_alumno.get_carrera());
        ((TextView)findViewById(R.id.userType)).setText(_alumno.get_matricula());


        // Prueba con Chipviews
        ChipView cvTag = (ChipView) findViewById(R.id.especView);
        ArrayList<Object> data = new ArrayList<>();
        data.add("First Item");
        data.add("Second Item");
        data.add("Third Item");
        data.add("Fourth Item");
        data.add("Fifth Item");
        data.add("Sixth Item");
        data.add("Seventh Item");
        SimpleChipAdapter adapter = new SimpleChipAdapter(data);
        cvTag.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

//    public void Profile(View view){
//        startActivity(new Intent(this, ProfileActivity.class));
//    }
}
