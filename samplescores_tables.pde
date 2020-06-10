void retrograde_examples(){
      //retrograde function tester
      //0. Dry
      //1. Tonal retrograde
      //2. Rhythmic retrograde
      //3. Full retrograde
      scoreTitle("Retrograde Examples");

      score.addCallback(32, 1);

      // freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
      // freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
      // freemotif_table1obj = new freeMotif_table(arch1_mm_table);
      // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
      freemotif_table1obj = new freeMotif_table(fanfare_table);

      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.scale_time=4;
      freemotif_table1obj.scale_dur=4;
      freemotif_table1obj.motif_name="original";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.duration_retrograde=1;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="duration";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=1;
      freemotif_table1obj.motif_name="position";
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.tonal_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.motif_name="pitch";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.tonal_retrograde=0;
      freemotif_table1obj.velocity_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="velocity";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.tonal_retrograde=1;
      freemotif_table1obj.velocity_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="pitch+vel";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.duration_retrograde=1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="pitch+vel+dur";
      freemotif_table1obj.renderfreemotif();

}

void basics_examples(){
      //retrograde function tester
      //0. Dry
      //1. Tonal retrograde
      //2. Rhythmic retrograde
      //3. Full retrograde
      scoreTitle("Basics Examples");

      score.addCallback(32, 1);

      freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
      freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
      freemotif_table1obj = new freeMotif_table(arch1_mm_table);
      freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
      // freemotif_table1obj = new freeMotif_table(fanfare_table);

      freemotif_table1obj.duration_retrograde=0;
      freemotif_table1obj.position_retrograde=0;
      freemotif_table1obj.scale_time=2;
      freemotif_table1obj.scale_dur=2;
      freemotif_table1obj.motif_name="original";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.diatonic_offset=-1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> diatonic -1";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic+1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> chromatic +1";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.scale_time=freemotif_table1obj.scale_time*2;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> timescale *2";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.scale_time=freemotif_table1obj.scale_time/2;
      freemotif_table1obj.scale_diatonic=2;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+8;
      freemotif_table1obj.motif_name="-> scale diatonically *2";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic+24;
      freemotif_table1obj.scale_diatonic=freemotif_table1obj.scale_diatonic*-1;
      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.motif_name="-> chrom +24 / Inv";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic-12;
      freemotif_table1obj.motif_name="-> chrom -12";
      freemotif_table1obj.renderfreemotif();
}

void fragmentation_examples(){
      //retrograde function tester
      //0. Dry
      //1. Tonal retrograde
      //2. Rhythmic retrograde
      //3. Full retrograde
      scoreTitle("Fragmentation Examples");

      score.addCallback(32, 1);

      freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
      freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
      freemotif_table1obj = new freeMotif_table(arch1_mm_table);
      // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
      // freemotif_table1obj = new freeMotif_table(fanfare_table);

      freemotif_table1obj.motif_name="original";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=1;
      freemotif_table1obj.motif_name="index from second note";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=2;
      freemotif_table1obj.motif_name="index from third note";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=0;
      freemotif_table1obj.frag_length=freemotif_table1obj.frag_length-1;
      freemotif_table1obj.motif_name="ignore last note";
      freemotif_table1obj.renderfreemotif();

      freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
      freemotif_table1obj.frag_index=0;
      freemotif_table1obj.frag_length=freemotif_table1obj.frag_length-1;
      freemotif_table1obj.motif_name="ignore last 2 notes";
      freemotif_table1obj.renderfreemotif();
}
