int gwidth = 640;
int gheight = 640;

float stretchpixwidth;
float ulcornx;
float brcornx;

float stretchpixheight;
float ulcorny; 
float brcorny;

//int zoom_starttime=1;
//int zoom_stoptime=8;




/// Use traditional note legnth "quarter note, 1/2, etc" to define 'length' decimals
/*------MOTIF / CLASS VARIABLES---*/
// { {pitch <diatonic reference>, start position, duration, velocity, timbre A, timbre B} }
float[][] simpleforms_1 = {{ 0, 0.00, .375, 127, 0, 0}, 
  { 2, .375, .375, 127, 0, 0}, 
  {-2, .750, .250, 127, 0, 0}};

/*-----"FREE MOTIF" OBJECTS--------*/
freeMotif freemotif_1;

/*-------------SCORE VARIABLES------------*/
// score length in measures
int score_length=16;
// score bpm
int score_bpm=120;

/*---------GUI VARIABLES------------*/
color gridline_y_minor=color(82, 82, 82);
color gridline_y_major=color(126, 126, 126);
color gridline_middleC=color(128, 0, 0);
color gridline_tonic=color(82, 0, 0);

color gridline_x_minor=color(12, 12, 12);
color gridline_x_major=color(82, 82, 82);
color gridline_4bars=color(0, 82, 0);

color note_color_a=color(120, 0, 0);


float noteyheight = gheight/128;
float quarternotexwidth = gwidth/128;

void setup() {
  size(640, 640, P2D);
  background(0, 0, 0);
  colorMode(RGB, 256);
  renderScore();
}

void draw() {
}
