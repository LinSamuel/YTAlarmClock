package sam.alarmclock;

/**
 * Created by Sam on 2/2/2017.
 */

public class VideoOption {

    private String videoURL;
    private String videoName;

    public VideoOption(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
