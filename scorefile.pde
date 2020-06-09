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

    // compound_score1();

    // debug_score_tables();
    // Sample_Score1_tables();
    // ss_descending_inv_scales_tables();
    // ss_descending_inv_scales_b_tables();
    // ss_non_even_scaling_tables();
    // ss_non_even_scaling_asc_tables();

    compound_score1_tables();
}
