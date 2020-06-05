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
freeMotif freemotif_1;
freeMotif freemotif_2;
freeMotif freemotif_3;
freeMotif freemotif_4;


/*-------------SCORE VARIABLES------------*/
// score length in measures
int score_length=16;
// score bpm
int score_bpm=120;

/*-----------KEY SIG Variables--------------*/
float[] scalebuffer = {0, 0, 0, 0, 0, 0, 0};

/*---------GUI VARIABLES------------*/
int gridline_y_minor=color(82, 82, 82);
int gridline_y_major=color(126, 126, 126);
int gridline_middleC=color(128, 0, 0);
int gridline_tonic=color(82, 0, 0);

int gridline_x_minor=color(12, 12, 12);
int gridline_x_major=color(82, 82, 82);
int gridline_4bars=color(0, 82, 0);

int note_color_a=color(120, 0, 0);

int gwidth = 640;
int gheight = 640;

float stretchpixwidth;
float ulcornx;
float brcornx;

float stretchpixheight;
float ulcorny;
float brcorny;

float noteyheight = gheight/128;
float quarternotexwidth = gwidth/128;

//End globals



public void setup() {
  
  background(0, 0, 0);
  colorMode(RGB, 256);

  globalscoresetup();
  renderScore();

  score.tempo(score_bpm);
  score.play();
}

