

class freeMotif{
  int noterenderindex;
  int numnotes;
  float[][] notearray;
  float pos_time=0;
  int pos_tonic=60;
  float motif_length=1;
  float scale_time=1;
  float scale_diatonic=1;
  float motif_inversion=0;
  float motif_retrograde=0;
  int frag_index=0;
  int frag_length;
  float time_offset=0;
  int inst_index=0;
  
  freeMotif(float[][] classinputmotif){
      notearray=classinputmotif;
      numnotes=notearray.length;
      frag_length=numnotes;
  }
  
  void renderfreemotif(){
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
          drawnote(pos_tonic+notearray[noterenderindex][0], 
                    pos_time+notearray[noterenderindex][1]+time_offset,
                    notearray[noterenderindex][2],
                    notearray[noterenderindex][3],
                    notearray[noterenderindex][4],
                    notearray[noterenderindex][5],
                    inst_index);
      }
  }
  
  void updatetimeoffset(float newstartpos){
      time_offset = newstartpos;
      //for(noterenderindex=0; noterenderindex<frag_length ; noterenderindex++){
      //       notearray[noterenderindex][1]=notearray[noterenderindex][1]+time_offset;
      //  }
  }
  
}
