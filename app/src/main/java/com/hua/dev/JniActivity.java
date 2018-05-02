package com.hua.dev;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hua.librarytools.safetyrelated.Ptlmaner;

public class JniActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(Ptlmaner.requestProcess());
    }
}
