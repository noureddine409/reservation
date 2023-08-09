package naf.norsys.reservation.model;

import javax.persistence.Embeddable;

@Embeddable
public class Parameter {
    private String key;
    private String value;
}
