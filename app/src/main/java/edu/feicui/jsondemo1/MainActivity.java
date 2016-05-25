package edu.feicui.jsondemo1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG       = "MainActivity";
    public static final  int    MESSAGE_1 = 0;
    String path = "http://192.168.1.147:8080/index2.jsp";

    public static final String STUDENTS = "students";
    public static final String NAME     = "name";
    public static final String AGE      = "age";
    public static final String CLASS    = "class";

    TextView tvClass;
    TextView tvName1;
    TextView tvAge1;
    TextView tvName2;
    TextView tvAge2;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_1:

//                    ArrayList<StudentBean> listData = (ArrayList<StudentBean>) msg.obj;
//                    Log.d(TAG, "handleMessage: "+listData);
//                    tvName1.setText(listData.get(0).name);
//                    tvAge1.setText(String.valueOf(listData.get(0).age));
//                    tvName2.setText(listData.get(1).name);
//                    tvAge2.setText(String.valueOf(listData.get(1).age));
//                    Log.d(TAG, "handleMessage: 处理json");
//
//                    tvName1.setText(mBeanList.get(0).name);
//                    tvAge1.setText(mBeanList.get(0).age);


                    Gson gson = new Gson();
                    String json = (String) msg.obj;
                    Students students = gson.fromJson(json, Students.class);
                    tvClass.setText(students.classname);
                    List<StudentBean> studentBeens = students.getStudents();
                    tvName1.setText(studentBeens.get(0).getName());
                    tvAge1.setText(studentBeens.get(0).getAge()+"");
                    tvName2.setText(studentBeens.get(1).getName());
                    tvAge2.setText(studentBeens.get(1).getAge()+"");

                    //tvName1.setText(studentBeens.get(0).getName());

//                    for (StudentBean studentBeen : studentBeens) {
//                        Log.d(TAG, "handleMessage: 遍历数组");
//                        System.out.println(studentBeen);
//                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {

        tvName1 = (TextView) findViewById(R.id.tv_name1);
        tvAge1 = (TextView) findViewById(R.id.tv_age1);
        tvName2 = (TextView) findViewById(R.id.tv_name2);
        tvAge2 = (TextView) findViewById(R.id.tv_age2);
        tvClass = (TextView) findViewById(R.id.tv_class);
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String infos = null;
                    URL url = new URL(path);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");

                    connection.setConnectTimeout(5000);

                    InputStream inputStream = connection.getInputStream();

                    StringBuffer buffer = new StringBuffer();

                    if (inputStream == null) {
                        return;
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    infos = buffer.toString();

                    JSONObject jsonObject =new JSONObject(infos);

                    JSONArray jsonArray =jsonObject.getJSONArray(STUDENTS);


                    List<StudentBean> list =new ArrayList<StudentBean>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String name;
                        int age;
                        JSONObject object=jsonArray.getJSONObject(i);

                        name=object.getString(NAME);
                        age =object.getInt(AGE);
                        Log.d(TAG, "run: "+name+ "     " +age);
                        StudentBean studentBean = new StudentBean(name, age);
                        list.add(studentBean);
                    }

                    Message message = new Message();
                    message.what = MESSAGE_1;
//                    message.obj =list;

                    message.obj =infos;
//                    getInfoFromJson(infos);
                    mHandler.sendMessage(message);
//                    mHandler.sendEmptyMessage(MESSAGE_1);
                    Log.d(TAG, "getData: " + infos);
                } catch (IOException |JSONException e) {
                    Log.d(TAG, "run: 你走这里了?");
                    e.printStackTrace();
                }
            }
        }.start();
    }

     String classname;

    private  void getInfoFromJson(String infos) {

        List<StudentBean> studentArrayList = new ArrayList<>();
        try {
            JSONObject infosJson = new JSONObject(infos);

            JSONArray infosArray = infosJson.getJSONArray(STUDENTS);

//            JSONObject classJson = infosJson.getJSONObject(infos);
            classname = infosJson.getString(CLASS);

            for (int i = 0; i < infosArray.length(); i++) {
                String name;
                int age;

                JSONObject students = infosArray.getJSONObject(i);
                name = students.getString(NAME);
                age = students.getInt(AGE);

                Log.d(TAG, "getInfoFromJson: "+name+"   "+age);
                StudentBean studentBean = new StudentBean(name, age);

                studentArrayList.add(studentBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
