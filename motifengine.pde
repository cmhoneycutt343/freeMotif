import arb.soundcipher.*;

SoundCipher sc = new SoundCipher(this);
SCScore score = new SCScore();


/// Use traditional note legnth "quarter note, 1/2, etc" to define 'length' decimals

/*-----"FREE MOTIF" OBJECTS--------*/
freeMotif freemotif_1;

/*-------------SCORE VARIABLES------------*/
// score length in measures
int score_length=16;
// score bpm
int score_bpm=120;

/*-----------KEY SIG Variables--------------*/
float[] scalebuffer = {0, 0, 0, 0, 0, 0, 0};

/*---------GUI VARIABLES------------*/
color gridline_y_minor=color(82, 82, 82);
color gridline_y_major=color(126, 126, 126);
color gridline_middleC=color(128, 0, 0);
color gridline_tonic=color(82, 0, 0);

color gridline_x_minor=color(12, 12, 12);
color gridline_x_major=color(82, 82, 82);
color gridline_4bars=color(0, 82, 0);

color note_color_a=color(120, 0, 0);

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



void setup() {
  size(640, 640, P2D);
  background(0, 0, 0);
  colorMode(RGB, 256);

  globalscoresetup();
  renderScore();

  score.tempo(score_bpm);
  score.play();
}

void draw() {
}
