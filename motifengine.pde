import arb.soundcipher.*;

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
int score_bpm=60;

int global_time_render_offset=0;

/*-----------KEY SIG Variables--------------*/
float[] scalebuffer = {0, 0, 0, 0, 0, 0, 0};

void setup() {
  size(1280, 640, P2D);
  background(0, 0, 0);
  colorMode(RGB, 256);

  loadMetamotifs();
  globalscoresetup();
  drawBackground();
  //
  sc.getMidiDeviceInfo();

  score.tempo(score_bpm);


  renderScore();

  score.addCallbackListener(this);

  score.play();

  println(score.sequencer().getLoopStartPoint());
  println(score.sequencer().getLoopEndPoint());

  score.writeMidiFile("/Users/charleshoneycutt/Desktop/freemotiftemp.mid");

}

void draw() {
  //println(score.sequencer().getTickPosition());

  //println(score.sequencer().getTickPosition());

}

public void handleCallbacks(int callbackID) {
    if(callbackID==1){
      score.stop();
      score.play();

      // score.sequencer().setLoopCount(100);
      // score.sequencer().setTickPosition(4*960);
      // score.sequencer().setLoopStartPoint(4*960);
      // score.sequencer().setLoopEndPoint(5*960);

      score.tempo(score_bpm);
    }
}
