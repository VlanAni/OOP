package edu.taskchecker.vladimir.services;

import edu.taskchecker.vladimir.domain.Course;
import edu.taskchecker.vladimir.domain.GradingConfig;
import edu.taskchecker.vladimir.domain.Student;
import edu.taskchecker.vladimir.domain.TaskResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    public void generate(List<TaskResult> results, Course course) {
        if (results == null || course == null) {
            throw new IllegalArgumentException("must be non-null");
        }

        Map<Student, List<TaskResult>> byStudent = results.stream()
                .collect(Collectors.groupingBy(TaskResult::getStudent));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html><head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <style>\n")
                .append("        table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }\n")
                .append("        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }\n")
                .append("        th { background-color: #f2f2f2; }\n")
                .append("        .pass { background-color: #d4edda; }\n")
                .append("        .fail { background-color: #f8d7da; }\n")
                .append("        .summary { background-color: #e9ecef; font-weight: bold; }\n")
                .append("    </style>\n")
                .append("    </head><body>\n")
                .append("    <h1>Report</h1>\n");

        for (Map.Entry<Student, List<TaskResult>> entry : byStudent.entrySet()) {
            Student student = entry.getKey();
            List<TaskResult> studentResults = entry.getValue();

            html.append("<table>\n");
            html.append("<thead><tr>")
                    .append("<th>Student</th>")
                    .append("<th>Task ID</th>")
                    .append("<th>Tests</th>")
                    .append("<th>Checkstyle</th>")
                    .append("<th>Points</th>")
                    .append("</tr></thead><tbody>\n");

            for (TaskResult r : studentResults) {
                String tests = r.getTestStat().getPassedTests() + " / " + r.getTestStat().getFailedTests();
                String checkstyle = r.isMatchStyle() ? "OK" : "Fail";
                String rowClass = r.getScore() > 0 ? "pass" : "fail";

                html.append("<tr class=\"").append(rowClass).append("\">")
                        .append("<td>").append(student.getName()).append("</td>")
                        .append("<td>").append(r.getTask().getId()).append("</td>")
                        .append("<td>").append(tests).append("</td>")
                        .append("<td>").append(checkstyle).append("</td>")
                        .append("<td>").append(r.getScore()).append("</td>")
                        .append("</tr>\n");
            }

            double totalScore = studentResults.stream().mapToDouble(TaskResult::getScore).sum();
            String grade = calcGrade(totalScore, course.getGradingConfig());

            html.append("<tr class=\"summary\">")
                    .append("<td colspan=\"3\">ИТОГО</td>")
                    .append("<td>Mark: ").append(grade).append("</td>")
                    .append("<td>").append(totalScore).append("</td>")
                    .append("</tr>\n");

            html.append("</tbody></table>\n");
        }

        html.append("</body></html>");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.html", StandardCharsets.UTF_8))) {
            writer.write(html.toString());
            System.out.println("successfully save to report.html");
        } catch (IOException e) {
            System.err.println("error during writing into file: " + e.getMessage());
        }
    }

    private String calcGrade(double score, GradingConfig config) {
        if (score >= config.getExcellentThreshold()) return "Excellent";
        if (score >= config.getGoodThreshold())      return "Good";
        if (score >= config.getPassThreshold())      return "Троечка";
        return "ДВА!";
    }
}