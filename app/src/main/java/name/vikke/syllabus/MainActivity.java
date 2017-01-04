package name.vikke.syllabus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private class CourseItem {
        Date date;
        String title;
        String teacher;
        String detail;
    }

    private class ItemAdapter extends ArrayAdapter<CourseItem> {
        private LayoutInflater inflater;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

        public ItemAdapter(Context context, int resource, List<CourseItem> objects) {
            super(context, resource, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.lecture_row, null, false);

            TextView dateView = (TextView) view.findViewById(R.id.date);
            TextView titleView = (TextView) view.findViewById(R.id.title);

            CourseItem item = getItem(position);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
            dateView.setText(dateFormat.format(item.date));
            titleView.setText(item.title);

            return view;
        }
    }

    private List<CourseItem> itemList;
    private ItemAdapter adapter;
    private RequestQueue requestQueue;
    private static final String url = "https://punch-drunker.herokuapp.com/syllabuses";

    private void getCourseData() {
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray array = response.getJSONArray("course");
                    setCourseArray(array);
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", "error=" + error);
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, listner, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    private void setCourseArray(JSONArray jsonArray) throws JSONException {
        int num = jsonArray.length();
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < num; i++) {
            CourseItem item = new CourseItem();
            JSONObject object = jsonArray.getJSONObject(i);
            String dataStr = object.getString("date");
            Date date = null;

            item.date = date;
            item.title = object.getString("title");
            item.teacher = object.getString("teacher");
            item.detail = object.getString("detail");

            itemList.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        itemList = new ArrayList<CourseItem>();
        adapter = new ItemAdapter(getApplicationContext(), 0, itemList);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        getCourseData();

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseItem item = (CourseItem)parent.getItemAtPosition(position);

        Intent intent = new Intent(this, CourseDetail.class);
        intent.putExtra("date", item.date);
        intent.putExtra("title", item.title);
        intent.putExtra("teacher", item.teacher);
        intent.putExtra("detail", item.detail);

        startActivity(intent);
    }

     /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
