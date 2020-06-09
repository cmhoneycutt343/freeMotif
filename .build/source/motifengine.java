import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import arb.soundcipher.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class motifengine extends PApplet {



SoundCipher sc = new SoundCipher(this);
SCScore score = new SCScore();


/// Use traditional note legnth "quarter note, 1/2, etc" to define 'length' decimals

/*-----"FREE MOTIF" OBJECTS--------*/
freeMotif_table freemotif_table1obj;
freeMotif_table freemotif_table2obj;
freeMotif_table freemotif_table3obj;
freeMotif_table freemotif_table4obj;


/*-------------SCORE VARIABLES------------*/
// score length in measures
int score_length=16;
// score bpm
int score_bpm=200;

int global_time_render_offset=0;

/*-----------KEY SIG Variables--------------*/
float[] scalebuffer = {0, 0, 0, 0, 0, 0, 0};

public void setup() {
  
  background(0, 0, 0);
  colorMode(RGB, 256);

  loadMetamotifs();
  globalscoresetup();
  renderScore();

  score.addCallbackListener(this);

  score.tempo(score_bpm);
  score.repeat(5);
  score.play();

}

public void draw() {
}

public void handleCallbacks(int callbackID) {
    if(callbackID==1){
      score.stop();
      score.play();
    }
}
/*---------GUI VARIABLES------------*/
int gridline_y_minor=color(82, 82, 82);
int gridline_y_major=color(126, 126, 126);
int gridline_middleC=color(128, 0, 0);
int gridline_tonic=color(82, 0, 0);

int gridline_x_minor=color(12, 12, 12);
int gridline_x_major=color(82, 82, 82);
int gridline_4bars=color(0, 82, 0);

int note_color_a=color(120, 0, 0);

int gwidth = 1280;
int gheight = 640;

float stretchpixwidth;
float ulcornx;
float brcornx;

float stretchpixheight;
float ulcorny;
float brcorny;

float noteyheight = gheight/128;
float quarternotexwidth = gwidth/128;

public void drawGUI(){
  drawgrid();
  background(0,0,0);

  //DEBUG
  //drawnote(0,0.0,0.0,0,0,0,0);
}

public void drawgrid(){
  //pitch seperation grid
  for(int drawgrid_noteindex=0; drawgrid_noteindex<(zoom_notehigh-zoom_notelow+5); drawgrid_noteindex++){
      //duration in .25=quarternote

      //float gl_ulcornx=quarternotexwidth*drawgrid_noteindex;
      float stretchpixheight=gheight/(zoom_notehigh-zoom_notelow+1);
      float gl_ulcornx=gwidth-stretchpixheight*drawgrid_noteindex;

      //note seperator grid
      if((drawgrid_noteindex+zoom_notelow)==60){
        stroke(gridline_middleC);
        line(0,gl_ulcornx,gwidth,gl_ulcornx);
      }  else if(((drawgrid_noteindex+zoom_notelow)%12)==0){
        stroke(gridline_y_major);
        line(0,gl_ulcornx,gwidth,gl_ulcornx);
      }  else {
        stroke(gridline_y_minor);
        line(0,gl_ulcornx,gwidth,gl_ulcornx);
      }
    }

    //time seperation grid
    for(int drawgrid_timeindex=0; drawgrid_timeindex<PApplet.parseInt((zoom_stoptime+1)*4); drawgrid_timeindex++){

      //float gl_ulcorny=noteyheight*drawgrid_timeindex;
      float stretchpixwidth=gwidth/(zoom_stoptime-zoom_starttime+1)/4;
      float gl_ulcorny=stretchpixwidth*drawgrid_timeindex;

      if(((drawgrid_timeindex+zoom_starttime)%16)==0){
        stroke(gridline_4bars);
        line(gl_ulcorny,0,gl_ulcorny,gheight);
      }  else if(((drawgrid_timeindex+zoom_starttime)%4)==0){
        stroke(gridline_y_major);
        line(gl_ulcorny,0,gl_ulcorny,gheight);
      }  else {
        stroke(gridline_y_minor);
        line(gl_ulcorny,0,gl_ulcorny,gheight);
      }
    }

}

public void drawnote(float pitch, float start_pos, float dur, float velo, float timbA, float timbB, int inst) {

    /***DEBUG; 'i' will be subbed in for MIDI#number*/

    colorMode(HSB);
    int note_color_byinst=color((inst*100)%255,128,128);
    fill(note_color_byinst);

    //duration in .25=quarternote

    ///*--unstretch proto-typed render--
    //float ulcornx=noteyheight*start_pos;
    //float ulcorny=gheight-quarternotexwidth*(pitch+1);
    //float brcornx=ulcornx+noteyheight;
    //float brcorny=ulcorny+quarternotexwidth;
    //rectMode(CORNERS);
    //rect(ulcornx,ulcorny,brcornx,brcorny);
    //*/

    ///*--unstretch proto-typed render--
    stretchpixwidth=gwidth/((zoom_stoptime-zoom_starttime+1)*4);
    ulcornx=(start_pos)*stretchpixwidth*4-(zoom_starttime)*stretchpixwidth;
    brcornx=ulcornx+(stretchpixwidth*dur*4);

    stretchpixheight=gheight/(zoom_notehigh-zoom_notelow+1);
    ulcorny=gheight-stretchpixheight*(pitch-zoom_notelow+1);
    brcorny=ulcorny+stretchpixheight;

    rectMode(CORNERS);
    rect(ulcornx,ulcorny,brcornx,brcorny);
    //*/



  colorMode(RGB);
}
/*---------'freeMotif Class'--------*/
class freeMotif_table{

  //counter variable for scanning / rendering notes
  int noterenderindex;
  // number of notes in freemotif
  int numnotes;
  // Multidimensional array,
  // notearray[<# note in motif>,<attribute #>]
  // { {pitch <diatonic reference>,
  //    start position,
  //    duration,
  //    velocity,
  //    timbre A,
  //    timbre B} }

  /***********REMOVE*****/
  Table notearray_table;
  // motif position (for iterations relative to first iteration)
  /***********REMOVE*****/
  float pos_time=0;
  // MIDI note Tonic index (60= Middle C)
  float pos_tonic=60;
  // Diatonic Offset to shift motif up and down within scale
  float diatonic_offset=0;
  // ??? use for fragmentation?
  float motif_length;
  // scales motif in time
  float scale_time=1;
  // scales duration of note durations in motif
  float scale_dur=1;
  // scales diatonic values (-1) is an inversion
  float scale_diatonic=1;
  // Bool; 0=normal direction, 1 motif is reversed
  float motif_retrograde=1;
  // Fragmentation: index of first note fragment
  int frag_index=0;
  // Fragmentation: number of notes in frag
  int frag_length;
  // index of instrument
  int inst_index=0;

  freeMotif_table(Table classinputmotif_table){
      notearray_table=classinputmotif_table;
      numnotes=notearray_table.getRowCount();
      frag_length=numnotes;

  }

  public void nestedmethod(){
      println("test print in testmethod");
  }

  //Method is called to write motif to score / GUI
  public void renderfreemotif(){
      //drawgrid();

      nestedmethod();
      //println(notearray.length);
      //println(notearray[0][0]);
      //println(notearray[0][1]);
      //println(notearray[0][2]);
      //println(notearray[0][3]);
      //println(notearray[0][4]);
      //println(notearray[0][5]);

      // { {pitch <diatonic reference>,
      //    start position,
      //    duration,
      //    velocity,
      //    timbre A,
      //    timbre B} }
      for(int notescan_abspos=0; notescan_abspos<frag_length ; notescan_abspos++){
        //experiment --- will redefining 'noterenderindex'

        // motif_retrograde
        //noterenderindex=abs(int(((frag_length-1)*motif_retrograde)-noterenderindex));
        // if(motif_retrograde==0)
        // {
        //   noterenderindex = notescan_abspos;
        // } else if(motif_retrograde==1)  {
        //   noterenderindex = notescan_abspos;
        // }

        noterenderindex = notescan_abspos;

        float output_pitch = return_diaton(notearray_table.getFloat(notescan_abspos, "pitch")*(scale_diatonic)+diatonic_offset,pos_tonic);
        float output_pos = global_time_render_offset+pos_time+notearray_table.getFloat(notescan_abspos, "time_pos")*scale_time;
        float output_dur = notearray_table.getFloat(notescan_abspos, "duration")*scale_dur;
        float output_vel = notearray_table.getFloat(notescan_abspos, "velocity");
        float output_timb1 = notearray_table.getFloat(notescan_abspos, "timbre1");
        float output_timb2 = notearray_table.getFloat(notescan_abspos, "timbre2");






        //draw notes in GUI
        drawnote(output_pitch,
                output_pos,
                output_dur,
                output_vel,
                output_timb1,
                output_timb2,
                inst_index);


        //add notes to MIDI score

        //score.addNote(startTime,
        //              channel,
        //              instrument,
        //              pitch,
        //              dynamic,
        //              duration,
        //              articulation,
        //               pan);

        score.addNote(output_pos,
                      inst_index,
                      inst_cata[inst_index],
                      output_pitch,
                      output_vel,
                      output_dur,
                      127,
                      64);


        //add noteOffs
        score.addNote(output_pos+output_dur,
                      inst_index,
                      inst_cata[inst_index],
                      output_pitch,
                      0,
                      output_dur,
                      127,
                      64);
        /******************/

        // end 'renderfreemotif' method
      }

      /******************
      printArray(notearray);
      /******************/
  }
}
/*------MOTIF / CLASS VARIABLES---*/
// { {pitch <diatonic reference>, start position, duration, velocity, timbre A, timbre B} }
//simple form 1
float[][] simpleforms_1 =
{{ 0, 0.00f, .375f, 127, 0, 0},
{ 2, .375f, .375f, 127, 0, 0},
{-2, .750f, .250f, 127, 0, 0}};

Table simpleforms_table1;

float[][] simpleforms_2 =
{{ 0, 0.00f, .375f, 127, 0, 0},
{1, .375f, .375f, 127, 0, 0},
{2, .750f, .250f, 127, 0, 0}};

Table simpleforms_table2;

float[][] arch1_mm =
{{ 0, 0.00f, .25f, 127, 0, 0},
{ 4, 1/4.0f, .25f, 127, 0, 0},
{ 2, 2/4.0f, .25f, 127, 0, 0},
{ 7, 3/4.0f, .25f, 127, 0, 0}};

Table arch1_mm_table;

float[][] even_ascent_mm =
{{ 0, 0.00f, .25f, 127, 0, 0},
{ 1, 1/4.0f, .25f, 127, 0, 0},
{ 2, 2/4.0f, .25f, 127, 0, 0},
{ 3, 3/4.0f, .25f, 127, 0, 0},
{ 4, 4/4.0f, .25f, 127, 0, 0},
{ 5, 5/4.0f, .25f, 127, 0, 0},
{ 6, 6/4.0f, .25f, 127, 0, 0},
{ 7, 7/4.0f, .25f, 127, 0, 0}};

Table even_ascent_mm_table;

public void loadMetamotifs(){
      simpleforms_table1 = loadTable("simpleforms_1.csv", "header");
      simpleforms_table2 = loadTable("simpleforms_2.csv", "header");
      arch1_mm_table = loadTable("arch1_mm.csv", "header");
      even_ascent_mm_table = loadTable("even_ascent_mm.csv", "header");
}
public void debug_score_tables_beta(){

  int starting_time_offset=global_time_render_offset;


    freemotif_table1obj = new freeMotif_table(simpleforms_table1);
    freemotif_table1obj.scale_time=4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=1;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=1;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

  setcurrentkey(0);

}




public void debug_score_tables(){

  int starting_time_offset=global_time_render_offset;

  for(int iter=0; iter<17; iter++)
  {
    freemotif_table1obj = new freeMotif_table(simpleforms_table1);
    freemotif_table1obj.scale_time=2;
    //freemotif_table1obj.pos_time=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table2obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj.inst_index=1;
    freemotif_table2obj.scale_time=1/3;
    if(iter<13){
      freemotif_table2obj.diatonic_offset=freemotif_table1obj.diatonic_offset+12+((iter-1)/4);
    } else {
      freemotif_table2obj.diatonic_offset=freemotif_table1obj.diatonic_offset+12+2;
    }
    freemotif_table2obj.renderfreemotif();

    global_time_render_offset=starting_time_offset+iter*4;
  }

  setcurrentkey(0);


}










public void Sample_Score1_tables(){
     //freemotif_table1obj = new freeMotif(simpleforms_2);
     freemotif_table1obj = new freeMotif_table(simpleforms_table2);

     setcurrentkey(0);

     freemotif_table1obj.pos_time=0.0f;
     freemotif_table1obj.diatonic_offset=0;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=1.0f;
     freemotif_table1obj.diatonic_offset=3;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=2.0f;
     freemotif_table1obj.diatonic_offset=-1;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=3.0f;
     freemotif_table1obj.diatonic_offset=2;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=4.0f;
     freemotif_table1obj.diatonic_offset=-2;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=5.0f;
     freemotif_table1obj.diatonic_offset=1;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=6.0f;
     freemotif_table1obj.diatonic_offset=-3;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=7.0f;
     freemotif_table1obj.diatonic_offset=0;
     freemotif_table1obj.renderfreemotif();
}

public void ss_descending_inv_scales_tables(){
    //Sample_Score1();
    //repeat at measure 32
    // score.addCallback(32, 1);

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table3obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.scale_time=8;
    freemotif_table4obj.scale_dur=16;
    freemotif_table4obj.scale_diatonic=-1;

    for(int iter=0; iter<8; iter++)
    {

      if(iter==6){
        setcurrentkey(2);
      } else {
        setcurrentkey(1);
      }

      freemotif_table1obj.pos_time=0+iter*4;
      freemotif_table1obj.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_table1obj.diatonic_offset=7;
          freemotif_table1obj.scale_diatonic=-1;
      } else {
          freemotif_table1obj.diatonic_offset=0;
          freemotif_table1obj.scale_diatonic=1;
      }
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset-(iter/2);
      freemotif_table1obj.renderfreemotif();

      freemotif_table2obj.pos_time=2+iter*4;
      if(iter==(2)||iter==(4)||iter==(6)) {
        freemotif_table2obj.scale_diatonic=1;
      } else {
        freemotif_table2obj.scale_diatonic=-1;
      }
      freemotif_table2obj.diatonic_offset=-iter;
      freemotif_table2obj.renderfreemotif();

      freemotif_table3obj.pos_time=0+iter*4;
      freemotif_table3obj.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_table3obj.diatonic_offset=7;
          freemotif_table3obj.scale_diatonic=-1;
      } else {
          freemotif_table3obj.diatonic_offset=0;
          freemotif_table3obj.scale_diatonic=1;
      }
      freemotif_table3obj.diatonic_offset=freemotif_table3obj.diatonic_offset-(iter/2);
      freemotif_table3obj.renderfreemotif();

      freemotif_table4obj.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_table4obj.diatonic_offset=7;
          freemotif_table4obj.scale_diatonic=-1;
      } else {
          freemotif_table4obj.diatonic_offset=0;
          freemotif_table4obj.scale_diatonic=1;
      }
      freemotif_table4obj.diatonic_offset=freemotif_table4obj.diatonic_offset-(iter/2);
      freemotif_table4obj.renderfreemotif();

    }
}

