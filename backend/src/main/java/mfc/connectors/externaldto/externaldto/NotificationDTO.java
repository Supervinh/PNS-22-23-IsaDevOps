package mfc.connectors.externaldto.externaldto;

public class NotificationDTO {
    private String message;
    private String numberplate;

    public NotificationDTO() {
    }

    public NotificationDTO(String message, String numberplate) {
        this.message = message;
        this.numberplate = numberplate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumberplate() {
        return numberplate;
    }

    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }
}
