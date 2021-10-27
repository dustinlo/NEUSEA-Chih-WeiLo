package edu.neu.khoury.madsea.chihweilo.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Todo")
public class ToDoItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "checked")
    private boolean isChecked;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "details")
    private String details;

    @ColumnInfo(name = "deadline")
    private String dateDeadline;

    @ColumnInfo(name = "tag")
    private int tag;

    @ColumnInfo(name = "reminded")
    private boolean isReminded;

    @ColumnInfo(name = "dateRemind")
    private String dateRemind;

    public ToDoItem(){}

    protected ToDoItem(Parcel in) {
        uid = in.readInt();
        isChecked = in.readByte() != 0;
        title = in.readString();
        details = in.readString();
        dateDeadline = in.readString();
        tag = in.readInt();
        isReminded = in.readByte() != 0;
        dateRemind = in.readString();
    }

    public static final Creator<ToDoItem> CREATOR = new Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    @Override
    public String toString() {
        return "ToDoItem{" +
                "uid=" + uid +
                ", isChecked=" + isChecked +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", dateDeadline='" + dateDeadline + '\'' +
                ", tag=" + tag +
                ", isReminded=" + isReminded +
                ", dateRemind='" + dateRemind + '\'' +
                '}';
    }

    public boolean isChecked() {
        return isChecked;
    }

    public int getUid(){
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(String dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isReminded() {
        return isReminded;
    }

    public void setReminded(boolean reminded) {
        isReminded = reminded;
    }

    public String getDateRemind() {
        return dateRemind;
    }

    public void setDateRemind(String dateRemind) {
        this.dateRemind = dateRemind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(dateDeadline);
        dest.writeInt(tag);
        dest.writeByte((byte) (isReminded ? 1 : 0));
        dest.writeString(dateRemind);
    }
}
