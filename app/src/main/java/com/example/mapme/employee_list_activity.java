package com.example.mapme;

import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class employee_list_activity extends AppCompatActivity {
    ArrayList<String> listItem; ArrayAdapter<String> adapter; ListView lvItems;
    DatabaseHelper myDb;
    HashMap<Integer, Integer> hashMap; ArrayList<Integer> locationStatus;

    /*
    //Object implementation try
    Employee employee;
    ArrayList<Employee> employeeObjects = new ArrayList<>(); String objectTry="";
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list_activity);

        myDb = new DatabaseHelper(this);
        lvItems = findViewById(R.id.lvItems);
        listItem = new ArrayList<>();

        viewData();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //checking if employee location is available
                Boolean locationStatusFlag = getLocationStatus(position);
                if (locationStatusFlag) {
                    //Calling pre_map_activity on listview item click
                    String message = String.valueOf(position);
                    Intent intent = new Intent(employee_list_activity.this, pre_map_activity.class);
                    //Passing the position of the click
                    intent.putExtra("message", message);
                    intent.putExtra("HashMap", hashMap);

                /*
                //Object implementation try
                //Passing the ArrayList<Employee> employeeObjects;
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)employeeObjects);
                intent.putExtra("BUNDLE",args);
                */

                    startActivity(intent);
                }
                else {
                    Integer clicked_id = hashMap.get(position);
                    //Displaying the dialog box..
                    //..which prompts the user to input employee's location
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    //Fragment fragment = fragmentManager.findFragmentByTag("dialog");
                    InputLocationDialogFragment ildf = new InputLocationDialogFragment(employee_list_activity.this, clicked_id);
                    ildf.show(fragmentManager,"dialog");
                }
            }
        });
    }

    private void viewData(){
        Cursor cursor = myDb.viewData();

        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data to show",Toast.LENGTH_LONG).show();
        }
        else {
            Integer position = 0; hashMap = new HashMap<>(); locationStatus = new ArrayList<>();
            while (cursor.moveToNext()){

                //Push each Employee "name" into listview
                listItem.add(cursor.getString(1));

                //Storing employee <positionInListview, id> as <key, value>..
                //..pairs in HashMap<int, int>
                Integer id = cursor.getInt(0);
                hashMap.put(position, id);
                position++;

                //stores locationStatus i.e. location available or not
                locationStatus.add(cursor.getInt(2));

                /*
                //Object implementation try
                //Constructing an Employee object for each employee
                String name, latitude, longitude; Integer id, location;
                id = cursor.getInt(0);
                name = cursor.getString(1);
                location = cursor.getInt(2);
                latitude = cursor.getString(3);
                longitude = cursor.getString(4);

                employee = new Employee(id, name, location, latitude, longitude);
                employeeObjects.add(employee);
                */
            }

            /*
            //Adds each employee object's information on the listview for the testing purpose
            for(int i = 0; i < employeeObjects.size(); i++){
                objectTry = objectTry + employeeObjects.get(i).toString()+"\n";
            }
            listItem.add(objectTry);
            */

            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItem);
            lvItems.setAdapter(adapter);
        }
    }

    //return employee location's availibility status
    private boolean getLocationStatus(Integer position){
        if (locationStatus.get(position) == 1){
            return true;
        }
        else {
            return false;
        }
    }
}
