package pl.mobilebox.mantis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        //Issue value = intent.getParcelableExtra("data");
        Issue value = new Gson().fromJson(intent.getStringExtra("data"), Issue.class);

        TextView textView = findViewById(R.id.summary);
        textView.setText(value.summary);

        textView = findViewById(R.id.data);
        textView.setText(value.created_at.replaceAll("T|(\\+.*)", " "));

        textView = findViewById(R.id.description);
        textView.setText(value.description);

        View view = findViewById(R.id.view);
        view.setBackgroundColor(Color.parseColor(value.status.color));

        textView = findViewById(R.id.reporter);
        textView.setText(value.reporter.name);

        textView = findViewById(R.id.projekt);
        textView.setText(value.project.name);

        textView = findViewById(R.id.status);
        textView.setText(value.status.label);

        textView = findViewById(R.id.view2);
        textView.setText(value.priority.label);
        switch (value.priority.id) {
            case 20: {//low
                textView.setTextColor(Color.parseColor("#69AA46"));
                break;
            }
            case 30: {//normal
                textView.setTextColor(Color.parseColor("#FEB902"));
                break;
            }
            case 40: {//hight
                textView.setTextColor(Color.parseColor("#fe7c02"));
                break;
            }
            case 50://urgent
            case 60: {//immediate
                textView.setTextColor(Color.parseColor("#DD5A43"));
                break;
            }
            case 10: //none
            default: {
                textView.setTextColor(Color.parseColor("#000000"));
                break;
            }
        }

        if (value.notes != null) {
            LinearLayout listView = findViewById(R.id.scrollView);
            for (Issue.Notes note : value.notes) {
                View tmpview = LayoutInflater.from(this).inflate(R.layout.list_note, null);
                ((TextView) tmpview.findViewById(R.id.reporter)).setText(note.reporter.name);
                ((TextView) tmpview.findViewById(R.id.updated_at)).setText(note.updated_at.replaceAll("T|(\\+.*)", " "));
                ((TextView) tmpview.findViewById(R.id.text)).setText(note.text);
                listView.addView(tmpview);
            }
        }
    }
}