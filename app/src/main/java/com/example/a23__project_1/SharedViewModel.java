package com.example.a23__project_1;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> selectedItem = new MutableLiveData<Integer>();
    private final MutableLiveData<Integer> fromWhere = new MutableLiveData<Integer>();
    public void selectItem(int item,int where) {
        selectedItem.setValue(item);
        fromWhere.setValue(where);
    }
    public void setFromWhere(int where) {
        fromWhere.setValue(where);
    }
    public MutableLiveData<Integer> getSelectedItem() {
        return selectedItem;
    }

    public MutableLiveData<Integer> getFromWhere() {
        return fromWhere;
    }

}