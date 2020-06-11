/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=36;
int zoom_notehigh=96;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=31;

// List of Instruments at http://explodingart.com/soundcipher/doc/arb/soundcipher/constants/ProgramChanges.html
float[] inst_cata = {score.BELL,score.CELLO,score.PIANO,score.MUSIC_BOX};

void globalscoresetup(){
      setcurrentkey(0);
}

void renderScore(){
    //*-------function examples--------*//
    //basics_examples();
    //retrograde_examples();
    //fragmentation_examples();
    concat_examples();
}
