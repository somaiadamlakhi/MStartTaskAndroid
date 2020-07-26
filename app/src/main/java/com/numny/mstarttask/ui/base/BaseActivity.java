package com.numny.mstarttask.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.numny.mstarttask.utils.AppConstatns;


public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {

    protected VM viewModel;

    @NonNull
    protected abstract VM createViewModel();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
    }

    public void goToActivity(Context context, Class secondClass) {
        Intent intent = new Intent(context, secondClass);
        startActivity(intent);
    }

    public void goToActivity(Context context, Class secondClass, Bundle bundle) {
        Intent intent = new Intent(context, secondClass);
        intent.putExtra(AppConstatns.BUNDLE_KEY, bundle);
        startActivity(intent);
    }

}
