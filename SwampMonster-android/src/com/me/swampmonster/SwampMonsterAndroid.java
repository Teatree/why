package com.me.swampmonster;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SwampMonsterAndroid extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
//        cfg.useGL30 = true;
//        MainSwamp mainSwamp = new MainSwamp();
//        ScreenContainer.game = mainSwamp;
        initialize(new MainSwamp(), cfg);
    }
}