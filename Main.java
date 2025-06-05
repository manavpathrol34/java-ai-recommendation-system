package com.example.recommender;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Load sample data from a CSV file.
            // Ensure SampleData.csv is located at src/main/java/com/example/recommender/SampleData.csv
            // The CSV format should be: userID,itemID,preferenceValue
            DataModel model = new FileDataModel(new File("src/main/java/com/example/recommender/SampleData.csv"));

            // Compute user similarity using Pearson Correlation.
            // Pearson Correlation is a common measure of the linear relationship between two datasets.
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define a user neighborhood. Here, NearestNUserNeighborhood finds the 2 most similar users.
            // This neighborhood is used to find users whose preferences are similar to the target user.
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build a user-based recommender.
            // This recommender uses the data model, the defined neighborhood, and the similarity measure
            // to generate recommendations.
            GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend items for user with ID 1.
            // The '2' indicates that we want 2 recommendations.
            List<RecommendedItem> recommendations = recommender.recommend(1, 2);

            // Iterate through the list of recommended items and print their IDs and values.
            // The RecommendedItem object directly provides getItemID() and getValue() methods.
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Recommended Item ID: " + recommendation.getItemID() +
                                   ", Value: " + recommendation.getValue());
            }

        } catch (Exception e) {
            // Print any exceptions that occur during the process.
            e.printStackTrace();
        }
    }
}
