package SportEvent.registration;

public abstract class Registration {
    protected String contactName;
    protected String contactEmail;
    protected String contactPhone;

    public Registration(String contactName, String contactEmail, String contactPhone) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }
}
