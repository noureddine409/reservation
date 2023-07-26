package naf.norsys.reservation.exception;

public class ItemAvailabilityException extends BusinessException{

    public ItemAvailabilityException() {
        super();
    }

    public ItemAvailabilityException(String defaultMessage, String key, Object[] args) {
        super(defaultMessage, key, args);
    }

    public ItemAvailabilityException(String defaultMessage, Throwable cause, String key, Object[] args) {
        super(defaultMessage, cause, key, args);
    }

    public ItemAvailabilityException(Throwable cause, String key, Object[] args) {
        super(cause, key, args);
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    @Override
    public Object[] getArgs() {
        return super.getArgs();
    }
}
