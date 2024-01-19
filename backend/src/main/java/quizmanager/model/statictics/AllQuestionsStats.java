package quizmanager.model.statictics;

import quizmanager.model.Quiz;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "ALL_QUESTIONS_STATS")
public class AllQuestionsStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ElementCollection
    @CollectionTable(name = "ANSWERS_MAP", joinColumns = @JoinColumn(name = "STATS_ID"))
    @MapKeyColumn(name = "ANSWER_KEY")
    @Column(name = "ANSWER_VALUE")
    private Map<String, Double> answersMap;
    private int totalAnswers;
    private boolean canBeModified;

    public AllQuestionsStats() {
    }

    public AllQuestionsStats(List<String> questionNames) {
        canBeModified = true;
        answersMap = new HashMap<>();
        for (String questionName : questionNames) {
            answersMap.put(questionName, (double) 0);
        }
        totalAnswers = 0;
    }


    public void addResponse(Map<String, Integer> response) {
        if (!canBeModified) {
            return;
        }
        for (String question: response.keySet()) {
            answersMap.merge(question, response.get(question).doubleValue(), Double::sum);
        }
        totalAnswers++;
    }

    public void calculatePercentages() {
        canBeModified = false;
        for (String question : answersMap.keySet()) {
            double correctAnswers = answersMap.get(question);
            answersMap.put(question, calculatePercentage(correctAnswers));
        }
    }

    private Double calculatePercentage(double correctAnswers) {
        return  correctAnswers / totalAnswers;
    }

    public Map<String, Double> getAnswersMap() {
        return answersMap;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("stats:\n");
        for (String question : answersMap.keySet()) {
            stringBuilder.append(String.format("%s: %.2f%% %n", question, 100 * answersMap.get(question)));
        }
        return stringBuilder.toString();
    }


}
