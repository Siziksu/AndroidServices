package com.siziksu.services.commons

class Constants {

    companion object {
        const val APP_PACKAGE = "com.siziksu.services"
        const val SERVICE_PACKAGE = ".service"

        const val ACTION_FILES_DOWNLOADED = "action.FILES_DOWNLOADED_ACTION"

        const val TAG_SIMPLE_SERVICE = "SimpleService"
        const val TAG_INTENT_SERVICE = "IntentService"
        const val TAG_INTENT_SERVICE_TO_BROADCAST = "IntentServiceToBroadcast"
        const val TAG_BROADCAST_RECEIVER = "BroadcastReceiver"
        const val TAG_LONG_RUNNING_SERVICE = "LongRunningService"
        const val TAG_BINDING_SERVICE = "BindingService"
        const val TAG_BINDING_PACKAGE_SERVICE = "BindingPackageService"
        const val TAG_WEATHER_SERVICE = "WeatherService"
        const val TAG_MESSENGER_SERVICE = "MessengerService"

        const val TAG_MOCK_FILE = "MockFile"

        const val SERVICE_CREATED = "Service created"
        const val SERVICE_DESTROYED = "Service destroyed"
        const val SERVICE_CONNECTED = "Service connected"
        const val SERVICE_DISCONNECTED = "Service disconnected"
        const val SERVICE_STARTING = "Starting service"
        const val SERVICE_STARTED = "Service started"
        const val SERVICE_NOT_STARTED = "Service not started"
        const val SERVICE_STOPPING = "Stopping service"
        const val SERVICE_STOPPED = "Service stopped"
        const val SERVICE_BOUND = "Service bound"
        const val SERVICE_UNBOUND = "Service unbound"
        const val SERVICE_BINDING = "Binding service"
        const val SERVICE_UNBINDING = "Unbinding Service"
        const val SERVICE_NOT_BOUND = "Service not bound"
        const val SERVICE_RUNNING = "The service is still running"

        const val BROADCAST_MESSAGE = "This message comes from a Service through a Broadcast"

        const val EXTRAS_TITLE = "title"
        const val EXTRAS_SUMMARY = "summary"
        const val EXTRAS_MESSAGE = "message"
        const val EXTRAS_JSON = "json"
    }
}
