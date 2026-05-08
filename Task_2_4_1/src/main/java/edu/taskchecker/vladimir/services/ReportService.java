package edu.taskchecker.vladimir.services;

import edu.taskchecker.vladimir.domain.Course;
import edu.taskchecker.vladimir.domain.GradingConfig;
import edu.taskchecker.vladimir.domain.Student;
import edu.taskchecker.vladimir.domain.TaskResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    public String generateHtml(List<TaskResult> results, Course course) {
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
                .append("        .summary { font-weight: bold; background-color: #eee; }\n")
                .append("    </style>\n")
                .append("</head><body>\n")
                .append("<h1>Checking report</h1>\n");

        for (var entry : byStudent.entrySet()) {
            Student student = entry.getKey();
            List<TaskResult> studentResults = entry.getValue();

            html.append("<h2>Student: ").append(student.getName()).append(" (").append(student.getNickname()).append(")</h2>\n")
                    .append("<table><thead><tr>\n")
                    .append("    <th>Task-Id</th><th>Task-Name</th><th>Building</th><th>Tests</th><th>Style</th><th>Points</th>\n")
                    .append("</tr></thead><tbody>\n");

            for (TaskResult r : studentResults) {
                String build = r.isSuccessBuild() ? "OK" : "FAILED";
                String style = r.isMatchStyle() ? "OK" : "FAILED";
                String tests = r.getTestStat().getPassedTests() + "/" +
                        (r.getTestStat().getPassedTests() + r.getTestStat().getFailedTests());

                html.append("<tr>")
                        .append("<td>").append(r.getTask().getId()).append("</td>")
                        .append("<td>").append(r.getTask().getName()).append("</td>")
                        .append("<td>").append(build).append("</td>")
                        .append("<td>").append(tests).append("</td>")
                        .append("<td>").append(style).append("</td>")
                        .append("<td>").append(r.getScore()).append("</td>")
                        .append("</tr>\n");
            }

            double totalScore = studentResults.stream().mapToDouble(TaskResult::getScore).sum();
            String grade = calcGrade(totalScore, course.getGradingConfig());

            html.append("<tr class=\"summary\">")
                    .append("<td colspan=\"4\">FINAL (Grade: ").append(grade).append(")</td>")
                    .append("<td>").append(totalScore).append("</td>")
                    .append("</tr>\n");

            html.append("</tbody></table>\n");
        }

        html.append("</body></html>");
        return html.toString();
    }

    private String calcGrade(double score, GradingConfig config) {
        if (score >= config.getExcellentThreshold()) return "Excellent";
        if (score >= config.getGoodThreshold())      return "Good";
        if (score >= config.getPassThreshold())      return "Satisfactory";
        return "Unsatisfactory";
    }
}