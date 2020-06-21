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
int score_bpm=100;

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
  score.writeMidiFile("/Users/charleshoneycutt/Desktop/freemotiftemp.mid");
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
class freeMotif_table {

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
/***********REMOVE*****/
// motif position (for iterations relative to first iteration)
float pos_time=0;
// MIDI note Tonic index (60= Middle C)
float pos_tonic=59;
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
int frag_numnotes;
// Fragmentation: fragment length in time
float frag_length;
// index of instrument
int inst_index=0;

/*---note render variables---*/
float diatonic_degree;
//score pitch (calculate chromatic; apply tonic)
float output_pitch;

// pitch property transformations
float relative_pos;
//score time (relative to score)
float output_pos;

float output_dur;
float output_vel;
float output_timb1;
float output_timb2;


freeMotif_table(Table classinputmotif_table){
        //notearray_table=classinputmotif_table;

        notearray_table = new Table();

        notearray_table.addColumn("pitch");
        notearray_table.addColumn("time_pos", Table.FLOAT);
        notearray_table.addColumn("duration");
        notearray_table.addColumn("velocity");
        notearray_table.addColumn("timbre1");
        notearray_table.addColumn("timbre2");


        for (int load_scan_iter=0; load_scan_iter<classinputmotif_table.getRowCount(); load_scan_iter++) {
                TableRow rowbuffer = classinputmotif_table.getRow(load_scan_iter);
                notearray_table.addRow(rowbuffer);
        }

        // println("notearray_table.getFloat(0,pitch)");
        // println(notearray_table.getFloat(0,"pitch"));

        // println("post 'classinputmotif_table' loading to 'notearray_table'");
        // print_mm();

        notearray_table.sort("time_pos");

        numnotes=notearray_table.getRowCount();
        frag_numnotes=numnotes;

        //calculate motif length ()
        motif_length=notearray_table.getFloat((frag_numnotes-1),"time_pos")+notearray_table.getFloat((frag_numnotes-1),"duration");

        // println(motif_name,"motif_length");
        // println(motif_length);
}

//called to write motif to score / GUI
public void renderfreemotif(){
        //drawgrid();

        // { {pitch <diatonic reference>,
        //    start position,
        //    duration,
        //    velocity,
        //    timbre A,
        //    timbre B} }

        notearray_table.sort("time_pos");

        //scan all notes in fragment
        for(int notescan_abspos=frag_index; notescan_abspos<frag_numnotes; notescan_abspos++) {


                /*-------generate note rendering values--------*/
                generate_noterender_vals(notescan_abspos);


                /*-------start gui generation--------*/
                if((notescan_abspos==frag_index)&&(motif_name!="default"))
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
                /*-------end gui generation--------*/

                /*-------end score writing generation--------*/
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
                /*-------start score writing--------*/

                // end 'renderfreemotif' method
        }

        /******************/
        // println("***********");
        // print("motif_name ");
        // println(motif_name);
        // print("frag_numnotes");
        // println(frag_numnotes);
        //print_mm();
        /******************/
}

//called generate output / post property transform values
public void generate_noterender_vals(int notescan_abspos_fcnin){

      //calculate fragment length in time
      frag_length=(notearray_table.getFloat((frag_numnotes-1),"time_pos")+notearray_table.getFloat((frag_numnotes-1),"duration"))-notearray_table.getFloat((frag_index),"time_pos");


      noterenderindex = notescan_abspos_fcnin;



      /*-------start retrograde-------*/
      //factor in 'tonal_retrograde'
      int tablepitch_index;
      if(tonal_retrograde==0) {
              tablepitch_index = noterenderindex;
      } else {
              tablepitch_index = frag_numnotes-1-noterenderindex;
      }

      //factor in 'duration-retrograde'
      int tabledur_index = noterenderindex;
      if(duration_retrograde==0) {
              tabledur_index = noterenderindex;
      } else {
              tabledur_index = frag_numnotes-1-noterenderindex;
      }

      //factor in 'time position_retrograde' (with fragmentation);
      float tablepos_fromindex;
      //fragmentation offset; time value of "first" element in fragment?

      float fragmentationoffset = notearray_table.getFloat(frag_index, "time_pos");
      // float fragmentationoffset = 0;

      //position retrograde not rendering correctly
      if(position_retrograde==0) {
              tablepos_fromindex = notearray_table.getFloat(noterenderindex, "time_pos");
      } else {
              if(noterenderindex==0)
              {
                      tablepos_fromindex = 0;
              } else{
                      tablepos_fromindex = motif_length-notearray_table.getFloat((frag_numnotes-noterenderindex)%frag_numnotes, "time_pos");
                      //
                          fragmentationoffset = motif_length-frag_length;


                      // println("in position_retrograde");
                      // println(motif_name);
                      // print("tablepos_fromindex: ");
                      // println(tablepos_fromindex);
                      // print("noterenderindex: ");
                      // println(noterenderindex);
              }
      }

      //factor in 'velocity_retrograde'
      int tablevel_index;
      if(velocity_retrograde==0) {
              tablevel_index = noterenderindex;
      } else {
              tablevel_index = frag_numnotes-1-noterenderindex;
      }

      // pitch property transformations
      diatonic_degree = (notearray_table.getFloat(tablepitch_index, "pitch"))*(scale_diatonic)+diatonic_offset;
      //debug print
      if(motif_name=="motif 1") {
        //println("tablepitch_index = frag_numnotes-1-noterenderindex;");
        print("tablepitch_index: ");
        println(tablepitch_index);
        // print("frag_numnotes: ");
        // println(frag_numnotes);
        print("noterenderindex: ");
        println(noterenderindex);

        print("diatonic_degree: ");
        println(diatonic_degree);
        println("");
      }

      //score pitch (calculate chromatic; apply tonic)
      output_pitch = return_diaton(diatonic_degree,pos_tonic);

      // pitch property transformations
      relative_pos = tablepos_fromindex*scale_time;
      //score time (relative to score)
      output_pos = (global_time_render_offset+pos_time+relative_pos)-fragmentationoffset;

      output_dur = notearray_table.getFloat(tabledur_index, "duration")*scale_dur;
      output_vel = notearray_table.getFloat(tablevel_index, "velocity");
      output_timb1 = notearray_table.getFloat(noterenderindex, "timbre1");
      output_timb2 = notearray_table.getFloat(noterenderindex, "timbre2");
      /*-------start retrograde-------*/
}

public void auto_retrograde(int bool_switch){
  velocity_retrograde=bool_switch;
  position_retrograde=bool_switch;
  duration_retrograde=bool_switch;
  tonal_retrograde=bool_switch;
}

//called to remove all properties
public void reset_properties(){
        // // motif position (for iterations relative to first iteration)
        // pos_time=0;
        // MIDI note Tonic index (60= Middle C)
        pos_tonic=60;
        // Diatonic Offset to shift motif up and down within scale
        diatonic_offset=0;
        // scales motif in time
        scale_time=1;
        // scales duration of note durations in motif
        scale_dur=1;
        // scales diatonic values (-1) is an inversion
        scale_diatonic=1;

        // Bool; 0=normal direction, 1 motif is reversed
        tonal_retrograde=0;
        // Bool; 0=normal direction, 1 motif is reversed
        duration_retrograde=0;
        // Bool; 0=normal direction, 1 motif is reversed
        position_retrograde=0;
        // Bool; 0=normal direction, 1 motif is reversed
        velocity_retrograde=0;


        // Fragmentation: index of first note fragment
        frag_index=0;
        // Fragmentation: number of notes in frag
        frag_numnotes=numnotes;
        // index of instrument
}

public void render_properties(){
    render_frag();

    Table buffertable = new Table();

    buffertable.addColumn("pitch");
    buffertable.addColumn("time_pos", Table.FLOAT);
    buffertable.addColumn("duration");
    buffertable.addColumn("velocity");
    buffertable.addColumn("timbre1");
    buffertable.addColumn("timbre2");

    //scan all notes in fragment
    for(int notescan_abspos=frag_index; notescan_abspos<frag_numnotes; notescan_abspos++){
        //generate note data
        generate_noterender_vals(notescan_abspos);

        // pitch property transformations
        // diatonic_degree = (notearray_table.getFloat(tablepitch_index, "pitch"))*(scale_diatonic)+diatonic_offset;
        buffertable.setFloat(notescan_abspos,"pitch",diatonic_degree);

        // time property transformations
        // relative_pos = tablepos_fromindex*scale_time;
        buffertable.setFloat(notescan_abspos,"time_pos",relative_pos);

        // output_dur = notearray_table.getFloat(tabledur_index, "duration")*scale_dur;
        buffertable.setFloat(notescan_abspos,"duration",output_dur);

        // output_vel = notearray_table.getFloat(tablevel_index, "velocity");
        buffertable.setFloat(notescan_abspos,"velocity",output_vel);

        // output_timb1 = notearray_table.getFloat(noterenderindex, "timbre1");
        buffertable.setFloat(notescan_abspos,"timbre1",output_timb1);

        // output_timb2 = notearray_table.getFloat(noterenderindex, "timbre2");
        buffertable.setFloat(notescan_abspos,"timbre2",output_timb2);
    }

    notearray_table.clearRows();

    for(int notescan_abspos=frag_index; notescan_abspos<frag_numnotes; notescan_abspos++){
          TableRow row = buffertable.getRow(notescan_abspos);

          notearray_table.addRow(row);
    }

    notearray_table.sort("time_pos");

    //calculate new motif length
    motif_length=notearray_table.getFloat((frag_numnotes-1),"time_pos")+notearray_table.getFloat((frag_numnotes-1),"duration");

    //reset properties
    reset_properties();
}

//concatenates new mm to note array
public void concat_mm(Table appendtable){
        //1. scanrows of new tabledur_index
        Table buffertable = new Table();
        int buffertablerowcount = appendtable.getRowCount();
        buffertable.addColumn("pitch");
        buffertable.addColumn("time_pos");
        buffertable.addColumn("duration");
        buffertable.addColumn("velocity");
        buffertable.addColumn("timbre1");
        buffertable.addColumn("timbre2");

        //add all rows from input table to new default table
        for (int rowiter=0; rowiter<buffertablerowcount; rowiter++) {
                buffertable.addRow(appendtable.getRow(rowiter));
        }


        // println("@after Table buffertable = appendtable;");
        //
        //
        // println(buffertablerowcount);
        // println("buffertablerowcount");

        // for (TableRow row : buffertable.rows()) {
        //
        //     float time_pos_shifted=row.getFloat("time_pos")+motif_length;
        //     row.setFloat("time_pos",time_pos_shifted);
        //     notearray_table.addRow(row);
        //     //println("@after notearray_table.addRow(row);");
        // }

        //add time shift (end concatenation) and add to existing table
        for (int rowiter=0; rowiter<buffertablerowcount; rowiter++) {

                TableRow row = buffertable.getRow(rowiter);

                float time_pos_shifted=row.getFloat("time_pos")+motif_length;
                // println(time_pos_shifted);
                // println("time_pos_shifted");
                row.setFloat("time_pos",time_pos_shifted);
                notearray_table.addRow(row);
        }

        // println("print_mm():concat_mm");
        // //print_mm();

        numnotes=notearray_table.getRowCount();
        frag_numnotes=numnotes;
        motif_length=notearray_table.getFloat((frag_numnotes-1),"time_pos")+notearray_table.getFloat((frag_numnotes-1),"duration");
        println(frag_numnotes);

        notearray_table.sort("time_pos");
}

//prepends new mm to note array
public void prepend_mm(Table appendtable){
        //Make Buffer Table
        Table buffertable = new Table();
        int buffertablerowcount = appendtable.getRowCount();
        buffertable.addColumn("pitch");
        buffertable.addColumn("time_pos");
        buffertable.addColumn("duration");
        buffertable.addColumn("velocity");
        buffertable.addColumn("timbre1");
        buffertable.addColumn("timbre2");
        for (int rowiter=0; rowiter<buffertablerowcount; rowiter++) {
                buffertable.addRow(appendtable.getRow(rowiter));
        }

        //time length of prepend_mm
        float prepend_mm_length = buffertable.getFloat((buffertablerowcount-1),"time_pos")
                                  +buffertable.getFloat(((buffertablerowcount-1)),"duration");

        // println("prepend_mm_length");
        // println(prepend_mm_length);


        // scan: add prepend_mm_length to all existing notes
        for (int rowiter=0; rowiter<numnotes; rowiter++) {
                //load buffer row
                TableRow row = notearray_table.getRow(rowiter);

                //calculate time shift
                float time_pos_shifted=row.getFloat("time_pos")+prepend_mm_length;

                //write to note
                notearray_table.setFloat(rowiter,"time_pos",time_pos_shifted);
        }

        //
        for (int rowiter=0; rowiter<buffertablerowcount; rowiter++) {
                //load buffer row
                TableRow row = buffertable.getRow(rowiter);
                // //calculate timeshift ( )
                // float time_pos_shifted=row.getFloat("time_pos")+motif_length;
                // println(time_pos_shifted);
                // println("time_pos_shifted");
                // row.setFloat("time_pos",time_pos_shifted);
                notearray_table.addRow(row);
        }

        //sort table

        numnotes=notearray_table.getRowCount();
        frag_numnotes=numnotes;
        motif_length=notearray_table.getFloat((frag_numnotes-1),"time_pos")+notearray_table.getFloat((frag_numnotes-1),"duration");
        // println(frag_numnotes);

        //notearray_table.setFloat(9,"time_pos",0.5);
        notearray_table.sort("time_pos");
        // println("print_mm: prepend_mm");
        //print_mm();
}

//'squeeze' maps input table onto object mm
public void subdivmap_mm(Table input_mm){
        //1. scanrows of new tabledur_index
        Table buffertable = new Table();
        int input_mm_numnotes = input_mm.getRowCount();
        float input_mm_length=input_mm.getFloat((input_mm_numnotes-1),"time_pos")+input_mm.getFloat((input_mm_numnotes-1),"duration");
        float section_offset=0;
        float section_length;
        float section_ratio;
        buffertable.addColumn("pitch");
        buffertable.addColumn("time_pos");
        buffertable.addColumn("duration");
        buffertable.addColumn("velocity");
        buffertable.addColumn("timbre1");
        buffertable.addColumn("timbre2");

        //each note (+silence to next note) of master mm becomes a 'section'
        //for each note in 'master' mm....
        for (int rowiter=0; rowiter<numnotes; rowiter++) {
              //1. calculate time width of note section (timepos[1]-timepos[0])
              if(rowiter==(0)){
                  section_length = notearray_table.getFloat((rowiter+1),"time_pos")-notearray_table.getFloat((rowiter),"time_pos");
              } else if (rowiter==(numnotes-1)){
                  section_offset = notearray_table.getFloat((rowiter),"time_pos")-notearray_table.getFloat((rowiter-1),"time_pos")+section_offset;
                  section_length = motif_length-notearray_table.getFloat((rowiter),"time_pos");
              } else {
                  section_offset = notearray_table.getFloat((rowiter),"time_pos")-notearray_table.getFloat((rowiter-1),"time_pos")+section_offset;
                  section_length = notearray_table.getFloat((rowiter+1),"time_pos")-notearray_table.getFloat((rowiter),"time_pos");
              }
              //how
              section_length=section_length;

              section_ratio=(section_length/motif_length);

              //debugprints: section offset
              // if(motif_name=="motif 2 onto-> 1")
              // {
              //   print("motif_name:");
              //   println(motif_name);
              //   print("input_mm_length:");
              //   println(input_mm_length);
              //   print("rowiter:");
              //   println(rowiter);
              //   print("section_offset:");
              //   println(section_offset);
              //   print("section_length:");
              //   println(section_length);
              //   print("section_ratio:");
              //   println(section_ratio);
              // }

              //need to 'normalize' input_mm length



              //2. for each note in input_mm
              for (int input_mmrowiter=0; input_mmrowiter<input_mm_numnotes; input_mmrowiter++) {
                //1. Scale postion (Add time shift (due to section)), and duration (to fit section)
                //debug: section offset seems correct; scaling of both duration and timepos relative to this is wrong; seems like section_ratio is wrong
                //
                float subdiv_pos_temp = input_mm.getFloat((input_mmrowiter),"time_pos")*section_ratio*motif_length/input_mm_length+section_offset;
                float subdiv_dur_temp = input_mm.getFloat((input_mmrowiter),"duration")*section_ratio*motif_length/input_mm_length;


                //3. Add Diatonic Degree
                float subdiv_dia_temp = input_mm.getFloat((input_mmrowiter),"pitch")+notearray_table.getFloat((rowiter),"pitch");
                //4. Add to buffertable
                float subdiv_vel_temp = input_mm.getFloat((input_mmrowiter),"velocity");
                float subdiv_timb1_temp = input_mm.getFloat((input_mmrowiter),"timbre1");
                float subdiv_timb2_temp = input_mm.getFloat((input_mmrowiter),"timbre2");

                buffertable.setFloat(rowiter*input_mm_numnotes+input_mmrowiter,"time_pos",subdiv_pos_temp);
                buffertable.setFloat(rowiter*input_mm_numnotes+input_mmrowiter,"duration",subdiv_dur_temp);
                buffertable.setFloat(rowiter*input_mm_numnotes+input_mmrowiter,"pitch",subdiv_dia_temp);
                buffertable.setFloat(rowiter*input_mm_numnotes+input_mmrowiter,"velocity",subdiv_vel_temp);
                buffertable.setFloat(rowiter*input_mm_numnotes+input_mmrowiter,"timbre1",subdiv_timb1_temp);
                buffertable.setFloat(rowiter*input_mm_numnotes+input_mmrowiter,"timbre2",subdiv_timb2_temp);
              }

        }

        notearray_table.clearRows();

        for (int rowiter=0; rowiter<(numnotes*input_mm_numnotes); rowiter++)
        {
            TableRow row = buffertable.getRow(rowiter);
            notearray_table.addRow(row);
        }
        //notearray_table = buffertable;

        //calcuate new mm properties
        numnotes=notearray_table.getRowCount();
        frag_numnotes=numnotes;
        motif_length=notearray_table.getFloat((frag_numnotes-1),"time_pos")+notearray_table.getFloat((frag_numnotes-1),"duration");
        // println(frag_numnotes);

        notearray_table.sort("time_pos");

        //add time shift (end concatenation) and add to existing table
        // for (int rowiter=0; rowiter<buffertablerowcount; rowiter++) {
        //
        //         TableRow row = buffertable.getRow(rowiter);
        //
        //         float time_pos_shifted=row.getFloat("time_pos")+motif_length;
        //         println(time_pos_shifted);
        //         println("time_pos_shifted");
        //         row.setFloat("time_pos",time_pos_shifted);
        //         notearray_table.addRow(row);
        // }
}

//destructively renders fragment
public void render_frag(){

        //buffer table for fragment
        Table des_frag_table = new Table();
        des_frag_table.addColumn("pitch");
        des_frag_table.addColumn("time_pos");
        des_frag_table.addColumn("duration");
        des_frag_table.addColumn("velocity");
        des_frag_table.addColumn("timbre1");
        des_frag_table.addColumn("timbre2");

        float frag_time_offset = notearray_table.getFloat(frag_index,"time_pos");

        //
        for(int notescan_abspos=frag_index; notescan_abspos<frag_numnotes; notescan_abspos++) {
                TableRow row = notearray_table.getRow(notescan_abspos);
                row.setFloat("time_pos",(row.getFloat("time_pos")-frag_time_offset));
                des_frag_table.addRow(row);

        }

        //clear old scan and scan in fragment
        notearray_table.clearRows();
        for (TableRow row : des_frag_table.rows()) {
                notearray_table.addRow(row);
        }

        //print_mm();

        numnotes=notearray_table.getRowCount();
        frag_index=0;
        frag_numnotes=numnotes;
        // println(frag_numnotes);

        notearray_table.sort("time_pos");
}

//prints fragment array
public void print_mm(){
        //1. scanrows of new tabledur_index
        //2. Add rows onto notearray_table
        //3. sort new table?? (shouldn't need to )

        for (int rowcounter=0; rowcounter<notearray_table.getRowCount(); rowcounter++) {
                TableRow row = notearray_table.getRow(rowcounter);

                println("in print_mm loop");
                print("row counter:", rowcounter, " ");
                print("pitch:",row.getString("pitch"), " ");
                print("time_pos:",row.getString("time_pos"), " ");
                print("duration:",row.getString("duration"), " ");
                print("velocity:",row.getString("velocity"), " ");
                print("timbre1:",row.getString("timbre1"), " ");
                print("timebre2:",row.getString("timbre2"), " ");
                println("");
        }
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

Table fugue_theme_mm_table;

public void loadMetamotifs(){
      simpleforms_1_table = loadTable("simpleforms_1.csv", "header");
      simpleforms_2_table = loadTable("simpleforms_2.csv", "header");
      arch1_mm_table = loadTable("arch1_mm.csv", "header");
      even_ascent_mm_table = loadTable("even_ascent_mm.csv", "header");
      fanfare_table = loadTable("fanfare.csv","header");

      fugue_theme_mm_table = loadTable("fugue_theme_mm.csv","header");
}
/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=24;
int zoom_notehigh=127;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=15;


// List of Instruments at http://explodingart.com/soundcipher/doc/arb/soundcipher/constants/ProgramChanges.html
float[] inst_cata = {score.PIANO,score.PIANO,score.PIANO,score.PIANO};

public void globalscoresetup(){
      setcurrentkey(0);
}

public void renderScore(){
    //*-------function examples--------*//
    // basics_examples();
    // renderproperties_example();
    // retrograde_examples();
    // fragmentation_examples();
    // concat_examples();
    // fugue_examples();
    mapping_examples();

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
        freemotif_table1obj.scale_diatonic=freemotif_table1obj.scale_diatonic* -1;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> chrom +24 / Inv";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic-12;
        freemotif_table1obj.motif_name="-> chrom -12";
        freemotif_table1obj.renderfreemotif();
}

public void fugue_examples(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Basics Examples");

        score.addCallback(96, 1);

        //harmonic minor
        setcurrentkey(2);

        freemotif_table1obj = new freeMotif_table(fugue_theme_mm_table);
        freemotif_table2obj = new freeMotif_table(fugue_theme_mm_table);
        freemotif_table3obj = new freeMotif_table(fugue_theme_mm_table);
        freemotif_table4obj = new freeMotif_table(fugue_theme_mm_table);

        /****round 1****/
        freemotif_table1obj.motif_name="original";
        freemotif_table1obj.frag_numnotes=33;
        freemotif_table1obj.renderfreemotif();

        /****round 2****/
        freemotif_table1obj.motif_name="default";
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(1);
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="-1 Oct round";
        freemotif_table2obj.inst_index=1;
        freemotif_table2obj.frag_numnotes=33;
        freemotif_table2obj.diatonic_offset=-7;
        freemotif_table2obj.pos_time=16;
        freemotif_table2obj.renderfreemotif();

        /****round 3****/
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(0);
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="default";
        freemotif_table2obj.pos_time=32;
        freemotif_table2obj.renderfreemotif();

        freemotif_table3obj.motif_name="-2 Oct; Scale time 4";
        freemotif_table3obj.inst_index=2;
        freemotif_table3obj.scale_time=4;
        freemotif_table3obj.scale_dur=4;
        freemotif_table3obj.frag_numnotes=4;
        freemotif_table3obj.diatonic_offset=-14;
        freemotif_table3obj.pos_time=32;
        freemotif_table3obj.renderfreemotif();

        /****round 4****/
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(1);
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(0);
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(1);
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.pos_time=48;
        freemotif_table2obj.frag_numnotes=freemotif_table2obj.numnotes;
        freemotif_table2obj.renderfreemotif();
        freemotif_table2obj.pos_time=freemotif_table2obj.pos_time+freemotif_table2obj.frag_length;
        freemotif_table2obj.auto_retrograde(1);
        freemotif_table2obj.renderfreemotif();
        freemotif_table2obj.pos_time=freemotif_table2obj.pos_time+freemotif_table2obj.frag_length;
        freemotif_table2obj.auto_retrograde(0);
        freemotif_table2obj.renderfreemotif();

        freemotif_table3obj.motif_name="default";
        freemotif_table3obj.inst_index=2;
        freemotif_table3obj.scale_time=4;
        freemotif_table3obj.scale_dur=4;
        freemotif_table3obj.frag_numnotes=freemotif_table3obj.numnotes;
        freemotif_table3obj.diatonic_offset=-14;
        freemotif_table3obj.pos_time=48;
        freemotif_table3obj.renderfreemotif();

        freemotif_table4obj.motif_name="Retro; oct +1; Reveal frag Section";
        freemotif_table4obj.pos_time=48;
        freemotif_table4obj.inst_index=3;
        freemotif_table4obj.auto_retrograde(1);
        freemotif_table4obj.diatonic_offset=4;
        freemotif_table4obj.renderfreemotif();

        freemotif_table4obj.pos_time=freemotif_table4obj.pos_time+freemotif_table4obj.frag_length;
        freemotif_table4obj.auto_retrograde(0);
        freemotif_table4obj.renderfreemotif();

        freemotif_table4obj.pos_time=freemotif_table4obj.pos_time+freemotif_table4obj.frag_length;
        freemotif_table4obj.auto_retrograde(1);
        freemotif_table4obj.renderfreemotif();
}

public void renderproperties_example(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Property Reset and Apply Transformations Examples");

        score.addCallback(32, 1);

        freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
        freemotif_table1obj = new freeMotif_table(arch1_mm_table);
        freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table1obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.duration_retrograde=0;
        freemotif_table1obj.position_retrograde=0;
        freemotif_table1obj.motif_name="original";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.diatonic_offset=-1;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> diatonic -1";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.scale_time=freemotif_table1obj.scale_time*.5f;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> timescale *.5";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.reset_properties();
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="reset properties";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.scale_diatonic=-1;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> diatonic scale -1";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.diatonic_offset=7;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> diatonic 12";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.scale_time=freemotif_table1obj.scale_time*.5f;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> timescale *.5";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="render & reset";
        freemotif_table1obj.render_properties();
        freemotif_table1obj.renderfreemotif();
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
        // // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        freemotif_table1obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.duration_retrograde=0;
        freemotif_table1obj.position_retrograde=0;
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
        freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        freemotif_table1obj = new freeMotif_table(fanfare_table);

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
        freemotif_table1obj.frag_numnotes=freemotif_table1obj.frag_numnotes-1;
        freemotif_table1obj.motif_name="ignore last note";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.frag_index=0;
        freemotif_table1obj.frag_numnotes=freemotif_table1obj.frag_numnotes-1;
        freemotif_table1obj.motif_name="ignore last 2 notes";
        freemotif_table1obj.renderfreemotif();
}

public void concat_examples(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Fragmentation Examples");

        score.addCallback(64, 1);

        // freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table1obj = new freeMotif_table(arch1_mm_table);
        // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table1obj = new freeMotif_table(fanfare_table);

        // freemotif_table2obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table2obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table2obj = new freeMotif_table(arch1_mm_table);
        // freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table2obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.motif_name="motif 1";
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="motif 2";
        freemotif_table2obj.pos_time=4;
        freemotif_table2obj.inst_index=1;
        freemotif_table2obj.renderfreemotif();

        freemotif_table1obj.motif_name="concat m1+m2";
        freemotif_table1obj.inst_index=2;
        freemotif_table1obj.concat_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.pos_time=8;
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=12;
        freemotif_table1obj.motif_name="remove notes 1,2";
        freemotif_table1obj.frag_index=2;
        freemotif_table1obj.inst_index=2;
        freemotif_table1obj.render_frag();
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=16;
        freemotif_table1obj.motif_name="remove last note";
        freemotif_table1obj.frag_numnotes=freemotif_table1obj.frag_numnotes-1;
        freemotif_table1obj.render_frag();
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=20;
        freemotif_table1obj.motif_name="prepend motif 2";
        freemotif_table1obj.prepend_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.renderfreemotif();
}


public void mapping_examples(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Mapping Examples");

        score.addCallback(zoom_stoptime, 1);

        // freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
        // freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table1obj = new freeMotif_table(arch1_mm_table);
        freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table1obj = new freeMotif_table(fanfare_table);

        freemotif_table2obj = new freeMotif_table(simpleforms_1_table);
        // freemotif_table2obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table2obj = new freeMotif_table(arch1_mm_table);
        // freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table2obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.motif_name="motif 1";
        freemotif_table1obj.tonal_retrograde=1;
        freemotif_table1obj.scale_time=.5f;
        freemotif_table1obj.scale_dur=.5f;
        freemotif_table1obj.render_properties();
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="motif 2";
        freemotif_table2obj.inst_index=1;
        freemotif_table2obj.scale_time=1;
        freemotif_table2obj.scale_dur=1;
        freemotif_table2obj.render_properties();
        freemotif_table2obj.pos_time=2;
        freemotif_table2obj.renderfreemotif();

        freemotif_table1obj.motif_name="motif 2 onto-> 1";
        freemotif_table1obj.scale_time=2;
        freemotif_table1obj.scale_dur=2;
        freemotif_table1obj.inst_index=2;
        freemotif_table1obj.pos_time=4;
        freemotif_table1obj.render_properties();
        freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.renderfreemotif();


        freemotif_table1obj.motif_name="iter again";
        freemotif_table1obj.scale_time=4;
        freemotif_table1obj.scale_dur=4;
        freemotif_table1obj.inst_index=3;
        freemotif_table1obj.pos_time=8;
        freemotif_table1obj.render_properties();
        freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        //freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=40;
        freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.renderfreemotif();

        // freemotif_table1obj.motif_name="motif 2 onto-> 1";
        // freemotif_table1obj.scale_time=8;
        // freemotif_table1obj.scale_dur=8;
        // freemotif_table1obj.inst_index=3;
        // freemotif_table1obj.pos_time=8;
        // freemotif_table1obj.render_properties();
        // freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        // freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        // freemotif_table1obj.renderfreemotif();


        // freemotif_table1obj.motif_name="again (with scale)";
        // freemotif_table1obj.inst_index=3;
        // freemotif_table1obj.pos_time=48;
        // freemotif_table1obj.scale_dur=1;
        // freemotif_table1obj.render_properties();
        // // freemotif_table1obj.renderfreemotif();
        // freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        // freemotif_table1obj.renderfreemotif();

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