public void draw() {
}
public void drawGUI(){
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
/*------MOTIF / CLASS VARIABLES---*/
// { {pitch <diatonic reference>, start position, duration, velocity, timbre A, timbre B} }
//simple form 1
float[][] simpleforms_1 =
{{ 0, 0.00f, .375f, 127, 0, 0},
{ 2, .375f, .375f, 127, 0, 0},
{-2, .750f, .250f, 127, 0, 0}};

float[][] simpleforms_2 =
{{ 0, 0.00f, .375f, 127, 0, 0},
{1, .375f, .375f, 127, 0, 0},
{2, .750f, .250f, 127, 0, 0}};

float[][] arch1_mm =
{{ 0, 0.00f, .25f, 127, 0, 0},
{ 4, 1/4.0f, .25f, 127, 0, 0},
{ 2, 2/4.0f, .25f, 127, 0, 0},
{ 7, 3/4.0f, .25f, 127, 0, 0}};

float[][] even_ascent_mm =
{{ 0, 0.00f, .25f, 127, 0, 0},
{ 1, 1/4.0f, .25f, 127, 0, 0},
{ 2, 2/4.0f, .25f, 127, 0, 0},
{ 3, 3/4.0f, .25f, 127, 0, 0},
{ 4, 4/4.0f, .25f, 127, 0, 0},
{ 5, 5/4.0f, .25f, 127, 0, 0},
{ 6, 6/4.0f, .25f, 127, 0, 0},
{ 7, 7/4.0f, .25f, 127, 0, 0}};

/*---------'freeMotif Class'--------*/
class freeMotif{
  int noterenderindex;
  int numnotes;
  float[][] notearray;
  float pos_time=0;
  float pos_tonic=60;
  float diatonic_offset=0;
  float motif_length=1;
  float scale_time=1;
  float scale_dur=1;
  float scale_diatonic=1;
  float motif_inversion=1;
  float motif_retrograde=0;
  int frag_index=0;
  int frag_length;
  int inst_index=0;

  freeMotif(float[][] classinputmotif){
      notearray=classinputmotif;
      numnotes=notearray.length;
      frag_length=numnotes;
  }

  public void renderfreemotif(){
      drawgrid();

      //println(notearray.length);
      //println(notearray[0][0]);
      //println(notearray[0][1]);
      //println(notearray[0][2]);
      //println(notearray[0][3]);
      //println(notearray[0][4]);
      //println(notearray[0][5]);

      // { {pitch <diatonic reference>,
      //start position,
      //duration,
      //velocity,
      //timbre A,
      //timbre B} }
      for(noterenderindex=0; noterenderindex<frag_length ; noterenderindex++){
        float output_pitch = return_diaton(notearray[noterenderindex][0]*(motif_inversion)+diatonic_offset,pos_tonic);
        float output_pos = pos_time+notearray[noterenderindex][1]*scale_time;
        float output_dur = notearray[noterenderindex][2]*scale_dur;
        float output_vel = notearray[noterenderindex][3];
        float output_timb1 = notearray[noterenderindex][4];
        float output_timb2 = notearray[noterenderindex][5];

        //draw notes in GUI
        drawnote(output_pitch,
                output_pos,
                output_dur,
                output_vel,
                output_timb1,
                output_timb2,
                inst_index);

        //add notes to MIDI score
        score.addNote(output_pos,
                      output_pitch,
                      output_vel,
                      1);

        // end 'renderfreemotif' method
      }
  }

}
public void Sample_Score1(){
     freemotif_1 = new freeMotif(simpleforms_2);

     setcurrentkey(0);

     freemotif_1.pos_time=0.0f;
     freemotif_1.diatonic_offset=0;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=1.0f;
     freemotif_1.diatonic_offset=3;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=2.0f;
     freemotif_1.diatonic_offset=-1;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=3.0f;
     freemotif_1.diatonic_offset=2;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=4.0f;
     freemotif_1.diatonic_offset=-2;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=5.0f;
     freemotif_1.diatonic_offset=1;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=6.0f;
     freemotif_1.diatonic_offset=-3;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=7.0f;
     freemotif_1.diatonic_offset=0;
     freemotif_1.renderfreemotif();
}

public void ss_descending_inv_scales(){
    //Sample_Score1();

    freemotif_1 = new freeMotif(arch1_mm);
    freemotif_2 = new freeMotif(even_ascent_mm);
    freemotif_3 = new freeMotif(arch1_mm);
    freemotif_4 = new freeMotif(arch1_mm);

    freemotif_2.inst_index=1;
    freemotif_3.inst_index=2;
    freemotif_4.inst_index=3;

    freemotif_3.pos_tonic=12+freemotif_3.pos_tonic;
    freemotif_4.pos_tonic=12+freemotif_3.pos_tonic;
    freemotif_4.scale_time=8;
    freemotif_4.scale_dur=16;
    freemotif_4.motif_inversion=-1;

    for(int iter=0; iter<8; iter++)
    {
      freemotif_1.pos_time=0+iter*4;
      freemotif_1.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_1.diatonic_offset=7;
          freemotif_1.motif_inversion=-1;
      } else {
          freemotif_1.diatonic_offset=0;
          freemotif_1.motif_inversion=1;
      }
      freemotif_1.diatonic_offset=freemotif_1.diatonic_offset-(iter/2);
      freemotif_1.renderfreemotif();

      freemotif_2.pos_time=2+iter*4;
      freemotif_2.motif_inversion=-1;
      freemotif_2.diatonic_offset=-iter;
      freemotif_2.renderfreemotif();

      freemotif_3.pos_time=0+iter*4;
      freemotif_3.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_3.diatonic_offset=7;
          freemotif_3.motif_inversion=-1;
      } else {
          freemotif_3.diatonic_offset=0;
          freemotif_3.motif_inversion=1;
      }
      freemotif_3.diatonic_offset=freemotif_3.diatonic_offset-(iter/2);
      freemotif_3.renderfreemotif();

      freemotif_4.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_4.diatonic_offset=7;
          freemotif_4.motif_inversion=-1;
      } else {
          freemotif_4.diatonic_offset=0;
          freemotif_4.motif_inversion=1;
      }
      freemotif_4.diatonic_offset=freemotif_4.diatonic_offset-(iter/2);
      freemotif_4.renderfreemotif();

    }
}
public void globalscoresetup(){
      setcurrentkey(1);
}

public void renderScore(){
    // Sample_Score1();
    ss_descending_inv_scales();
}
/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=36;
int zoom_notehigh=100;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=31;

public float truemod(float input, float modulus)
{
      return ((input%modulus+modulus)%modulus);
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
  public void settings() {  size(640, 640, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "motifengine" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
