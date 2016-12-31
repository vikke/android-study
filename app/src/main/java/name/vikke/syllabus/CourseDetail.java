package name.vikke.syllabus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CourseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent intent = getIntent();

        String dateStr = intent.getStringExtra("date");
        String titleStr = intent.getStringExtra("title");
        String teacherStr = intent.getStringExtra("teacher");
        String detailStr = intent.getStringExtra("detail");

        TextView dateText = (TextView)findViewById(R.id.dateText);
        dateText.setText(dateStr);
        TextView titleText = (TextView)findViewById(R.id.titleText);
        titleText.setText(titleStr);
        TextView teacherText = (TextView)findViewById(R.id.teacherText);
        teacherText.setText(teacherStr);
        TextView detailText = (TextView)findViewById(R.id.detailText);
        detailText.setText(detailStr);


    }
}
