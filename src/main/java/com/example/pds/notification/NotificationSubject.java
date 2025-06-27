package com.example.pds.notification;

public interface NotificationSubject {
    void registerObserver(NotificationObserver observer);
    void removeObserver(NotificationObserver observer);
    void notifyObservers(NotificationEvent event);
} 