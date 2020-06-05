/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=36;
int zoom_notehigh=84;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=7;


void renderScore(){
     freemotif_1 = new freeMotif(simpleforms_1);
     freemotif_1.renderfreemotif();
     
     freemotif_1.updatetimeoffset(3.25);
     freemotif_1.renderfreemotif();
}