public void ss_descending_inv_scales_b_tables(){
    //Sample_Score1();
    //repeat at measure 32
    // score.addCallback(32, 1);

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table3obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.scale_time=8;
    freemotif_table4obj.scale_dur=16;
    freemotif_table4obj.scale_diatonic=-1;

    for(int iter=0; iter<8; iter++)
    {

      if(iter==6){
        setcurrentkey(2);
      } else {
        setcurrentkey(1);
      }

      freemotif_table1obj.pos_time=0+iter*4;
      freemotif_table1obj.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_table1obj.diatonic_offset=7;
          freemotif_table1obj.scale_diatonic=-1;
      } else {
          freemotif_table1obj.diatonic_offset=0;
          freemotif_table1obj.scale_diatonic=1;
      }
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset-(iter/2);
      freemotif_table1obj.renderfreemotif();
      freemotif_table1obj.pos_time=2+iter*4;
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset+7;
      freemotif_table1obj.renderfreemotif();
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset-7;









      freemotif_table2obj.pos_time=2+iter*4;
      if(iter==(2)||iter==(4)||iter==(6)) {
        freemotif_table2obj.scale_diatonic=1;
      } else {
        freemotif_table2obj.scale_diatonic=-1;
      }
      freemotif_table2obj.diatonic_offset=-iter;
      freemotif_table2obj.renderfreemotif();

      freemotif_table3obj.pos_time=0+iter*4;
      freemotif_table3obj.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_table3obj.diatonic_offset=7;
          freemotif_table3obj.scale_diatonic=-1;
      } else {
          freemotif_table3obj.diatonic_offset=0;
          freemotif_table3obj.scale_diatonic=1;
      }
      freemotif_table3obj.diatonic_offset=freemotif_table3obj.diatonic_offset-(iter/2);
      freemotif_table3obj.renderfreemotif();

      freemotif_table4obj.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_table4obj.diatonic_offset=7;
          freemotif_table4obj.scale_diatonic=-1;
      } else {
          freemotif_table4obj.diatonic_offset=0;
          freemotif_table4obj.scale_diatonic=1;
      }
      freemotif_table4obj.diatonic_offset=freemotif_table4obj.diatonic_offset-(iter/2);
      freemotif_table4obj.renderfreemotif();

    }
}

