
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

  void renderfreemotif(){
      //drawgrid();

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
        float output_pos = global_time_render_offset+pos_time+notearray[noterenderindex][1]*scale_time;
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

        // end 'renderfreemotif' method
      }
  }

}
