import java.util.ArrayList;

public class fuzzy_set {
    public enum rpm_fuzzy {
        Positive_large(new double[]{0, 0.6, 1}),
        Positive_medium(new double[]{0.5, 1, 0.5}),
        Positive_small(new double[]{1, 0.4, 0});

        private final double[] value;
        rpm_fuzzy(double[] value) {
            this.value = value;
        }
    }

    public enum slope_fuzzy {
        Positive_large(new double[]{ 0, 0, 0.8, 1}),
        Positive_medium(new double[]{0, 0.7, 1, 0.7}),
        Positive_small(new double[]{ 0.6, 1, 0.6, 0}),
        Zero(new double[]{1, 0.2, 0, 0});
        private final double[] value;

        slope_fuzzy(double[] value) {
            this.value = value;
        }
    }

    public enum gear_fuzzy {
        Positive_large(new double[]{0, 0, 0, 0, 0.7, 0.8, 1}),
        Positive_medium(new double[]{0, 0, 0, 0, 0.6, 1, 0.7}),
        Positive_small(new double[]{0, 0, 0, 0.6, 1, 0.7, 0}),
        Zero(new double[]{0, 0, 0.4, 1, 0.5, 0, 0}),
        Negative_small(new double[]{0, 0.5, 1, 0.7, 0, 0, 0}),
        Negative_medium(new double[]{0.3, 1, 0.4, 0, 0, 0, 0}),
        Negative_large(new double[]{1, 0.5, 0, 0, 0, 0, 0});
        private final double[] value;

        gear_fuzzy(double[] value) {
            this.value = value;
        }
    }

    public fuzzy_set() {
    }

    public int fuzzification_rpm(double _rpm) {  // return index at rpm fuzzy
        if (_rpm >= 4000)
            return 2;
        if (_rpm >= 2000)
            return 1;
        else
            return 0;
    }

    public int fuzzification_slope(double _angle) {  // return index at slope fuzzy
        if (_angle >= 15)
            return 3;
        if (_angle >= 10)
            return 2;
        if (_angle >= 5)
            return 1;

        return 0;
    }

    public double[] fuzzy_rule(rpm_fuzzy rpm, slope_fuzzy slope, double input_rpm, double input_slope) {
        double min_alphacut;
        if (rpm == rpm_fuzzy.Positive_large && slope == slope_fuzzy.Positive_large) { // then NL
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Negative_large.value);
        }
        if (rpm == rpm_fuzzy.Positive_large && slope == slope_fuzzy.Positive_medium) { // then NS
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Negative_small.value);
        }
        if (rpm == rpm_fuzzy.Positive_large && slope == slope_fuzzy.Positive_small) { // then ZE
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Zero.value);
        }
        if (rpm == rpm_fuzzy.Positive_large && slope == slope_fuzzy.Zero) { // then PL
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Positive_large.value);
        }

        ///////////////////////////////////////
        if (rpm == rpm_fuzzy.Positive_medium && slope == slope_fuzzy.Positive_large) { // then NM
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Negative_medium.value);
        }
        if (rpm == rpm_fuzzy.Positive_medium && slope == slope_fuzzy.Positive_medium) {// then ZE
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Zero.value);
        }
        if (rpm == rpm_fuzzy.Positive_medium && slope == slope_fuzzy.Positive_small) {// then PS
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Positive_small.value);
        }
        if (rpm == rpm_fuzzy.Positive_medium && slope == slope_fuzzy.Zero) {// then PL
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Positive_large.value);
        }

        ///////////////////////////////////////
        if (rpm == rpm_fuzzy.Positive_small && slope == slope_fuzzy.Positive_large) {// then NS
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Negative_small.value);
        }
        if (rpm == rpm_fuzzy.Positive_small && slope == slope_fuzzy.Positive_medium) {// then PS
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Positive_small.value);
        }
        if (rpm == rpm_fuzzy.Positive_small && slope == slope_fuzzy.Positive_small) {// then PM
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Positive_medium.value);
        }
        if (rpm == rpm_fuzzy.Positive_small && slope == slope_fuzzy.Zero) {// then PL
            min_alphacut = Math.min(rpm.value[fuzzification_rpm(input_rpm)], slope.value[fuzzification_slope(input_slope)]);
            return min_fuzzy(min_alphacut, gear_fuzzy.Positive_large.value);
        }

        return new double[]{1, 1, 1, 1, 1, 1, 1};
    }

    public double[] mamdani_model(double input_rpm, double input_slope) {
        ArrayList<double[]> OutputFuzzy =  new ArrayList<>();;

        for (rpm_fuzzy rpm : rpm_fuzzy.values()) {
            for (slope_fuzzy slope:slope_fuzzy.values()) {
                OutputFuzzy.add( fuzzy_rule(rpm , slope , input_rpm , input_slope)  );
            }
        }

        return union_outputfuzzy(OutputFuzzy);
    }

    public static double[] min_fuzzy(double alphacut, double[] b) {
        double[] temp = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            temp[i] = Math.min(alphacut, b[i]);
        }
        return temp;
    }

    public static double[] union_outputfuzzy(ArrayList<double[]> _allOutputFuzzy) {
        double[] temp = _allOutputFuzzy.get(0).clone();

        for (double[] doubles : _allOutputFuzzy) {
            for (int j = 0; j < doubles.length; j++) {
                if (temp[j] < doubles[j]) temp[j] = doubles[j];
            }
        }
        return temp;
    }

    public double defuzzification(double[] output_fuzzy_set) {
        double max = 0;
        int gear_level = 0;
        for (int i = 0; i < output_fuzzy_set.length; i++)
            if (max < output_fuzzy_set[i]) {   // เลือกซ้ายสุดที่มากสุด
                max = output_fuzzy_set[i];
                gear_level = i;
            }
        return gear_level+1;
    }
}
