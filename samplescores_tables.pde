void retrograde_scoretest(){
      //retrograde function tester
      //0. Dry
      //1. Tonal retrograde
      //2. Rhythmic retrograde
      //3. Full retrograde


      freemotif_table1obj = new freeMotif_table(simpleforms_table2);

      freemotif_table1obj.diatonic_offset=0;
      freemotif_table1obj.scale_time=4;
      freemotif_table1obj.scale_dur=4;
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.tonal_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.tonal_retrograde=0;
      freemotif_table1obj.duration_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.duration_retrograde=1;
      freemotif_table1obj.position_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.renderfreemotif();

      // freemotif_table1obj = new freeMotif_table(arch1_mm_table);
      //
      // freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset=0;
      // freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+16;
      // freemotif_table1obj.scale_time=2;
      // freemotif_table1obj.renderfreemotif();
      //
      // freemotif_table1obj.tonal_retrograde=1;
      // freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      // freemotif_table1obj.renderfreemotif();
      //
      // // freemotif_table1obj.tonal_retrograde=0;
      // // freemotif_table1obj.duration_retrograde=1;
      // // freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      // // freemotif_table1obj.renderfreemotif();
      // //
      // // freemotif_table1obj.tonal_retrograde=1;
      // // freemotif_table1obj.position_retrograde=1;
      // // freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      // // freemotif_table1obj.renderfreemotif();
}








void debug_score_tables_beta()
{

  int starting_time_offset=global_time_render_offset;


    freemotif_table1obj = new freeMotif_table(simpleforms_table1);
    freemotif_table1obj.scale_time=8;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=1;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=1;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
    freemotif_table1obj.diatonic_offset=2;
    freemotif_table1obj.renderfreemotif();

  setcurrentkey(0);}

void debug_score_tables()
{

  int starting_time_offset=global_time_render_offset;

  for(int iter=0; iter<17; iter++)
  {
    freemotif_table1obj = new freeMotif_table(simpleforms_table1);
    freemotif_table1obj.scale_time=2;
    //freemotif_table1obj.pos_time=2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table2obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj.inst_index=1;
    freemotif_table2obj.scale_time=1/3;
    if(iter<13){
      freemotif_table2obj.diatonic_offset=freemotif_table1obj.diatonic_offset+12+((iter-1)/4);
    } else {
      freemotif_table2obj.diatonic_offset=freemotif_table1obj.diatonic_offset+12+2;
    }
    freemotif_table2obj.renderfreemotif();

    global_time_render_offset=starting_time_offset+iter*4;
  }

  setcurrentkey(0);}

void Sample_Score1_tables(){
     //freemotif_table1obj = new freeMotif(simpleforms_2);
     freemotif_table1obj = new freeMotif_table(simpleforms_table2);

     setcurrentkey(0);

     freemotif_table1obj.pos_time=0.0;
     freemotif_table1obj.diatonic_offset=0;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=1.0;
     freemotif_table1obj.diatonic_offset=3;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=2.0;
     freemotif_table1obj.diatonic_offset=-1;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=3.0;
     freemotif_table1obj.diatonic_offset=2;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=4.0;
     freemotif_table1obj.diatonic_offset=-2;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=5.0;
     freemotif_table1obj.diatonic_offset=1;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=6.0;
     freemotif_table1obj.diatonic_offset=-3;
     freemotif_table1obj.renderfreemotif();

     freemotif_table1obj.pos_time=7.0;
     freemotif_table1obj.diatonic_offset=0;
     freemotif_table1obj.renderfreemotif();
}

