package projects.android.my.autopopulate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    //Data to be stored in DB
    String[] cities = {"Managalore","Bangalore","Pune","Bombay","Hyderabad","Panji","Manipur","Bhuvneshwar","Hinjewadi"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CityDB cityDB = new CityDB(this);
        db = cityDB.getWritableDatabase();
        db.delete("City",null,null);
        prepareData();

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.txtcity);
        Cursor cursor = db.query("City",null,null,null,null,null,null);

        String[] cityFromDB = new String[cursor.getCount()];
        int pos=0;
        cursor.moveToFirst();
        do {
            cityFromDB[pos]=cursor.getString(0);
            pos++;
        }while (cursor.moveToNext());



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cityFromDB);
        textView.setThreshold(1);
        textView.setAdapter(adapter);

    }

    private void prepareData()
    {
        //Load Initial Values to DB
        ContentValues values = new ContentValues();
            for(int i=0;i<cities.length;i++)
            {
                  values.put("cityname",cities[i]);
                db.insert("City",null,values);
            }

    }
}
