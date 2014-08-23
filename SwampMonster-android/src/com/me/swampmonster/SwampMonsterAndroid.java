package com.me.swampmonster;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.me.swampmonster.utils.ScreenContainer;

public class SwampMonsterAndroid extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
//        MainSwamp mainSwamp = new MainSwamp();
//        ScreenContainer.game = mainSwamp;
        initialize(new MainSwamp(), cfg);
    }
}