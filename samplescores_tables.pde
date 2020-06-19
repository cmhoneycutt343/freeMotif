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
        freemotif_table1obj.scale_diatonic=freemotif_table1obj.scale_diatonic* -1;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> chrom +24 / Inv";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.pos_tonic=freemotif_table1obj.pos_tonic-12;
        freemotif_table1obj.motif_name="-> chrom -12";
        freemotif_table1obj.renderfreemotif();
}

void fugue_examples(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Basics Examples");

        score.addCallback(96, 1);

        //harmonic minor
        setcurrentkey(2);

        freemotif_table1obj = new freeMotif_table(fugue_theme_mm_table);
        freemotif_table2obj = new freeMotif_table(fugue_theme_mm_table);
        freemotif_table3obj = new freeMotif_table(fugue_theme_mm_table);
        freemotif_table4obj = new freeMotif_table(fugue_theme_mm_table);

        /****round 1****/
        freemotif_table1obj.motif_name="original";
        freemotif_table1obj.frag_numnotes=33;
        freemotif_table1obj.renderfreemotif();

        /****round 2****/
        freemotif_table1obj.motif_name="default";
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(1);
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="-1 Oct round";
        freemotif_table2obj.inst_index=1;
        freemotif_table2obj.frag_numnotes=33;
        freemotif_table2obj.diatonic_offset=-7;
        freemotif_table2obj.pos_time=16;
        freemotif_table2obj.renderfreemotif();

        /****round 3****/
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(0);
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="default";
        freemotif_table2obj.pos_time=32;
        freemotif_table2obj.renderfreemotif();

        freemotif_table3obj.motif_name="-2 Oct; Scale time 4";
        freemotif_table3obj.inst_index=2;
        freemotif_table3obj.scale_time=4;
        freemotif_table3obj.scale_dur=4;
        freemotif_table3obj.frag_numnotes=4;
        freemotif_table3obj.diatonic_offset=-14;
        freemotif_table3obj.pos_time=32;
        freemotif_table3obj.renderfreemotif();

        /****round 4****/
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(1);
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(0);
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+freemotif_table1obj.frag_length;
        freemotif_table1obj.auto_retrograde(1);
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.pos_time=48;
        freemotif_table2obj.frag_numnotes=freemotif_table2obj.numnotes;
        freemotif_table2obj.renderfreemotif();
        freemotif_table2obj.pos_time=freemotif_table2obj.pos_time+freemotif_table2obj.frag_length;
        freemotif_table2obj.auto_retrograde(1);
        freemotif_table2obj.renderfreemotif();
        freemotif_table2obj.pos_time=freemotif_table2obj.pos_time+freemotif_table2obj.frag_length;
        freemotif_table2obj.auto_retrograde(0);
        freemotif_table2obj.renderfreemotif();

        freemotif_table3obj.motif_name="default";
        freemotif_table3obj.inst_index=2;
        freemotif_table3obj.scale_time=4;
        freemotif_table3obj.scale_dur=4;
        freemotif_table3obj.frag_numnotes=freemotif_table3obj.numnotes;
        freemotif_table3obj.diatonic_offset=-14;
        freemotif_table3obj.pos_time=48;
        freemotif_table3obj.renderfreemotif();

        freemotif_table4obj.motif_name="Retro; oct +1; Reveal frag Section";
        freemotif_table4obj.pos_time=48;
        freemotif_table4obj.inst_index=3;
        freemotif_table4obj.auto_retrograde(1);
        freemotif_table4obj.diatonic_offset=4;
        freemotif_table4obj.renderfreemotif();

        freemotif_table4obj.pos_time=freemotif_table4obj.pos_time+freemotif_table4obj.frag_length;
        freemotif_table4obj.auto_retrograde(0);
        freemotif_table4obj.renderfreemotif();

        freemotif_table4obj.pos_time=freemotif_table4obj.pos_time+freemotif_table4obj.frag_length;
        freemotif_table4obj.auto_retrograde(1);
        freemotif_table4obj.renderfreemotif();
}

