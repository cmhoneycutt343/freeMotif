void Sample_Score1(){
     freemotif_1 = new freeMotif(simpleforms_2);

     setcurrentkey(0);

     freemotif_1.pos_time=0.0;
     freemotif_1.diatonic_offset=0;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=1.0;
     freemotif_1.diatonic_offset=3;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=2.0;
     freemotif_1.diatonic_offset=-1;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=3.0;
     freemotif_1.diatonic_offset=2;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=4.0;
     freemotif_1.diatonic_offset=-2;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=5.0;
     freemotif_1.diatonic_offset=1;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=6.0;
     freemotif_1.diatonic_offset=-3;
     freemotif_1.renderfreemotif();

     freemotif_1.pos_time=7.0;
     freemotif_1.diatonic_offset=0;
     freemotif_1.renderfreemotif();
}

void ss_descending_inv_scales(){
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
