package es.upm.miw.devops.services.criteria;

public class UserFindCriteria {

    private Boolean active;
    private Boolean billable;

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

    public boolean hasActive() {
        return active != null;
    }

    public boolean hasBillable() {
        return billable != null;
    }
}