void ss_descending_inv_scales_tables(){
    //Sample_Score1();
    //repeat at measure 32
    // score.addCallback(32, 1);

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table3obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.scale_time=8;
    freemotif_table4obj.scale_dur=16;
    freemotif_table4obj.scale_diatonic=-1;

    for(int iter=0; iter<8; iter++)
    {

      if(iter==6){
        setcurrentkey(2);
      } else {
        setcurrentkey(1);
      }

      freemotif_table1obj.pos_time=0+iter*4;
      freemotif_table1obj.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_table1obj.diatonic_offset=7;
          freemotif_table1obj.scale_diatonic=-1;
      } else {
          freemotif_table1obj.diatonic_offset=0;
          freemotif_table1obj.scale_diatonic=1;
      }
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset-(iter/2);
      freemotif_table1obj.renderfreemotif();

      freemotif_table2obj.pos_time=2+iter*4;
      if(iter==(2)||iter==(4)||iter==(6)) {
        freemotif_table2obj.scale_diatonic=1;
      } else {
        freemotif_table2obj.scale_diatonic=-1;
      }
      freemotif_table2obj.diatonic_offset=-iter;
      freemotif_table2obj.renderfreemotif();

      freemotif_table3obj.pos_time=0+iter*4;
      freemotif_table3obj.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_table3obj.diatonic_offset=7;
          freemotif_table3obj.scale_diatonic=-1;
      } else {
          freemotif_table3obj.diatonic_offset=0;
          freemotif_table3obj.scale_diatonic=1;
      }
      freemotif_table3obj.diatonic_offset=freemotif_table3obj.diatonic_offset-(iter/2);
      freemotif_table3obj.renderfreemotif();

      freemotif_table4obj.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_table4obj.diatonic_offset=7;
          freemotif_table4obj.scale_diatonic=-1;
      } else {
          freemotif_table4obj.diatonic_offset=0;
          freemotif_table4obj.scale_diatonic=1;
      }
      freemotif_table4obj.diatonic_offset=freemotif_table4obj.diatonic_offset-(iter/2);
      freemotif_table4obj.renderfreemotif();

    }
}

void ss_descending_inv_scales_b_tables(){
    //Sample_Score1();
    //repeat at measure 32
    // score.addCallback(32, 1);

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table3obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.pos_tonic=12+freemotif_table3obj.pos_tonic;
    freemotif_table4obj.scale_time=8;
    freemotif_table4obj.scale_dur=16;
    freemotif_table4obj.scale_diatonic=-1;

    for(int iter=0; iter<8; iter++)
    {

      if(iter==6){
        setcurrentkey(2);
      } else {
        setcurrentkey(1);
      }

      freemotif_table1obj.pos_time=0+iter*4;
      freemotif_table1obj.scale_time=2;
      if((iter%2)==1)
      {
          freemotif_table1obj.diatonic_offset=7;
          freemotif_table1obj.scale_diatonic=-1;
      } else {
          freemotif_table1obj.diatonic_offset=0;
          freemotif_table1obj.scale_diatonic=1;
      }
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset-(iter/2);
      freemotif_table1obj.renderfreemotif();
      freemotif_table1obj.pos_time=2+iter*4;
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset+7;
      freemotif_table1obj.renderfreemotif();
      freemotif_table1obj.diatonic_offset=freemotif_table1obj.diatonic_offset-7;









      freemotif_table2obj.pos_time=2+iter*4;
      if(iter==(2)||iter==(4)||iter==(6)) {
        freemotif_table2obj.scale_diatonic=1;
      } else {
        freemotif_table2obj.scale_diatonic=-1;
      }
      freemotif_table2obj.diatonic_offset=-iter;
      freemotif_table2obj.renderfreemotif();

      freemotif_table3obj.pos_time=0+iter*4;
      freemotif_table3obj.scale_time=4;
      if((iter%2)==1)
      {
          freemotif_table3obj.diatonic_offset=7;
          freemotif_table3obj.scale_diatonic=-1;
      } else {
          freemotif_table3obj.diatonic_offset=0;
          freemotif_table3obj.scale_diatonic=1;
      }
      freemotif_table3obj.diatonic_offset=freemotif_table3obj.diatonic_offset-(iter/2);
      freemotif_table3obj.renderfreemotif();

      freemotif_table4obj.pos_time=0+iter*4;
      if((iter%2)==1)
      {
          freemotif_table4obj.diatonic_offset=7;
          freemotif_table4obj.scale_diatonic=-1;
      } else {
          freemotif_table4obj.diatonic_offset=0;
          freemotif_table4obj.scale_diatonic=1;
      }
      freemotif_table4obj.diatonic_offset=freemotif_table4obj.diatonic_offset-(iter/2);
      freemotif_table4obj.renderfreemotif();

    }
}

