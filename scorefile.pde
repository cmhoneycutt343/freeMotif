/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=32;
int zoom_notehigh=100;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=93;

// List of Instruments at http://explodingart.com/soundcipher/doc/arb/soundcipher/constants/ProgramChanges.html
float[] inst_cata = {score.BELL,score.CELLO,score.VIOLIN,score.MUSIC_BOX};

void globalscoresetup(){
      setcurrentkey(1);
}

void renderScore(){
    // Sample_Score1();
    score.addCallback(94,1);

    global_time_render_offset=0;
    // ss_descending_inv_scales2();
    ss_non_even_scaling_asc();


    global_time_render_offset=8;
    ss_descending_inv_scales();


    global_time_render_offset=8+32;
    ss_descending_inv_scales_b();

    global_time_render_offset=44+32;
    ss_non_even_scaling();
}
