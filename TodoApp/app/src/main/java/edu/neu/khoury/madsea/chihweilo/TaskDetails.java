package edu.neu.khoury.madsea.chihweilo;
import java.util.Date;

/**
 * A Task class to hold information of certain task.
 */
public class TaskDetails {

    private String title;
    private String details;
    private Date dateDeadline;
    private int tag;
    private boolean remind;
    private Date dateRemind;


    public TaskDetails(String title, String details, Date dateDeadline, int tag, boolean remind, Date dateRemind) {
        this.title = title;
        this.details = details;
        this.dateDeadline = dateDeadline;
        this.tag = tag;
        this.remind = remind;
        this.dateRemind = dateRemind;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public int getTag() {
        return tag;
    }

    public boolean isRemind() {
        return remind;
    }

    public Date getDateRemind() {
        return dateRemind;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public void setDateRemind(Date dateRemind) {
        this.dateRemind = dateRemind;
    }
}
