package com.truxapp.fbfire;

import android.support.v7.app.AppCompatActivity;

public class RealmActivity extends AppCompatActivity {
/*    private Realm realm;
    Button add, view;
    EditText name, age;
    TextView allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        realm = Realm.getDefaultInstance();
        add = (Button) findViewById(R.id.addData);
        view = (Button) findViewById(R.id.viewData);
        allData = (TextView) findViewById(R.id.allData);
        name = (EditText)findViewById(R.id.name);
        age = (EditText)findViewById(R.id.age);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(name.getText().toString().trim(), Integer.parseInt(age.getText().toString().trim()));
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });
    }

    private void refreshData(){
        RealmResults<UserClass> result = realm.where(UserClass.class).findAllAsync();
        result.load();
        String output = "";

        for (UserClass userClass : result){
            output+= userClass.toString();
        }
        allData.setText(output);
    }

    private void saveData(final String Name, final int Age){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                UserClass userClass = bgRealm.createObject(UserClass.class);
                userClass.setName(Name);
                userClass.setAge(Age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("Successfully Saved", "Saved");
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("Error", error.getMessage());
                // Transaction failed and was automatically canceled.
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }*/
}