void ss_non_even_scaling_tables(){
  //Sample_Score1();

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(arch1_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table1obj.diatonic_offset = 0;
    freemotif_table2obj.diatonic_offset = -2;
    freemotif_table3obj.diatonic_offset = 0;
    freemotif_table4obj.diatonic_offset = 0;

    freemotif_table1obj.pos_time = 0;
    freemotif_table2obj.pos_time = 1;
    freemotif_table3obj.pos_time = 2;
    freemotif_table4obj.pos_time = 3;

    freemotif_table1obj.scale_time = 1;
    freemotif_table2obj.scale_time = 2;
    freemotif_table3obj.scale_time = 4;
    freemotif_table4obj.scale_time = 8;

    freemotif_table1obj.scale_dur = 1;
    freemotif_table2obj.scale_dur = 2;
    freemotif_table3obj.scale_dur = 4;
    freemotif_table4obj.scale_dur = 8;

    freemotif_table1obj.renderfreemotif();
    freemotif_table2obj.renderfreemotif();
    freemotif_table3obj.renderfreemotif();
    freemotif_table4obj.renderfreemotif();

    freemotif_table3obj.pos_time = 10;
    freemotif_table3obj.renderfreemotif();

    freemotif_table2obj.pos_time = 13;
    freemotif_table2obj.renderfreemotif();

    freemotif_table1obj.pos_time = 14;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 15;
    freemotif_table1obj.scale_time =0;
    freemotif_table1obj.renderfreemotif();
}

void ss_non_even_scaling_asc_tables(){
    //Sample_Score1();

    freemotif_table1obj = new freeMotif_table(arch1_mm_table);
    freemotif_table2obj = new freeMotif_table(arch1_mm_table);
    freemotif_table3obj = new freeMotif_table(arch1_mm_table);
    freemotif_table4obj = new freeMotif_table(arch1_mm_table);

    freemotif_table2obj.inst_index=1;
    freemotif_table3obj.inst_index=2;
    freemotif_table4obj.inst_index=3;

    freemotif_table1obj.diatonic_offset = 0;
    freemotif_table2obj.diatonic_offset = 1;
    freemotif_table3obj.diatonic_offset = 2;
    freemotif_table4obj.diatonic_offset = 3;

    freemotif_table1obj.pos_time = 0;
    freemotif_table2obj.pos_time = 1;
    freemotif_table3obj.pos_time = 2;
    freemotif_table4obj.pos_time = 3;

    freemotif_table1obj.scale_time = 1;
    freemotif_table2obj.scale_time = 2/3;
    freemotif_table3obj.scale_time = 1/3;
    freemotif_table4obj.scale_time = 4/3;

    freemotif_table1obj.renderfreemotif();
    freemotif_table2obj.renderfreemotif();
    freemotif_table3obj.renderfreemotif();
    freemotif_table4obj.renderfreemotif();

    freemotif_table1obj.pos_time = 4;
    freemotif_table1obj.scale_time = 0;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table2obj.pos_time = 5;
    freemotif_table2obj.diatonic_offset = 6;
    freemotif_table2obj.renderfreemotif();

    freemotif_table1obj.pos_time = 6;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 7;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 7.5;
    freemotif_table1obj.diatonic_offset = 2;
    freemotif_table1obj.renderfreemotif();

    freemotif_table1obj.pos_time = 8;
    freemotif_table1obj.diatonic_offset = 4;
    freemotif_table1obj.renderfreemotif();
}

void compound_score1_tables(){
    // Sample_Score1();
    score.addCallback(94,1);

    global_time_render_offset=0;
    // ss_descending_inv_scales2();
    ss_non_even_scaling_asc_tables();


    global_time_render_offset=8;
    ss_descending_inv_scales_tables();


    global_time_render_offset=8+32;
    ss_descending_inv_scales_b_tables();

    global_time_render_offset=44+32;
    ss_non_even_scaling_tables();
}
