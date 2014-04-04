package org.owasp.goatdroid.activity;

import android.app.Activity;
import android.os.Bundle;

import org.owasp.goatdroid.R;

public class BinaryProtectionActivity extends BaseModuleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_hardening);
    }
}
