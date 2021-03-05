package Enums;

public enum Ev3SensorPort {
    _1(1),
    _2(2),
    _3(3),
    _4(4);

    public final int portNumber;
    Ev3SensorPort(int portNumber) {
        this.portNumber = portNumber;
    }
}
