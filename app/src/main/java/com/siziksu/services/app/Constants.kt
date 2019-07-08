package com.siziksu.services.app

class Constants {

    companion object {
        const val APP_PACKAGE = "com.siziksu.services"
        const val SERVICE_PACKAGE = ".service"

        const val ACTION_FILES_DOWNLOADED = "action.FILES_DOWNLOADED_ACTION"

        const val TAG_SIMPLE_SERVICE = "SimpleService"
        const val TAG_INTENT_SERVICE = "IntentService"
        const val TAG_COMMUNICATE_FROM_SERVICE = "CommunicateFromIService"
        const val TAG_LONG_RUNNING_SERVICE = "LongRunningService"
        const val TAG_TIMER_CLASS_SERVICE = "TimerClassService"
        const val TAG_BINDING_SERVICE = "BindingService"
        const val TAG_BINDING_PACKAGE_SERVICE = "BindingPackageService"
        const val TAG_WEATHER_SERVICE = "WeatherService"
        const val TAG_MESSENGER_SERVICE = "MessengerService"
        const val TAG_BOOT_RECEIVER = "BootReceiver"

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

        const val BROADCAST_RECEIVED = "Broadcast received"
        const val BOOT_RECEIVER_FIRED = "Boot receiver fired after boot"

        const val TASK_CANCELED = "Task canceled"

        const val EXTRAS_URL = "urls"
        const val EXTRAS_TITLE = "title"
        const val EXTRAS_OWNER = "owner"
        const val EXTRAS_SUMMARY = "summary"
        const val EXTRAS_MESSAGE = "message"
        const val EXTRAS_FLAG = "flag"
        const val EXTRAS_JSON = "json"

        const val FLAG_TIME = "time"
    }
}
