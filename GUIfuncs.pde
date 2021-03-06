/*---------GUI VARIABLES------------*/
color gridline_y_minor=color(16, 16, 16);
color gridline_y_major=color(32, 32, 32);
color gridline_middleC=color(128, 0, 0);
color gridline_tonic=color(82, 0, 0);

color gridline_x_minor=color(12, 12, 12);
color gridline_x_major=color(82, 82, 82);
color gridline_4bars=color(0, 82, 0);

color note_color_a=color(120, 0, 0);

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

void drawBackground(){
  background(0,0,0);
  drawgrid();

  //DEBUG
  //drawnote(0,0.0,0.0,0,0,0,0);
}

void drawgrid(){
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
  for(int drawgrid_timeindex=0; drawgrid_timeindex<int((zoom_stoptime+1)*4); drawgrid_timeindex++){

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

void drawnote(float pitch, float start_pos, float dur, float velo, float timbA, float timbB, int inst) {

    /***DEBUG; 'i' will be subbed in for MIDI#number*/

    colorMode(HSB);
    color note_color_byinst=color((inst*100)%255,128,velo);
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

void addGUIcom(float pitch, float start_pos, int inst, String inputcomment ) {

    /***DEBUG; 'i' will be subbed in for MIDI#number*/

    colorMode(HSB);
    color note_color_byinst=color((inst*100)%255,128,128);
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
    text(inputcomment, ulcornx+.5*stretchpixheight, ulcorny+4.5*stretchpixwidth);

    //*/



  colorMode(RGB);
}

void scoreTitle(String titletext){

  textSize(28);
  text(titletext, 20, 20);

  //*/

  colorMode(RGB);
}
