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
int score_bpm=180;

int global_time_render_offset=0;

/*-----------KEY SIG Variables--------------*/
float[] scalebuffer = {0, 0, 0, 0, 0, 0, 0};

public void setup() {
  
  background(0, 0, 0);
  colorMode(RGB, 256);

  loadMetamotifs();
  globalscoresetup();
  drawBackground();
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
int gridline_y_minor=color(16, 16, 16);
int gridline_y_major=color(32, 32, 32);
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

public void drawBackground(){
  background(0,0,0);
  drawgrid();

  //DEBUG
  //drawnote(0,0.0,0.0,0,0,0,0);
}

public void drawgrid(){
  //pitch seperation grid
  for(int drawgrid_noteindex=0; drawgrid_noteindex<129; drawgrid_noteindex++){
      //duration in .25=quarternote

      //float gl_ulcornx=quarternotexwidth*drawgrid_noteindex;
      float stretchpixheight=gheight/(zoom_notehigh-zoom_notelow+1);
      float gl_ulcornx=gheight-stretchpixheight*drawgrid_noteindex;

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
    int note_color_byinst=color((inst*100)%255,128,velo);
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

    // textSize(10);
    // text(pitch, ulcornx+.5*stretchpixheight, ulcorny-.5*stretchpixwidth);

    //*/



  colorMode(RGB);
}

public void addGUIcom(float pitch, float start_pos, int inst, String inputcomment ) {

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
    // brcornx=ulcornx+(stretchpixwidth*dur*4);

    stretchpixheight=gheight/(zoom_notehigh-zoom_notelow+1);
    ulcorny=gheight-stretchpixheight*(pitch-zoom_notelow+1);
    // brcorny=ulcorny+stretchpixheight;

    textSize(14);
    text(inputcomment, ulcornx+.5f*stretchpixheight, ulcorny+4.5f*stretchpixwidth);

    //*/



  colorMode(RGB);
}

public void scoreTitle(String titletext){

  textSize(28);
  text(titletext, 20, 20);

  //*/

  colorMode(RGB);
}
/*---------'freeMotif Class'--------*/
class freeMotif_table{

  String motif_name="default";
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
  // motif length in time (not number of notes)?
  float motif_length;
  // scales motif in time
  float scale_time=1;
  // scales duration of note durations in motif
  float scale_dur=1;
  // scales diatonic values (-1) is an inversion
  float scale_diatonic=1;

  // Bool; 0=normal direction, 1 motif is reversed
  float tonal_retrograde=0;
  // Bool; 0=normal direction, 1 motif is reversed
  float duration_retrograde=0;
  // Bool; 0=normal direction, 1 motif is reversed
  float position_retrograde=0;
  // Bool; 0=normal direction, 1 motif is reversed
  float velocity_retrograde=0;


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

      //calculate motif length ()
      motif_length=notearray_table.getFloat((frag_length-1),"time_pos")+notearray_table.getFloat((frag_length-1),"duration");
      println(motif_length);
  }

  //Method is called to write motif to score / GUI
  public void renderfreemotif(){
      //drawgrid();

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
      for(int notescan_abspos=frag_index; notescan_abspos<frag_length ; notescan_abspos++){


        /*-------start rendering calcuations--------*/
        noterenderindex = notescan_abspos;

        //factor in 'tonal_retrograde'
        int tablepitch_index;
        if(tonal_retrograde==0){
          tablepitch_index = notescan_abspos;
        } else {
          tablepitch_index = frag_length-1-notescan_abspos;
        }

        //factor in 'duration-retrograde'
        int tabledur_index = notescan_abspos;
        if(duration_retrograde==0){
          tabledur_index = notescan_abspos;
        } else {
          tabledur_index = frag_length-1-notescan_abspos;
        }

        //relative position of note in riff
        float tablepos_fromindex;
        float fragmentationoffset = notearray_table.getFloat(frag_index, "time_pos");
        if(position_retrograde==0){
          tablepos_fromindex = notearray_table.getFloat(notescan_abspos, "time_pos");
        } else {
          if(notescan_abspos==0)
          {
            tablepos_fromindex = 0;
          } else{
            tablepos_fromindex = motif_length-notearray_table.getFloat((frag_length-notescan_abspos)%frag_length, "time_pos");
          }
        }

        int tablevel_index;
        if(velocity_retrograde==0){
          tablevel_index = notescan_abspos;
        } else {
          tablevel_index = frag_length-1-notescan_abspos;
        }

        //calculate overall diatonic degree
        float diatonic_degree = (notearray_table.getFloat(tablepitch_index, "pitch"))*(scale_diatonic)+diatonic_offset;

        float output_pitch = return_diaton(diatonic_degree,pos_tonic);

        //absolute time (relative to score)
        float output_pos = (global_time_render_offset+pos_time+tablepos_fromindex*scale_time)-fragmentationoffset;

        float output_dur = notearray_table.getFloat(tabledur_index, "duration")*scale_dur;
        float output_vel = notearray_table.getFloat(tablevel_index, "velocity");
        float output_timb1 = notearray_table.getFloat(notescan_abspos, "timbre1");
        float output_timb2 = notearray_table.getFloat(notescan_abspos, "timbre2");
        /*-------end rendering calcuations--------*/


        if(notescan_abspos==frag_index)
        {
          addGUIcom(output_pitch,output_pos,inst_index,motif_name);
        }



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

Table simpleforms_1_table;

float[][] simpleforms_2 =
{{ 0, 0.00f, .375f, 127, 0, 0},
{1, .375f, .375f, 127, 0, 0},
{2, .750f, .250f, 127, 0, 0}};

Table simpleforms_2_table;

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

Table fanfare_table;

public void loadMetamotifs(){
      simpleforms_1_table = loadTable("simpleforms_1.csv", "header");
      simpleforms_2_table = loadTable("simpleforms_2.csv", "header");
      arch1_mm_table = loadTable("arch1_mm.csv", "header");
      even_ascent_mm_table = loadTable("even_ascent_mm.csv", "header");
      fanfare_table = loadTable("fanfare.csv","header");
}
public void retrograde_examples(){
      //retrograde function tester
      //0. Dry
      //1. Tonal retrograde
      //2. Rhythmic retrograde
      //3. Full retrograde
      scoreTitle("Retrograde Examples");

      score.addCallback(32, 1);

      // freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
      // freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
      // freemotif_table1obj = new freeMotif_table(arch1_mm_table);
      // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
      freemotif_table1obj = new freeMotif_table(fanfare_table);

      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.scale_time=4;
      freemotif_table1obj.scale_dur=4;
      freemotif_table1obj.motif_name="original";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.duration_retrograde=1;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="duration";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=1;
      freemotif_table1obj.motif_name="position";
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.tonal_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.motif_name="pitch";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.tonal_retrograde=0;
      freemotif_table1obj.velocity_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="velocity";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.tonal_retrograde=1;
      freemotif_table1obj.velocity_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="pitch+vel";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.duration_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="pitch+vel+dur";
      freemotif_table1obj.renderfreemotif();

}

public void basics_examples(){
      //retrograde function tester
      //0. Dry
      //1. Tonal retrograde
      //2. Rhythmic retrograde
      //3. Full retrograde
      scoreTitle("Basics Examples");

      score.addCallback(32, 1);

      freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
      freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
      freemotif_table1obj = new freeMotif_table(arch1_mm_table);
      freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
      // freemotif_table1obj = new freeMotif_table(fanfare_table);

      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.scale_time=2;
      freemotif_table1obj.scale_dur=2;
      freemotif_table1obj.motif_name="original";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.diatonic_offset=-1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> diatonic -1";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic+1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> chromatic +1";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.scale_time=freemotif_table1obj.scale_time*2;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> timescale *2";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.scale_time=freemotif_table1obj.scale_time/2;
      freemotif_table1obj.scale_diatonic=2;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+8;
      freemotif_table1obj.motif_name="-> scale diatonically *2";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic+24;
      freemotif_table1obj.scale_diatonic=freemotif_table1obj.scale_diatonic*-1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> chrom +24 / Inv";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic-12;
      freemotif_table1obj.motif_name="-> chrom -12";
      freemotif_table1obj.renderfreemotif();
}

public void fragmentation_examples(){
      //retrograde function tester
      //0. Dry
      //1. Tonal retrograde
      //2. Rhythmic retrograde
      //3. Full retrograde
      scoreTitle("Fragmentation Examples");

      score.addCallback(32, 1);

      freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
      freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
      freemotif_table1obj = new freeMotif_table(arch1_mm_table);
      // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
      // freemotif_table1obj = new freeMotif_table(fanfare_table);

      freemotif_table1obj.motif_name="original";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=1;
      freemotif_table1obj.motif_name="index from second note";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=2;
      freemotif_table1obj.motif_name="index from third note";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=0;
      freemotif_table1obj.frag_length=freemotif_table1obj.frag_length-1;
      freemotif_table1obj.motif_name="ignore last note";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=0;
      freemotif_table1obj.frag_length=freemotif_table1obj.frag_length-1;
      freemotif_table1obj.motif_name="ignore last 2 notes";
      freemotif_table1obj.renderfreemotif();
}
/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=36;
int zoom_notehigh=96;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=31;

// List of Instruments at http://explodingart.com/soundcipher/doc/arb/soundcipher/constants/ProgramChanges.html
float[] inst_cata = {score.BELL,score.CELLO,score.VIOLIN,score.MUSIC_BOX};

public void globalscoresetup(){
      setcurrentkey(0);
}

public void renderScore(){
    //*-------function examples--------*//
    // basics_examples();
    // retrograde_examples();
    fragmentation_examples();
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

public float return_diaton(float dia_degree_in, float tonic_offset){

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
