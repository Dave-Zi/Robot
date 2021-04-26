package Enums;

public enum BoardTypeEnum {
    EV3,
    GrovePi,
    Fake;

    // Get the board's matching port
    public IPortEnums getPortType(String port) {
        switch (valueOf(this.name())) {

            case EV3:
                return IEv3Port.getPortType(port);

            case GrovePi:
                return GrovePiPort.valueOf(port);

            case Fake:
                return FakeBoardPort.valueOf(port);
        }
        throw new IllegalArgumentException();
    }
}
