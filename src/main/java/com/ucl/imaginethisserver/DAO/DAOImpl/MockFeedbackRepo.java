//package com.ucl.imaginethisserver.DAO.DAOImpl;
//
//import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
//import com.ucl.imaginethisserver.DAO.FeedbackDAO;
//import com.ucl.imaginethisserver.Model.Feedback;
//import com.ucl.imaginethisserver.Model.Vote;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public class MockFeedbackRepo implements FeedbackDAO {
//    private final List<Feedback> feedbacks = new ArrayList<>();
//
//    public MockFeedbackRepo() {
//        this.feedbacks.add(
//                new Feedback(UUID.randomUUID(), "MgWqYTZMdjG26oA1CxbWaE", UUID.randomUUID(),
//                        "John", System.currentTimeMillis(), "Good prototype!")
//        );
//        this.feedbacks.add(
//                new Feedback(UUID.randomUUID(), "MgWqYTZMdjG26oA1CxbWaE", UUID.randomUUID(),
//                        "Elena", System.currentTimeMillis(), "I don't like the background color of the theme. Too dark.")
//        );
//        this.feedbacks.add(
//                new Feedback(UUID.randomUUID(), "Eg1xBU6j7499du1AX2lTvQ", UUID.randomUUID(),
//                        "Alex", System.currentTimeMillis(), "Buttons can be disabled when the user is not logged in.")
//        );
//    }
//
//    @Override
//    public List<Feedback> getAllFeedbacks(String projectID) {
//        List<Feedback> result = new ArrayList<>();
//        // simulate querying process
//        for (Feedback f :
//                feedbacks) {
//            if (f.getProjectID().equals(projectID)) {
//                result.add(f);
//            }
//        }
//        if (result.size() == 0) {
//            throw new ProjectNotFoundException("Project Not Found");
//        }
//        return result;
//    }
//
//    @Override
//    public Feedback getFeedbackByID(String projectID, UUID feedbackID) {
//        Feedback result = null;
//        for (Feedback f :
//                feedbacks) {
//            if (f.getProjectID().equals(projectID) && f.getFeedbackID().equals(feedbackID)) {
//                result = f;
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public boolean addNewFeedback(String projectID, Feedback feedback) {
//        this.feedbacks.add(feedback);
//        return true;
//    }
//
//    @Override
//    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
//        boolean result = false;
//        for (Feedback f :
//                feedbacks) {
//            if (f.getProjectID().equals(projectID) && f.getFeedbackID().equals(feedbackID)
//                    && f.getUserID().equals(vote.getUserID())) {
//                if (vote.getVote() == 1) {
//                    f.setUpvotes(f.getUpvotes() + 1);
//                    result = true;
//                } else if (vote.getVote() == -1) {
//                    f.setDownvotes(f.getDownvotes() + 1);
//                    result = true;
//                }
//            }
//        }
//        return result;
//    }
//}
