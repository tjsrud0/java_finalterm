import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private JTextArea ProjectListArea;
    private List<ProjectDetails> projects;
    private List<ProjectDetails> submittedProjects;
    private JTextArea projectListArea;
    private String filePath;

    public Project() {
        projects = new ArrayList<>();
        autoAddProjects();
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

        JPanel buttonPanel = new JPanel();
        JButton viewProjectsBuTTon = new JButton("과제 목록 보기");
        buttonPanel.add(viewProjectsBuTTon);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        viewProjectsBuTTon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProjects();
            }
        });

        JButton fileButton = new JButton("제출 파일 선택");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    JOptionPane.showMessageDialog(null, "선택한 파일 : " + filePath);
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void autoAddProjects() {
        String[] majors = {"운영체제", "GUI", "JAVA2", "알고리즘 설계"};
        String[] titles = {"프로젝트 1", "프로젝트 2", "프로젝트 3"};

        Random random = new Random();
        for(int i = 0; i < 5; i++) {
            String major = majors[random.nextInt(majors.length)];
            String title = titles[random.nextInt(titles.length)];
            projects.add(new ProjectDetails(major, title, "2024-12-01"));
        }
    }

    private void displayProjects() {
        projectListArea.setText("과제 목록을 표시합니다.");
        for(ProjectDetails project : projects) {
            projectListArea.append(project.toString() + "\n");
        }
        projectListArea.setCaretPosition(0);
    }

    private void submitProject() {
        if(filePath == null || filePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제출할 파일을 선택하세요.");
            return;
        }

        JOptionPane.showMessageDialog(this, "과제가 제출되었습니다. " + filePath);
        filePath = null;
    }

    private void displaySubmittedProjects() {
        JTextArea submittedProjectsArea = new JTextArea();
        submittedProjectsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(submittedProjectsArea);

        if(submittedProjectsArea.isEmpty()) {
            submittedProjectsArea.setText("제출한 과제가 없습니다.");
        } else {
            for(ProjectDetails project : submittedProjects) {
                submittedProjectsArea.append(project.toString() + "\n");
            }
        }

        JOptionPane.showMessageDialog(this, scrollPane, "제출한 과제 목록", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Project();
    }
}
