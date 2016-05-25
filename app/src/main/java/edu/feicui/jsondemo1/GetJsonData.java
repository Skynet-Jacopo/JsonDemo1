package edu.feicui.jsondemo1;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by ☆刘群☆ on 2016/5/25.
 */
public class GetJsonData {

    private static final String TAG      = "GetJsonData";
    public static final  String STUDENTS = "students";
    public static final  String NAME     = "name";
    public static final  String AGE      = "age";
    public static final  String CLASS    = "class";

    public static void getData(String path) throws IOException {

        String infos = null;
        URL url = new URL(path);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        connection.setConnectTimeout(5000);
        connection.connect();

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

        Log.d(TAG, "getData: " + infos);

        getInfoFromJson(infos);
    }

    private static void getInfoFromJson(String infos) {

        ArrayList<StudentBean> studentArrayList = new ArrayList<>();
        try {
            JSONObject infosJson = new JSONObject(infos);

            JSONArray infosArray = infosJson.getJSONArray(STUDENTS);

            JSONObject classJson = infosJson.getJSONObject(infos);
            String classname = classJson.getString(CLASS);

            for (int i = 0; i < infosArray.length(); i++) {
                String name;
                int age;

                JSONObject students = infosArray.getJSONObject(i);
                name = students.getString(NAME);
                age = students.getInt(AGE);

                StudentBean studentBean = new StudentBean(name, age);

                studentArrayList.add(studentBean);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
