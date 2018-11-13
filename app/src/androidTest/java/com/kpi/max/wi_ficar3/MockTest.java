package com.kpi.max.wi_ficar3;


/**
 * Created by Max on 13.11.2018.
 */

import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.view.MenuItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    @Test
    public void onOptionsItemSelected() throws Exception{
        Spanned word = mock(SpannedString.class);

        // 2. tell the mock how to behave
        when(word.length()).thenReturn(1);

        // 3. use the mock
        assertEquals(1, word.length());
    }

}
