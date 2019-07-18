package pl.mobilebox.mantis;

import java.util.List;

public class Issues {
    //public final int id;
    public final List<Issue> issues;

    public class Issue{
        int id;
        String summary,description;
    }

    public Issues(List<Issue> issues) {
        //this.id = id;
        this.issues = issues;
    }
}
