package utils;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.File;

public class TwitterManager {
    public static Status updateStatus(String status) throws TwitterException {
        return TwitterFactory.getSingleton().updateStatus(status);
    }

    public static Status updateStatus(String status, File[] media) throws TwitterException {
        StatusUpdate statusUpdate = new StatusUpdate(status);
        for (File file : media) {
            statusUpdate.setMedia(file);
        }
        return TwitterFactory.getSingleton().updateStatus(statusUpdate);
    }

    public static Status updateStatus(String status, File media) throws TwitterException {
        StatusUpdate statusUpdate = new StatusUpdate(status);
        statusUpdate.setMedia(media);
        return TwitterFactory.getSingleton().updateStatus(statusUpdate);
    }
}
