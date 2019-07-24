package pl.mobilebox.mantis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    int page =1;
    int size =10;
    API api;
    ListView listView;
    IssuesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = new API();
        api.addObserver(this);
        api.getIssues(size, page);
        Toast.makeText(this,getString(R.string.loading),Toast.LENGTH_LONG).show();

        adapter = new IssuesAdapter(this, new ArrayList<Issue>());
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(MainActivity.this,"", Toast.LENGTH_LONG).show();
                Intent item = new Intent(MainActivity.this, ItemActivity.class);
                item.putExtra("data", new Gson().toJson(adapter.data.get(position)));
                MainActivity.this.startActivity(item);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener(){

            boolean scroll=true;

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                scroll=true;
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && scroll)
                {
                    api.getIssues(size, ++page);
                    scroll=false;
                }
            }
        });
    }

    public void update(Observable obj, Object arg) {
        adapter.addItems(((Issues)arg).issues);
        adapter.notifyDataSetChanged();
//        final Issues data = (Issues)arg;
//        final IssuesAdapter adapter = new IssuesAdapter(this, data.issues);
//        ListView listView = findViewById(R.id.listView);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                //Toast.makeText(MainActivity.this,"", Toast.LENGTH_LONG).show();
//                Intent item = new Intent(MainActivity.this, ItemActivity.class);
//                item.putExtra("data", new Gson().toJson(data.issues.get(position)));
//                MainActivity.this.startActivity(item);
//            }
//        });
//        listView.setOnScrollListener(new AbsListView.OnScrollListener(){
//
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
//                {
//                    api.getIssues(size, ++page);
//                }
//            }
//        });
    }
}

class IssuesAdapter extends BaseAdapter {
    private Context context;
    List<Issue> data;

    IssuesAdapter(Context context, List<Issue> data) {
        this.context = context;
        this.data = data;
    }

    void addItems(List<Issue> data){
        this.data.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder.summary = convertView.findViewById(R.id.summary);
            holder.description = convertView.findViewById(R.id.description);
            holder.priority = convertView.findViewById(R.id.priority);
            holder.status = convertView.findViewById(R.id.status);
            holder.created_at = convertView.findViewById(R.id.created_at);
            holder.updated_at = convertView.findViewById(R.id.updated_at);
            holder.projekt = convertView.findViewById(R.id.projekt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.summary.setText(data.get(position).summary);
        if (data.get(position).description.length() < 120)
            holder.description.setText(data.get(position).description);
        else
            holder.description.setText(data.get(position).description.substring(0, 117) + "...");
        holder.priority.setText(data.get(position).priority.label);
        holder.status.setText(data.get(position).status.label);
        holder.created_at.setText(data.get(position).created_at.replaceAll("T|(\\+.*)", " "));
        holder.updated_at.setText(data.get(position).updated_at.replaceAll("T|(\\+.*)", " "));
        holder.projekt.setText(data.get(position).project.name);
        return convertView;
    }

    class ViewHolder {
        TextView summary;
        TextView description;
        TextView projekt;
        TextView priority;
        TextView status;
        TextView created_at;
        TextView updated_at;
    }
}