void renderproperties_example(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Property Reset and Apply Transformations Examples");

        score.addCallback(32, 1);

        freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
        freemotif_table1obj = new freeMotif_table(arch1_mm_table);
        freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table1obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.duration_retrograde=0;
        freemotif_table1obj.position_retrograde=0;
        freemotif_table1obj.motif_name="original";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.diatonic_offset=-1;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> diatonic -1";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.scale_time=freemotif_table1obj.scale_time*.5;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> timescale *.5";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.reset_properties();
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="reset properties";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.scale_diatonic=-1;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> diatonic scale -1";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.diatonic_offset=7;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> diatonic 12";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.scale_time=freemotif_table1obj.scale_time*.5;
        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="-> timescale *.5";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.motif_name="render & reset";
        freemotif_table1obj.render_properties();
        freemotif_table1obj.renderfreemotif();
}

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
        // // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        freemotif_table1obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.duration_retrograde=0;
        freemotif_table1obj.position_retrograde=0;
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
        freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        freemotif_table1obj = new freeMotif_table(fanfare_table);

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
        freemotif_table1obj.frag_numnotes=freemotif_table1obj.frag_numnotes-1;
        freemotif_table1obj.motif_name="ignore last note";
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=freemotif_table1obj.pos_time+4;
        freemotif_table1obj.frag_index=0;
        freemotif_table1obj.frag_numnotes=freemotif_table1obj.frag_numnotes-1;
        freemotif_table1obj.motif_name="ignore last 2 notes";
        freemotif_table1obj.renderfreemotif();
}

void concat_examples(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Fragmentation Examples");

        score.addCallback(64, 1);

        // freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table1obj = new freeMotif_table(arch1_mm_table);
        // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table1obj = new freeMotif_table(fanfare_table);

        // freemotif_table2obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table2obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table2obj = new freeMotif_table(arch1_mm_table);
        // freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table2obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.motif_name="motif 1";
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="motif 2";
        freemotif_table2obj.pos_time=4;
        freemotif_table2obj.inst_index=1;
        freemotif_table2obj.renderfreemotif();

        freemotif_table1obj.motif_name="concat m1+m2";
        freemotif_table1obj.inst_index=2;
        freemotif_table1obj.concat_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.pos_time=8;
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=12;
        freemotif_table1obj.motif_name="remove notes 1,2";
        freemotif_table1obj.frag_index=2;
        freemotif_table1obj.inst_index=2;
        freemotif_table1obj.render_frag();
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=16;
        freemotif_table1obj.motif_name="remove last note";
        freemotif_table1obj.frag_numnotes=freemotif_table1obj.frag_numnotes-1;
        freemotif_table1obj.render_frag();
        freemotif_table1obj.renderfreemotif();

        freemotif_table1obj.pos_time=20;
        freemotif_table1obj.motif_name="prepend motif 2";
        freemotif_table1obj.prepend_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.renderfreemotif();
}


void mapping_examples(){
        //retrograde function tester
        //0. Dry
        //1. Tonal retrograde
        //2. Rhythmic retrograde
        //3. Full retrograde
        scoreTitle("Mapping Examples");

        score.addCallback(64, 1);

        // freemotif_table1obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table1obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table1obj = new freeMotif_table(arch1_mm_table);
        // freemotif_table1obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table1obj = new freeMotif_table(fanfare_table);

        // freemotif_table2obj = new freeMotif_table(simpleforms_1_table);
        freemotif_table2obj = new freeMotif_table(simpleforms_2_table);
        // freemotif_table2obj = new freeMotif_table(arch1_mm_table);
        // freemotif_table2obj = new freeMotif_table(even_ascent_mm_table);
        // freemotif_table2obj = new freeMotif_table(fanfare_table);

        freemotif_table1obj.motif_name="motif 1";
        freemotif_table1obj.scale_time=8;
        freemotif_table1obj.scale_dur=8;
        freemotif_table1obj.render_properties();
        freemotif_table1obj.renderfreemotif();

        freemotif_table2obj.motif_name="motif 2";
        freemotif_table2obj.scale_time=8;
        freemotif_table2obj.scale_dur=8;
        freemotif_table2obj.inst_index=1;
        freemotif_table2obj.render_properties();
        freemotif_table2obj.pos_time=16;
        freemotif_table2obj.renderfreemotif();

        freemotif_table1obj.subdivmap_mm(freemotif_table2obj.notearray_table);
        freemotif_table1obj.inst_index=2;
        freemotif_table1obj.pos_time=32;
        freemotif_table1obj.renderfreemotif();
}
