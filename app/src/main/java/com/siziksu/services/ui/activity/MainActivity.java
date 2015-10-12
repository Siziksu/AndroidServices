package com.siziksu.services.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.siziksu.services.R;
import com.siziksu.services.app.Constants;
import com.siziksu.services.ui.object.Index;
import com.siziksu.services.ui.object.adapter.IndexAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

  private List<Index> index;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    index = new ArrayList<Index>();
    index.add(new Index(
            SimpleServiceActivity.class,
            "Simple Service",
            "Simple example of a Sticky Service. You can start and stop the Service, but since it's not bound, if you stop it, it will be destroyed but not stopped until the task ends.")
    );
    index.add(new Index(
            LongRunningServiceActivity.class,
            "Long Running Service",
            "Simple example of a Sticky Service with a simulated long running task. You can start and stop the Service, but since it's not bound, if you stop it, it will be destroyed but not stopped until the task ends.")
    );
    index.add(new Index(
            TimerClassServiceActivity.class,
            "Repeated Tasks Using the Timer Class",
            "This example performs some repeated tasks in a Service. An Alarm Clock Service that runs persistently in the background. You can start and stop the Service, but since it's not bound, if you stop it, it will be destroyed but not stopped until the task ends.")
    );
    index.add(new Index(
            IServiceActivity.class,
            "Using an IntentService",
            "This example executes an asynchronous task on a separate thread using an IntentService.")
    );
    index.add(new Index(
            CommunicateFromIServiceActivity.class,
            "Communication from an IntentService",
            "Often a service simply executes on its own thread, independently of the activity that calls it. The service might need to communicate information to the activity. In this example the IntentService communicates with the activity using a BroadcastReceiver.")
    );
    index.add(new Index(
            BindingServiceActivity.class,
            "Binding an Activity to a Service",
            "In this example we bind an Activity to a Service, being able to call methods inside the Service.")
    );
    index.add(new Index(
            BindingPackageServiceActivity.class,
            "Binding an Activity to a Service by Package",
            "In this example we bind an Activity to a Service by Package, being able to call methods inside the Service.")
    );
    index.add(new Index(
            WeatherServiceActivity.class,
            "Service with an external Binder and Listener",
            "In this example we bind an Activity to a Service with an external Binder that returns the result with a custom listener. The result is delivered after 2 seconds.")
    );
    index.add(new Index(
            MessengerServiceActivity.class,
            "Messenger Service",
            "In this example we bind an Activity to a Messenger Service. This Service works through Handler Messages. The result is delivered after 2 seconds.")
    );
    IndexAdapter adapter = new IndexAdapter(this, index);
    setListAdapter(adapter);
    getListView().setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    startActivity(
        new Intent(this, index.get(position).clazz)
            .putExtra(Constants.EXTRAS_TITLE, index.get(position).title)
            .putExtra(Constants.EXTRAS_SUMMARY, index.get(position).summary)
    );
  }
}
