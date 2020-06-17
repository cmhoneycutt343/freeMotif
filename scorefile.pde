/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=24;
int zoom_notehigh=96;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=95;

// List of Instruments at http://explodingart.com/soundcipher/doc/arb/soundcipher/constants/ProgramChanges.html
float[] inst_cata = {score.PIANO,score.PIANO,score.PIANO,score.PIANO};

void globalscoresetup(){
      setcurrentkey(0);
}

void renderScore(){
    //*-------function examples--------*//
    // basics_examples();
    // renderproperties_example();
    // retrograde_examples();
    // fragmentation_examples();
    // concat_examples();
    fugue_examples();
}
