/**
 * AsyncResponse.java
 *
 * An interface class for MyAsyncTask, to be implemented by classes that want to send HTTP commands
 *
 * Created by tjluce on 11/27/15.
 */

package edu.calvin.cs262.scrumptious;

public interface AsyncResponse<String> {
    void processFinish(String output);
}
