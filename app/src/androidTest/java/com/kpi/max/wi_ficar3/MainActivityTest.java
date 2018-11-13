package com.kpi.max.wi_ficar3;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.kpi.max.wi_ficar3.R.id.buttonPin11;
import static org.junit.Assert.*;

/**
 * Created by Max on 13.11.2018.
 */
public class MainActivityTest {
    public MainActivity mainActivity;
    @Test
    public void onCreate() throws Exception {
        EditText testField = mainActivity.getEditTextIPAddress();
        assertNull(mainActivity.checkEdit(testField));
    }


    @Test
    public void onOptionsItemSelected(MenuItem itemTest) throws Exception {
        assertEquals(itemTest.getItemId(), R.id.action_about);
    }


    @Test
    public void onClick() throws Exception {
        String testText = "174.2.0.0";
        String testText2 = null;
        assertFalse(mainActivity.checkStr(testText));
        assertTrue(mainActivity.checkStr(testText2));
    }



}