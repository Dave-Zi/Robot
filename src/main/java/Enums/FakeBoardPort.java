package Enums;

public enum FakeBoardPort implements IPortEnums {
    A('A'),
    B('B'),
    C('C'),
    D('D');

    private char portChar;

    FakeBoardPort(char portChar) {
        this.portChar = portChar;
    }
}
