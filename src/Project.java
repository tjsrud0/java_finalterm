import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.filechooser.FileNameExtensionFilter;

class ProjectDetails {
    private String major;
    private String title;
    private String dueDate;

    public ProjectDetails(String major, String title, String dueDate) {
        this.major = major;
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getMajor() {
        return major;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
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
    private JTextField majorField, titleField, dueDateField, studentIdField, studentNameField;
    private JTextArea projectContentArea;
    private String filePath;
    private JPanel mainPanel, submitPanel;

    public Project() {
        projects = new ArrayList<>();
        submittedProjects = new ArrayList<>();
        autoAddProjects();

        setTitle("과제 관리 프로그램");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton viewProjectsBuTTon = new JButton("과제 목록 보기");
        JButton viewSubmittedButton = new JButton("제출한 과제 보기");
        buttonPanel.add(viewProjectsBuTTon);
        buttonPanel.add(viewSubmittedButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        projectListArea = new JTextArea();
        projectListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(projectListArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        submitPanel = new JPanel();
        submitPanel.setLayout(new BorderLayout());

        JPanel projectInfoPanel = new JPanel();
        projectInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        projectInfoPanel.add(new JLabel("전공:"));
        majorField = new JTextField(10);
        majorField.setEditable(false);
        projectInfoPanel.add(majorField);

        projectInfoPanel.add(new JLabel("과제:"));
        titleField = new JTextField(10);
        titleField.setEditable(false);
        projectInfoPanel.add(titleField);

        projectInfoPanel.add(new JLabel("마감일:"));
        dueDateField = new JTextField(10);
        dueDateField.setEditable(false);
        projectInfoPanel.add(dueDateField);

        submitPanel.add(projectInfoPanel, BorderLayout.NORTH);

        projectContentArea = new JTextArea(3, 20);
        projectContentArea.setEditable(false);
        projectContentArea.setBackground(Color.LIGHT_GRAY);
        submitPanel.add(new JLabel("과제 설명:"), BorderLayout.CENTER);
        submitPanel.add(projectContentArea, BorderLayout.CENTER);

        JPanel studentInfoPanel = new JPanel();
        studentInfoPanel.setLayout(new GridLayout(3, 2));

        studentInfoPanel.add(new JLabel("학번:"));
        studentIdField = new JTextField(10);
        studentInfoPanel.add(studentIdField);

        studentInfoPanel.add(new JLabel("이름:"));
        studentNameField = new JTextField(10);
        studentInfoPanel.add(studentNameField);


        JButton fileButton = new JButton("제출 파일 선택");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("모든 파일", "*.*"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    JOptionPane.showMessageDialog(null, "선택한 파일: " + filePath);
                }
            }
        });
        studentInfoPanel.add(fileButton);

        submitPanel.add(studentInfoPanel, BorderLayout.SOUTH); // 하단에 추가

        JButton submitButton = new JButton("제출하기");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitProject();
            }
        });
        submitPanel.add(submitButton, BorderLayout.EAST); // 제출 버튼


        add(mainPanel, "MainPanel");
        add(submitPanel, "SubmitPanel");


        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "MainPanel");


        viewProjectsBuTTon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProjects();
            }
        });

        viewSubmittedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySubmittedProjects();
            }
        });

        projectListArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // 더블 클릭 시
                    try {
                        int index = projectListArea.getLineOfOffset(projectListArea.getCaretPosition());
                        if (index >= 0 && index < projects.size()) {
                            ProjectDetails selectedProject = projects.get(index);
                            majorField.setText(selectedProject.getMajor());
                            titleField.setText(selectedProject.getTitle());
                            dueDateField.setText(selectedProject.getDueDate());
                            projectContentArea.setText("과제 내용."); // 과제 내용을 여기에 추가
                            CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                            cardLayout.show(getContentPane(), "SubmitPanel"); // 제출 패널로 전환
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace(); // 예외 처리
                    }
                }
            }
        });

        setVisible(true);
    }

    private void autoAddProjects() {
        String[] majors = {"운영체제", "GUI", "JAVA2", "알고리즘 설계"};
        String[] titles = {"프로젝트 1", "프로젝트 2", "프로젝트 3"};
        String[] dueDates = {"2024-12-01", "2024-12-10", "2024-12-15"};

        Random random = new Random();
        for(int i = 0; i < 5; i++) {
            String major = majors[random.nextInt(majors.length)];
            String title = titles[random.nextInt(titles.length)];
            String dueDate = dueDates[random.nextInt(dueDates.length)];
            projects.add(new ProjectDetails(major, title, dueDate));
        }
    }

    private void displayProjects() {
        projectListArea.setText("과제 목록 : \n ");
        for(ProjectDetails project : projects) {
            projectListArea.append(project.toString() + "\n");
        }
        projectListArea.setCaretPosition(0);
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

    private void submitProject() {
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();

        if(filePath == null || filePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제출할 파일을 선택하세요.");
            return;
        }

        if (projects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제출할 과제가 없습니다.");
            return;
        }

        ProjectDetails projectToSubmit = projects.get(0);
        submittedProjects.add(projectToSubmit);
        projects.remove(0);

        JOptionPane.showMessageDialog(this, "과제가 제출되었습니다. " + filePath);
        studentIdField.setText("");
        studentNameField.setText("");
        filePath = null;

        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "MainPanel");
    }

    public static void main(String[] args) {
        new Project();
    }
}
