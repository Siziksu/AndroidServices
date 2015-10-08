# AndroidServices
This project is intended to bring an approach to the Services in Android. All the events are shown in the logcat.

List of examples:

- **Simple Service**: Simple example of a Sticky Service.
- **Long Running Service**: Simple example of a Sticky Service with a simulated long running task.
- **Repeated Tasks Using the Timer Class**: This example performs some repeated tasks in a Service. An Alarm Clock Service that runs persistently in the background.
- **Using an IntentService**: This example executes an asynchronous task on a separate thread using an IntentService.
- **Communication from an IntentService**: Often a service simply executes on its own thread, independently of the activity that calls it. The service might need to communicate information to the activity. In this example the IntentService communicates with the activity using a BroadcastReceiver.
- **Binding an Activity to a Service**: In this example we bind an Activity to a Service, being able to call methods inside the Service.
- **Binding an Activity to a Service by Package**: In this example we bind an Activity to a Service by Package, being able to call methods inside the Service.
- **Service with an external Binder and Listener**: In this example we bind an Activity to a Service with an external Binder that returns the result with a listener. The result is delivered after 2 seconds.
- **Messenger Service**: In this example we bind an Activity to a Messenger Service. This Service works through Handler Messages. The result is delivered after 2 seconds.

## License
    Copyright 2015 Esteban Latre
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.