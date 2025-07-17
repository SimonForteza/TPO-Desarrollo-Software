package com.example.pds.notification;

import java.util.List;
import java.util.ArrayList;
import com.example.pds.model.entity.Usuario;

public abstract class IObservable {
    private List<IObserver> observers;

    public IObservable() {
        observers = new ArrayList<>();
    }

    public void attach(IObserver observer) {
        observers.add(observer);
    }
    public void detach(IObserver observer) {
        observers.remove(observer);
    }
    public void notifyObservers(Usuario usuarioResponsable) {
        for (IObserver observer : observers) {
            observer.update(this, usuarioResponsable);
        }
    }
} 