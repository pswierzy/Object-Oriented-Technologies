package model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gallery {

    private final ListProperty<Photo> photos = new SimpleListProperty<>(FXCollections.observableArrayList());

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public ObservableList<Photo> getPhotos() {
        return photos.get();
    }

    public void clear() {
        photos.clear();
    }
}
