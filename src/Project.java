import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ProjectDetails {
    private String major;
    private String title;
    private String dueDate;

    public ProjectDetails(String major, String title, String dueDate) {
        this.major = major;
        this.title = title;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "전공 : " + major + ", 과제 : " + title + ", 마감일 : " + dueDate;
    }
}

public class Project extends JFrame {
    private List<ProjectDetails> projects;
    private List<ProjectDetails> submittedProjects;

    public Project() {
        projects = new ArrayList();
        submittedProjects = new ArrayList<>();
        setTitle("과제 관리 프로그램");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Project();
    }
}
