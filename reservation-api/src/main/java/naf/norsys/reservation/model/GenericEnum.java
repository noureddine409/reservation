package naf.norsys.reservation.model;

public class GenericEnum {

    public enum Gender {
        MALE,
        FEMALE
    }
    public enum ItemStatus{
       AVAILABLE,
        UNAVAILABLE

    }

    public enum ItemCategory {
        APARTMENT,
        VEHICULE,

    }

    public enum RoleName {
        USER,
        ADMIN
    }

    public enum JwtTokenType {
        ACCESS,
        REFRESH,
        RESET
    }
}
