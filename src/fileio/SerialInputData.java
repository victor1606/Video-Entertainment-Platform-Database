package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private double average = 0;

    private int totalDuration = 0;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public double getAverage() {
        return average;
    }

    /**
     * This method sets a series' average rating based on its seasons average.
     * Implements inherited abstract method.
     */
    public void setAverage() {
        average = 0;
        for (Season season : seasons) {
            average = average + season.getAverage();
        }
        average = average / (double) seasons.size();
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    /**
     * This method sets a series' total duration.
     *
     */
    public void setTotalDuration() {
        for (Season season : seasons) {
            totalDuration = totalDuration + season.getDuration();
        }
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
