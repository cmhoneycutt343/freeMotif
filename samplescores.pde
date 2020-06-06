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
    //repeat at measure 32
    // score.addCallback(32, 1);

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
    freemotif_4.scale_diatonic=-1;

    for(int iter=0; iter<8; iter++)
    {

      if(iter==6){
        setcurrentkey(2);
      } else {
        setcurrentkey(1);
      }

      freemotif_1.pos_time=0+iter*4;
      freemotif_1.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_1.diatonic_offset=7;
          freemotif_1.scale_diatonic=-1;
      } else {
          freemotif_1.diatonic_offset=0;
          freemotif_1.scale_diatonic=1;
      }
      freemotif_1.diatonic_offset=freemotif_1.diatonic_offset-(iter/2);
      freemotif_1.renderfreemotif();

      freemotif_2.pos_time=2+iter*4;
      if(iter==(2)||iter==(4)||iter==(6)) {
        freemotif_2.scale_diatonic=1;
      } else {
        freemotif_2.scale_diatonic=-1;
      }
      freemotif_2.diatonic_offset=-iter;
      freemotif_2.renderfreemotif();

      freemotif_3.pos_time=0+iter*4;
      freemotif_3.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_3.diatonic_offset=7;
          freemotif_3.scale_diatonic=-1;
      } else {
          freemotif_3.diatonic_offset=0;
          freemotif_3.scale_diatonic=1;
      }
      freemotif_3.diatonic_offset=freemotif_3.diatonic_offset-(iter/2);
      freemotif_3.renderfreemotif();

      freemotif_4.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_4.diatonic_offset=7;
          freemotif_4.scale_diatonic=-1;
      } else {
          freemotif_4.diatonic_offset=0;
          freemotif_4.scale_diatonic=1;
      }
      freemotif_4.diatonic_offset=freemotif_4.diatonic_offset-(iter/2);
      freemotif_4.renderfreemotif();

    }
}

void ss_descending_inv_scales_b(){
    //Sample_Score1();
    //repeat at measure 32
    // score.addCallback(32, 1);

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
    freemotif_4.scale_diatonic=-1;

    for(int iter=0; iter<8; iter++)
    {

      if(iter==6){
        setcurrentkey(2);
      } else {
        setcurrentkey(1);
      }

      freemotif_1.pos_time=0+iter*4;
      freemotif_1.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_1.diatonic_offset=7;
          freemotif_1.scale_diatonic=-1;
      } else {
          freemotif_1.diatonic_offset=0;
          freemotif_1.scale_diatonic=1;
      }
      freemotif_1.diatonic_offset=freemotif_1.diatonic_offset-(iter/2);
      freemotif_1.renderfreemotif();
      freemotif_1.pos_time=2+iter*4;
      freemotif_1.diatonic_offset=freemotif_1.diatonic_offset+7;
      freemotif_1.renderfreemotif();
      freemotif_1.diatonic_offset=freemotif_1.diatonic_offset-7;









      freemotif_2.pos_time=2+iter*4;
      if(iter==(2)||iter==(4)||iter==(6)) {
        freemotif_2.scale_diatonic=1;
      } else {
        freemotif_2.scale_diatonic=-1;
      }
      freemotif_2.diatonic_offset=-iter;
      freemotif_2.renderfreemotif();

      freemotif_3.pos_time=0+iter*4;
      freemotif_3.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_3.diatonic_offset=7;
          freemotif_3.scale_diatonic=-1;
      } else {
          freemotif_3.diatonic_offset=0;
          freemotif_3.scale_diatonic=1;
      }
      freemotif_3.diatonic_offset=freemotif_3.diatonic_offset-(iter/2);
      freemotif_3.renderfreemotif();

      freemotif_4.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_4.diatonic_offset=7;
          freemotif_4.scale_diatonic=-1;
      } else {
          freemotif_4.diatonic_offset=0;
          freemotif_4.scale_diatonic=1;
      }
      freemotif_4.diatonic_offset=freemotif_4.diatonic_offset-(iter/2);
      freemotif_4.renderfreemotif();

    }
}

