package org.owasp.goatdroid.activity;

import android.app.Activity;
import android.os.Bundle;

import org.owasp.goatdroid.R;

public class ComponentIntentActivity extends BaseModuleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_intent);
    }
}
