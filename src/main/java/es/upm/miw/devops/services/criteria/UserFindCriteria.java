package es.upm.miw.devops.services.criteria;

public class UserFindCriteria {

    private Boolean active;
    private String mobile;
    private Boolean billable;

    public UserFindCriteria() {
    }

    public UserFindCriteria(Boolean active, String mobile) {
        this.active = active;
        this.mobile = mobile;
    }

    public UserFindCriteria(Boolean active, String mobile, Boolean billable) {
        this.active = active;
        this.mobile = mobile;
        this.billable = billable;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean hasMobile() {
        return mobile != null && !mobile.isBlank();
    }

    public boolean hasActive() {
        return active != null;
    }

    public boolean hasBillable() {
        return billable != null;
    }
}