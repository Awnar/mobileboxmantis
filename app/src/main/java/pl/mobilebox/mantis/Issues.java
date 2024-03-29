package pl.mobilebox.mantis;

import java.util.List;

public class Issues {
    final List<Issue> issues;

    public Issues(List<Issue> issues) {
        this.issues = issues;
    }
}

class Issue /*implements Parcelable */{

    public class Priority {
        int id;
        String label;
    }

    public class Status {
        String label, color;
    }

    class Notes /*implements Parcelable*/{
        String text;
        Object1 reporter;
        String updated_at;
//        @Override
//        public int describeContents() {return 0;}
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            out.writeInt(id);
//            out.writeString(text);
//            out.writeString(new Gson().toJson(reporter));
//        }
//
//        public final Parcelable.Creator<Notes> CREATOR = new Parcelable.Creator<Notes>() {
//            public Notes createFromParcel(Parcel in) {
//                return new Notes(in);
//            }
//
//            public Notes[] newArray(int size) {
//                return new Notes[size];
//            }
//        };
//
//        private Notes(Parcel in) {
//            id = in.readInt();
//            text = in.readString();
//            reporter= new Gson().fromJson(in.readString(),Reporter.class);
//        }
    }

    public class Object1{
        String name;
    }

    String summary, description, created_at, updated_at;
    Priority priority;
    Status status;
    List<Notes> notes;
    Object1 reporter;
    Object1 project;
    Object1 handler;

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel out, int flags) {
//        out.writeInt(id);
//        out.writeString(summary);
//        out.writeString(description);
//        out.writeString(created_at);
//        out.writeString(updated_at);
//        out.writeString(new Gson().toJson(priority));
//        out.writeString(new Gson().toJson(status));
//        //out.writeList(notes);
//        //out.writeTypedList(Collections.singletonList(notes));
//        //out.writeString(new Gson().toJson(notes));
////        out.writeValue(priority);
////        out.writeInt(priority.id);
////        out.writeString(status.color);
//    }
//
//    public static final Parcelable.Creator<Issue> CREATOR = new Parcelable.Creator<Issue>() {
//        public Issue createFromParcel(Parcel in) {
//            return new Issue(in);
//        }
//
//        public Issue[] newArray(int size) {
//            return new Issue[size];
//        }
//    };
//
//    private Issue(Parcel in) {
//        id = in.readInt();
//        summary = in.readString();
//        description = in.readString();
//        created_at = in.readString();
//        updated_at = in.readString();
//
//        priority= new Gson().fromJson(in.readString(),Priority.class);
//        status= new Gson().fromJson(in.readString(),Status.class);
//
//        //notes = in.readArrayList(Notes.class.getClassLoader());
//        //notes= new Gson().fromJson(in.readString(),Notes.class);
//    }
}