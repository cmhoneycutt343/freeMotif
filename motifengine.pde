import arb.soundcipher.*;

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
int score_bpm=200;

int global_time_render_offset=0;

/*-----------KEY SIG Variables--------------*/
float[] scalebuffer = {0, 0, 0, 0, 0, 0, 0};

void setup() {
  size(1280, 640, P2D);
  background(0, 0, 0);
  colorMode(RGB, 256);

  globalscoresetup();
  renderScore();

  score.addCallbackListener(this);

  score.tempo(score_bpm);
  score.repeat(5);
  score.play();

}

void draw() {
}

public void handleCallbacks(int callbackID) {
    if(callbackID==1){
      score.stop();
      score.play();
    }
}
