package edu.neu.khoury.madsea.chihweilo;

public class TodoItem {

    private boolean checked;
    private String textTitle;
    private String textDeadline;

    public TodoItem(boolean checked, String textTitle, String textDeadline) {
        this.checked = checked;
        this.textTitle = textTitle;
        this.textDeadline = textDeadline;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextDeadline() {
        return textDeadline;
    }

    public void setTextDeadline(String textDeadline) {
        this.textDeadline = textDeadline;
    }






}
