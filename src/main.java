import java.io.*;
import java.util.ArrayList;

public class main {

    public static void main(String[] args) throws IOException {
        ArrayList<double[]> data_i = new ArrayList<>();
        FileInputStream fstream = new FileInputStream("src/simulation_track.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String data;

        while ((data = br.readLine()) != null) { // each line
                String[] eachLine = data.split(" ");
                if(eachLine.length == 0) continue;
                double[] temp = new double[eachLine.length];
                for(int  i =0 ; i<eachLine.length;i++){
                    double dataNum = Double.parseDouble(eachLine[i]);
                    temp[i] = dataNum ;
                }
                data_i.add(temp);
        }

        fuzzy_set fs = new fuzzy_set();
        sim simulation =  new sim(60,5);

        for (double[] input : data_i) {
            ArrayList<Double> output = new ArrayList<>();
            ArrayList<Double> speed = new ArrayList<>();
            for (int j = 0 ; j < 16 ; j++){
                double[] output_fuzzy =  (fs.mamdani_model(input[0] , j));
                double gear = fs.defuzzification(output_fuzzy);
                output.add( gear );

                simulation.rpm_hill(input[0], j, 7);
                speed.add(simulation.speed());
            }
            StringBuilder gear_level = new StringBuilder();
            StringBuilder speed_km = new StringBuilder();
            for (double gear: output) {
                gear_level.append(gear).append("\t");
            }
            for (Double skm:speed){
                speed_km.append(skm.intValue()).append("\t");
            }

            System.out.print("Gear \t");
            System.out.println(gear_level);
            System.out.print("Speed \t");
            System.out.println(speed_km);

        }
    }
}
