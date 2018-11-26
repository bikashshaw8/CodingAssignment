package com.poc.assignment.interfaces;

import android.support.annotation.StringRes;

/**
 * Created by Bikash on 24/11/18.
 *
 * @author Bikash
 */

public interface IBasicUI {

  interface Presenter<V extends View> {

    void onAttach(V view);

    void onDetach();
  }

  interface View {
    public void showProgressLoader(boolean isShow);
    public void showError();
  }
}
