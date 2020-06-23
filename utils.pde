
float truemod(float input, float modulus)
{
      return ((input%modulus+modulus)%modulus);
}

void printArray(float[][] a)
{
  println("############ Array ############");
  for(int i=0; i < a.length; i ++){
    for(int j = 0; j < a[i].length; j++){
     print(" " + a[i][j]);
    }
    println();
  }
}

void printTable(Table input_table)
{
      //scan notes in table
      for(int tab_print_iter=0;tab_print_iter<input_table.getRowCount();tab_print_iter++){

          //
          print("pitch: ");
          print(input_table.getFloat(tab_print_iter, "pitch"));
          print(" - ");

          print("time_pos: ");
          print(input_table.getFloat(tab_print_iter, "time_pos"));
          print(" - ");

          print("duration: ");
          print(input_table.getFloat(tab_print_iter, "duration"));
          print(" - ");

          print("velocity: ");
          print(input_table.getFloat(tab_print_iter, "velocity"));
          print(" - ");

          print("timbre1: ");
          print(input_table.getFloat(tab_print_iter, "timbre1"));
          print(" - ");

          print("timbre2: ");
          print(input_table.getFloat(tab_print_iter, "timbre2"));
          print(" - ");
          println();
      }
      println("---------------");
}

void setcurrentkey(int keyindex)
{
    switch(keyindex){
      case 0:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.MAJOR[k];
        }
        break;
      case 1:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.MINOR[k];
        }
        break;
      case 2:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.HARMONIC_MINOR[k];
        }
        break;
      case 3:
        for (int k = 0; k <7; k++)
        {
          scalebuffer[k]=sc.PHRYGIAN[k];
        }
        break;
      default:
        break;
    }
}

float return_diaton(float dia_degree_in, float tonic_offset){

    // float currentnote = tonic_offset+scalebuffer[int(truemod(dia_degree_in+degreebuffer[k], 7))];
    // float currentoctave = (floor((pitches[i]+degreebuffer[k])/7))*12;
    float calc_diatonic = tonic_offset + scalebuffer[int(truemod(dia_degree_in,7.0))];
    float calc_octave_factor = (floor((dia_degree_in)/7))*12;
    return calc_diatonic+calc_octave_factor;
}