void ss_descending_inv_scales2(){
    //Sample_Score1();

    freemotif_1 = new freeMotif(arch1_mm);
    freemotif_2 = new freeMotif(arch1_mm);
    freemotif_3 = new freeMotif(arch1_mm);
    freemotif_4 = new freeMotif(arch1_mm);

    freemotif_2.inst_index=1;
    freemotif_3.inst_index=2;
    freemotif_4.inst_index=3;

    freemotif_3.pos_tonic=12+freemotif_3.pos_tonic;
    freemotif_4.pos_tonic=12+freemotif_3.pos_tonic;
    freemotif_4.scale_time=8;
    freemotif_4.scale_dur=16;
    freemotif_4.scale_diatonic=-1;

    for(int iter=0; iter<8; iter++)
    {
      freemotif_1.pos_time=0+iter*4;
      freemotif_1.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_1.diatonic_offset=7;
          freemotif_1.scale_diatonic=-1;
      } else {
          freemotif_1.diatonic_offset=0;
          freemotif_1.scale_diatonic=1;
      }
      freemotif_1.diatonic_offset=freemotif_1.diatonic_offset-(iter/2);
      freemotif_1.renderfreemotif();

      setcurrentkey(2);
      freemotif_2.pos_time=3+iter*4;
      freemotif_2.scale_diatonic=-1;
      freemotif_2.diatonic_offset=-iter;
      freemotif_2.renderfreemotif();
      setcurrentkey(1);

      freemotif_3.pos_time=0+iter*4;
      freemotif_3.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_3.diatonic_offset=7;
          freemotif_3.scale_diatonic=-1;
      } else {
          freemotif_3.diatonic_offset=0;
          freemotif_3.scale_diatonic=1;
      }
      freemotif_3.diatonic_offset=freemotif_3.diatonic_offset-(iter/2);
      freemotif_3.renderfreemotif();

      freemotif_4.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_4.diatonic_offset=7;
          freemotif_4.scale_diatonic=-1;
      } else {
          freemotif_4.diatonic_offset=0;
          freemotif_4.scale_diatonic=1;
      }
      freemotif_4.diatonic_offset=freemotif_4.diatonic_offset-(iter/2);
      freemotif_4.renderfreemotif();

    }
}

void ss_non_even_scaling(){
    //Sample_Score1();

    freemotif_1 = new freeMotif(arch1_mm);
    freemotif_2 = new freeMotif(arch1_mm);
    freemotif_3 = new freeMotif(arch1_mm);
    freemotif_4 = new freeMotif(arch1_mm);

    freemotif_2.inst_index=1;
    freemotif_3.inst_index=2;
    freemotif_4.inst_index=3;

    freemotif_1.diatonic_offset = 0;
    freemotif_2.diatonic_offset = -2;
    freemotif_3.diatonic_offset = 0;
    freemotif_4.diatonic_offset = 0;

    freemotif_1.pos_time = 0;
    freemotif_2.pos_time = 1;
    freemotif_3.pos_time = 2;
    freemotif_4.pos_time = 3;

    freemotif_1.scale_time = 1;
    freemotif_2.scale_time = 2;
    freemotif_3.scale_time = 4;
    freemotif_4.scale_time = 8;

    freemotif_1.scale_dur = 1;
    freemotif_2.scale_dur = 2;
    freemotif_3.scale_dur = 4;
    freemotif_4.scale_dur = 8;

    freemotif_1.renderfreemotif();
    freemotif_2.renderfreemotif();
    freemotif_3.renderfreemotif();
    freemotif_4.renderfreemotif();

    freemotif_3.pos_time = 10;
    freemotif_3.renderfreemotif();

    freemotif_2.pos_time = 13;
    freemotif_2.renderfreemotif();

    freemotif_1.pos_time = 14;
    freemotif_1.renderfreemotif();

    freemotif_1.pos_time = 15;
    freemotif_1.scale_time =0;
    freemotif_1.renderfreemotif();
}

void ss_non_even_scaling_asc(){
    //Sample_Score1();

    freemotif_1 = new freeMotif(arch1_mm);
    freemotif_2 = new freeMotif(arch1_mm);
    freemotif_3 = new freeMotif(arch1_mm);
    freemotif_4 = new freeMotif(arch1_mm);

    freemotif_2.inst_index=1;
    freemotif_3.inst_index=2;
    freemotif_4.inst_index=3;

    freemotif_1.diatonic_offset = 0;
    freemotif_2.diatonic_offset = 1;
    freemotif_3.diatonic_offset = 2;
    freemotif_4.diatonic_offset = 3;

    freemotif_1.pos_time = 0;
    freemotif_2.pos_time = 1;
    freemotif_3.pos_time = 2;
    freemotif_4.pos_time = 3;

    freemotif_1.scale_time = 1;
    freemotif_2.scale_time = 2/3;
    freemotif_3.scale_time = 1/3;
    freemotif_4.scale_time = 4/3;

    freemotif_1.renderfreemotif();
    freemotif_2.renderfreemotif();
    freemotif_3.renderfreemotif();
    freemotif_4.renderfreemotif();

    freemotif_1.pos_time = 4;
    freemotif_1.scale_time = 0;
    freemotif_1.diatonic_offset = 4;
    freemotif_1.renderfreemotif();

    freemotif_2.pos_time = 5;
    freemotif_2.diatonic_offset = 6;
    freemotif_2.renderfreemotif();

    freemotif_1.pos_time = 6;
    freemotif_1.diatonic_offset = 4;
    freemotif_1.renderfreemotif();

    freemotif_1.pos_time = 7;
    freemotif_1.diatonic_offset = 4;
    freemotif_1.renderfreemotif();

    freemotif_1.pos_time = 7.5;
    freemotif_1.diatonic_offset = 2;
    freemotif_1.renderfreemotif();

    freemotif_1.pos_time = 8;
    freemotif_1.diatonic_offset = 4;
    freemotif_1.renderfreemotif();
}
