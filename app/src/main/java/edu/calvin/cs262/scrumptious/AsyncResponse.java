package edu.calvin.cs262.scrumptious;

/**
 * Created by tjluce on 11/27/15.
 */
public interface AsyncResponse<String> {
    void processFinish(String output);
}
