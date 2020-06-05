/*---GUI ZOOM SETTINGS--*/
int zoom_notelow=36;
int zoom_notehigh=84;

//by beat eg: <0 = display includes measure 1; 3 = measure includes measure 4>
float zoom_starttime=0;
float zoom_stoptime=15;

float truemod(float input, float modulus)
{
      return ((input%modulus+modulus)%modulus);
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
    }
}

float return_diaton(float dia_degree_in, float tonic_offset)
{
    // float currentnote = tonic_offset+scalebuffer[int(truemod(dia_degree_in+degreebuffer[k], 7))];
    // float currentoctave = (floor((pitches[i]+degreebuffer[k])/7))*12;
    float calc_diatonic = tonic_offset + scalebuffer[int(truemod(dia_degree_in,7.0))];
    float calc_octave_factor = (floor((dia_degree_in)/7))*12;
    return calc_diatonic+calc_octave_factor;
}
