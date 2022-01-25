package com.example.mymusic;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /**
     * 测试当前应用包名
     */
    @Test
    public void useAppContext() {
        // //app测试上下文
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //测试包名
        assertEquals("com.example.mymusic", appContext.getPackageName());
    }
}