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
    private JTextArea projectListArea;

    public Project() {
        projects = new ArrayList();
        submittedProjects = new ArrayList<>();
        setTitle("과제 관리 프로그램");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        projectListArea = new JTextArea();
        projectListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(projectListArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Project();
    }
}
