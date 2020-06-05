void globalscoresetup(){
      setcurrentkey(0);
}

void renderScore(){
     freemotif_1 = new freeMotif(even_ascent_mm);
     freemotif_1.renderfreemotif();

     freemotif_1.time_offset=2;
     freemotif_1.diatonic_offset=1;
     freemotif_1.renderfreemotif();

     freemotif_1.time_offset=4;
     freemotif_1.diatonic_offset=2;
     freemotif_1.renderfreemotif();

     freemotif_1.time_offset=6;
     freemotif_1.diatonic_offset=3;
     freemotif_1.renderfreemotif();

     setcurrentkey(1);

     freemotif_1.time_offset=8;
     freemotif_1.diatonic_offset=0;
     freemotif_1.renderfreemotif();

     freemotif_1.time_offset=10;
     freemotif_1.diatonic_offset=-1;
     freemotif_1.renderfreemotif();

     freemotif_1.time_offset=12;
     freemotif_1.diatonic_offset=-2;
     freemotif_1.renderfreemotif();

     freemotif_1.time_offset=14;
     freemotif_1.diatonic_offset=-3;
     freemotif_1.renderfreemotif();

}
