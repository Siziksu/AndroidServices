package com.siziksu.services.ui.activity

import com.siziksu.services.ui.menu.Index
import java.util.ArrayList

class MenuLoader {

    companion object {
        fun getMenu(): ArrayList<Index> {
            val menu: ArrayList<Index> = ArrayList()
            menu.add(Index(
                    SimpleServiceActivity::class.java,
                    "Simple Service",
                    "Simple example of a Sticky Service. You can start and stop the Service, but since it's not bound, if you stop it, it will be destroyed but not stopped until the task ends.")
            )
            menu.add(Index(
                    LongRunningServiceActivity::class.java,
                    "Long Running Service",
                    "Simple example of a Sticky Service with a simulated long running task. You can start and stop the Service, but since it's not bound, if you stop it, it will be destroyed but not stopped until the task ends.")
            )
            menu.add(Index(
                    ServiceActivity::class.java,
                    "Using an IntentService",
                    "This example executes an asynchronous task on a separate thread using an IntentService.")
            )
            menu.add(Index(
                    CommunicateFromIServiceActivity::class.java,
                    "Communication from an IntentService",
                    "Often a service simply executes on its own thread, independently of the activity that calls it. The service might need to communicate information to the activity. In this example the IntentService communicates with the activity using a BroadcastReceiver.")
            )
            menu.add(Index(
                    BindingServiceActivity::class.java,
                    "Binding an Activity to a Service",
                    "In this example we bind an Activity to a Service, being able to call methods inside the Service.")
            )
            menu.add(Index(
                    BindingPackageServiceActivity::class.java,
                    "Binding an Activity to a Service by Package",
                    "In this example we bind an Activity to a Service by Package, being able to call methods inside the Service.")
            )
            menu.add(Index(
                    WeatherServiceActivity::class.java,
                    "Service with an external Binder and Listener",
                    "In this example we bind an Activity to a Service with an external Binder that returns the result with a custom listener. The result is delivered after 2 seconds.")
            )
            menu.add(Index(
                    MessengerServiceActivity::class.java,
                    "Messenger Service",
                    "In this example we bind an Activity to a Messenger Service. This Service works through Handler Messages. The result is delivered after 2 seconds.")
            )
            return menu
        }
    }
}
