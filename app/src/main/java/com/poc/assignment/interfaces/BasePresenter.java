package com.poc.assignment.interfaces;

import android.support.annotation.NonNull;

/**
 * Created by Bikash on 24/11/18.
 *
 * @author Bikash
 */

public abstract class BasePresenter<V extends IBasicUI.View>
    implements IBasicUI.Presenter<V> {
  private V view;

  @Override
  public void onAttach(V view) {
    this.view = view;
  }

  @Override
  public void onDetach() {
    view = null;
  }

  public boolean isViewAttached() {
    return view != null;
  }

  public V getView() {
    return view;
  }

}
