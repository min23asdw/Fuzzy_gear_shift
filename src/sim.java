public class sim {
    double weight;
    double wheel_size;
    double engine_force;
    double engine_rpm;
    double wheel_rpm;

    public sim( double _weight , double _wheel_size ){
        this.weight = _weight;
        this.wheel_size = _wheel_size;

    }

    public void rpm_hill(double _engine_force , double _slope, double _gear){
        this.engine_force = _engine_force ;
        this.engine_rpm =   wheel_size*  ( engine_force  - ( slope_fore(_slope) * ratio_by_gear(_gear) )   );
        this.wheel_rpm = engine_rpm * ratio_by_gear(_gear)  ;
    }

    public double slope_fore(double slope){
        double angel = Math.toRadians(slope);
        return ( this.weight * 10 * Math.sin(angel) ) + 0.1;
    }

    public double ratio_by_gear(double _gear) {
        return switch ((int) _gear) {
            case 1 -> 0.97;
            case 2 -> 0.98;
            case 3 -> 0.99;
            case 4 -> 1;
            case 5 -> 1.1;
            case 6 -> 1.2;
            case 7 -> 1.3;
            default -> 0;
        };
    }

    public double speed(){
        double speed =   wheel_rpm * wheel_size * 3.14 * 60 / 100000  ;
        return speed;
    }
}
