package fileio;

import actor.ActorsAwards;
import utils.Utils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    private double videosAverage = 0;

    private int awardCount = 0;

    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public double getVideosAverage() {
        return videosAverage;
    }

    /**
     * This method sets an actor's average grade based on the shows he's acted in.
     *
     */
    public void setVideosAverage(final Input input) {
        int videoCount = 0;
        videosAverage = 0;

        for (String movieName : filmography) {
            MovieInputData movie = Utils.findMovieByName(movieName, input);
            if (movie != null && movie.getAverage() != 0) {
                videosAverage = videosAverage + movie.getAverage();
                videoCount++;
            }
        }

        for (String seriesName : filmography) {
            SerialInputData serial = Utils.findSerialByName(seriesName, input);
            if (serial != null && serial.getAverage() != 0) {
                videosAverage = videosAverage + serial.getAverage();
                videoCount++;
            }
        }

        videosAverage = videosAverage / videoCount;
    }

    /**
     * This method sets an actor's total award count.
     *
     */
    public void setAwardCount() {
        for (Map.Entry<ActorsAwards, Integer> award : awards.entrySet()) {
            awardCount = awardCount + award.getValue();
        }
    }

    public int getAwardCount() {
        return awardCount;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
