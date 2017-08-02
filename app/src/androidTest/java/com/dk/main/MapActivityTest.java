package com.dk.main;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.hitaxi.activities.MapsActivity;

/**
 * Created by DK on 2017/7/21.
 */

public class MapActivityTest extends ActivityInstrumentationTestCase2<MapsActivity>{
    private Context ctx;
    public MapActivityTest() {
        super(MapsActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ctx = getActivity().getApplicationContext();
    }

    public void testStart() {
        Intent intent = new Intent(ctx, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }


}