public void ss_non_even_scaling_tables(){
  //Sample_Score1();

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(arch1_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table1obj.diatonic_offset = 0;
    freemotif_table2obj.diatonic_offset = -2;
    freemotif_table3obj.diatonic_offset = 0;
    freemotif_table4obj.diatonic_offset = 0;

    freemotif_table1obj.pos_time = 0;
    freemotif_table2obj.pos_time = 1;
    freemotif_table3obj.pos_time = 2;
    freemotif_table4obj.pos_time = 3;

    freemotif_table1obj.scale_time = 1;
    freemotif_table2obj.scale_time = 2;
    freemotif_table3obj.scale_time = 4;
    freemotif_table4obj.scale_time = 8;

    freemotif_table1obj.scale_dur = 1;
    freemotif_table2obj.scale_dur = 2;
    freemotif_table3obj.scale_dur = 4;
    freemotif_table4obj.scale_dur = 8;

    freemotif_table1obj.renderfreemotif();
    freemotif_table2obj.renderfreemotif();
    freemotif_table3obj.renderfreemotif();
    freemotif_table4obj.renderfreemotif();

    freemotif_table3obj.pos_time = 10;
    freemotif_table3obj.renderfreemotif();

    freemotif_table2obj.pos_time = 13;
    freemotif_table2obj.renderfreemotif();

    freemotif_table1obj.pos_time = 14;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 15;
    freemotif_table1obj.scale_time =0;
    freemotif_table1obj.renderfreemotif();
}

public void ss_non_even_scaling_asc_tables(){
    //Sample_Score1();

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(arch1_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table1obj.diatonic_offset = 0;
    freemotif_table2obj.diatonic_offset = 1;
    freemotif_table3obj.diatonic_offset = 2;
    freemotif_table4obj.diatonic_offset = 3;

    freemotif_table1obj.pos_time = 0;
    freemotif_table2obj.pos_time = 1;
    freemotif_table3obj.pos_time = 2;
    freemotif_table4obj.pos_time = 3;

    freemotif_table1obj.scale_time = 1;
    freemotif_table2obj.scale_time = 2/3;
    freemotif_table3obj.scale_time = 1/3;
    freemotif_table4obj.scale_time = 4/3;

    freemotif_table1obj.renderfreemotif();
    freemotif_table2obj.renderfreemotif();
    freemotif_table3obj.renderfreemotif();
    freemotif_table4obj.renderfreemotif();

    freemotif_table1obj.pos_time = 4;
    freemotif_table1obj.scale_time = 0;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table2obj.pos_time = 5;
    freemotif_table2obj.diatonic_offset = 6;
    freemotif_table2obj.renderfreemotif();

    freemotif_table1obj.pos_time = 6;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 7;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 7.5f;
    freemotif_table1obj.diatonic_offset = 2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 8;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();
}

public void compound_score1_tables(){
    // Sample_Score1();
    score.addCallback(94,1);

    global_time_render_offset=0;
    // ss_descending_inv_scales2();
    ss_non_even_scaling_asc_tables();


    global_time_render_offset=8;
    ss_descending_inv_scales_tables();


    global_time_render_offset=8+32;
    ss_descending_inv_scales_b_tables();

    global_time_render_offset=44+32;
    ss_non_even_scaling_tables();
}
/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=32;
int zoom_notehigh=100;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=93;

// List of Instruments at http://explodingart.com/soundcipher/doc/arb/soundcipher/constants/ProgramChanges.html
float[] inst_cata = {score.BELL,score.CELLO,score.VIOLIN,score.MUSIC_BOX};

public void globalscoresetup(){
      setcurrentkey(1);
}

public void renderScore(){

    // compound_score1();

    // debug_score_tables();
    // Sample_Score1_tables();
    // ss_descending_inv_scales_tables();
    // ss_descending_inv_scales_b_tables();
    // ss_non_even_scaling_tables();
    // ss_non_even_scaling_asc_tables();

    compound_score1_tables();
}

public float truemod(float input, float modulus)
{
      return ((input%modulus+modulus)%modulus);
}

public void printArray(float[][] a)
{
  println("############ Array ############");
  for(int i=0; i < a.length; i ++){
    for(int j = 0; j < a[i].length; j++){
     print(" " + a[i][j]);
    }
    println();
  }
}

public void printTable(Table input_table)
{
      //scan notes in table
      for(int tab_print_iter=0;tab_print_iter<input_table.getRowCount();tab_print_iter++){

          //
          print("pitch: ");
          print(input_table.getFloat(tab_print_iter, "pitch"));
          print(" - ");

          print("time_pos: ");
          print(input_table.getFloat(tab_print_iter, "time_pos"));
          print(" - ");

          print("duration: ");
          print(input_table.getFloat(tab_print_iter, "duration"));
          print(" - ");

          print("velocity: ");
          print(input_table.getFloat(tab_print_iter, "velocity"));
          print(" - ");

          print("timbre1: ");
          print(input_table.getFloat(tab_print_iter, "timbre1"));
          print(" - ");

          print("timbre2: ");
          print(input_table.getFloat(tab_print_iter, "timbre2"));
          print(" - ");
          println();
      }
      println("---------------");
}

public void setcurrentkey(int keyindex)
{
    switch(keyindex){
      case 0:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.MAJOR[k];
        }
        break;
      case 1:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.MINOR[k];
        }
        break;
      case 2:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.HARMONIC_MINOR[k];
        }
        break;
      case 3:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.HARMONIC_MINOR[k];
        }
        break;
      default:
        break;
    }
}

public float return_diaton(float dia_degree_in, float tonic_offset)
{
    // float currentnote = tonic_offset+scalebuffer[int(truemod(dia_degree_in+degreebuffer[k], 7))];
    // float currentoctave = (floor((pitches[i]+degreebuffer[k])/7))*12;
    float calc_diatonic = tonic_offset + scalebuffer[PApplet.parseInt(truemod(dia_degree_in,7.0f))];
    float calc_octave_factor = (floor((dia_degree_in)/7))*12;
    return calc_diatonic+calc_octave_factor;
}
  public void settings() {  size(1280, 640, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "motifengine" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
