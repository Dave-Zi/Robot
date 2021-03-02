package Enums;

public enum Ev3DrivePort implements IPortEnums {
    A('A'),
    B('B'),
    C('C'),
    D('D');

    public final char portChar;
    Ev3DrivePort(char portChar) {
        this.portChar = portChar;
    }
}
