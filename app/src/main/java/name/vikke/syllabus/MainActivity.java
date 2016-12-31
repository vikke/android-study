package name.vikke.syllabus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private class CourseItem {
        String date;
        String title;
        String teacher;
        String detail;
    }

    private class ItemAdapter extends ArrayAdapter<CourseItem> {
        private LayoutInflater inflater;

        public ItemAdapter(Context context, int resource, List<CourseItem> objects) {
            super(context, resource, objects);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.lecture_row, null, false);

            TextView dateView = (TextView)view.findViewById(R.id.date);
            TextView titleView = (TextView)view.findViewById(R.id.title);

            CourseItem item = getItem(position);
            dateView.setText(item.date);
            titleView.setText(item.title);

            return view;
        }
    }

    private List<CourseItem> itemList;
    private ItemAdapter adapter;


    private void setCourseData() {
        CourseItem item = new CourseItem();
        item.date = "1/1";
        item.title = "title";
        item.teacher = "internet";
        item.detail = "detail";

        itemList.add(item);

        item = new CourseItem();
        item.date = "1/2";
        item.title = "title2";
        item.teacher = "internet2";
        item.detail = "detail2";

        itemList.add(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemList = new ArrayList<CourseItem>();
        adapter = new ItemAdapter(getApplicationContext(), 0, itemList);
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

        setContentView(R.layout.activity_main);
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
