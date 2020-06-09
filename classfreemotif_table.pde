/*---------'freeMotif Class'--------*/
class freeMotif_table{

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
  }

  void retro(){
      println("test print in testmethod");
  }

  //Method is called to write motif to score / GUI
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
      //    start position,
      //    duration,
      //    velocity,
      //    timbre A,
      //    timbre B} }
      for(int notescan_abspos=frag_index; notescan_abspos<frag_length ; notescan_abspos++){
        //experiment --- will redefining 'noterenderindex'

        // motif_retrograde
        //noterenderindex=abs(int(((frag_length-1)*motif_retrograde)-noterenderindex));
        // if(motif_retrograde==0)
        // {
        //   noterenderindex = notescan_abspos;
        // } else if(motif_retrograde==1)  {
        //   noterenderindex = notescan_abspos;
        // }

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

        //chromatic pitch is returned
        float output_pitch = return_diaton(notearray_table.getFloat(tablepitch_index, "pitch")*(scale_diatonic)+diatonic_offset,pos_tonic);

        //absolute time (relative to score)
        float output_pos = global_time_render_offset+pos_time+tablepos_fromindex*scale_time;

        float output_dur = notearray_table.getFloat(tabledur_index, "duration")*scale_dur;
        float output_vel = notearray_table.getFloat(notescan_abspos, "velocity");
        float output_timb1 = notearray_table.getFloat(notescan_abspos, "timbre1");
        float output_timb2 = notearray_table.getFloat(notescan_abspos, "timbre2");

        drawgrid();
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