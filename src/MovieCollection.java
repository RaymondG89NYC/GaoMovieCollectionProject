import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.util.Collection.*;

import static java.util.Collections.sort;

public class MovieCollection
{
    //For some reason it says Arturi commited these changes. I don't know how to change the name
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> actors;
    private ArrayList<String> genres;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);

        //Task 4
        //Creates an arraylist of actors
        actors = new ArrayList<String>();
        for(Movie m: movies){
            String allCasts = m.getCast();
            while(allCasts.indexOf("|") != -1){
                actors.add(allCasts.substring(0, allCasts.indexOf("|")));
                allCasts = allCasts.substring(allCasts.indexOf("|")+1);
            }
        }
        //After arraylist is created, a set is used to remove all duplicates i hope
        Set<String> set = new LinkedHashSet<String>();
        set.addAll(actors);
        actors.clear();
        actors.addAll(set);
        //Collection sort into alphabetical order
        sort(actors);
        System.out.println(actors);
        System.out.println();

        //Task 5
        //Creates an arraylist of genres
        genres = new ArrayList<String>();
        for(Movie m: movies){
            String allGenres = m.getGenres();
            while(allGenres.indexOf("|") != -1){
                genres.add(allGenres.substring(0, allGenres.indexOf("|")));
                allGenres = allGenres.substring(allGenres.indexOf("|")+1);
            }
        }
        //After arraylist is created, a set is used to remove all duplicates i hope
        set = new LinkedHashSet<String>();
        set.addAll(genres);
        genres.clear();
        genres.addAll(set);
        //Collection sort into alphabetical order
        sort(genres);
        System.out.println(genres);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>();

        // search through ALL actors in collection
        for (int i = 0; i < actors.size(); i++)
        {
            String name = actors.get(i);
            name = name.toLowerCase();

            if (name.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(actors.get(i));
            }
        }

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which actor would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedActor = results.get(choice - 1);
        System.out.println("These are all the movies that " + selectedActor + " has appeared in:");

        // arraylist to hold search results
        ArrayList<Movie> movieResults = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            String movieCast = movies.get(i).getCast();
            movieTitle = movieTitle.toLowerCase();

            if (movieCast.indexOf(selectedActor) != -1)
            {
                //add the Movie objest to the results list
                movieResults.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(movieResults);

        // now, display them all to the user
        for (int i = 0; i < movieResults.size(); i++)
        {
            String title = movieResults.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movieResults.get(movieChoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getKeywords();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        for (int i = 0; i < genres.size(); i++)
        {
            String genre = genres.get(i);

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + genre);
        }
        System.out.println("Which genre would you like to search?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = genres.get(choice - 1);
        System.out.println("These are all the movies that are in the " + selectedGenre + " genre:");

        // arraylist to hold search results
        ArrayList<Movie> movieResults = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            String movieGenres = movies.get(i).getGenres();
            movieTitle = movieTitle.toLowerCase();

            if (movieGenres.indexOf(selectedGenre) != -1)
            {
                //add the Movie objest to the results list
                movieResults.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(movieResults);

        // now, display them all to the user
        for (int i = 0; i < movieResults.size(); i++)
        {
            String title = movieResults.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movieResults.get(movieChoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Double> allRatings = new ArrayList<Double>();
        for(Movie m: movies){
            allRatings.add(m.getUserRating());
        }

        ArrayList<Movie> moviesSortedByRatings = new ArrayList<Movie>();
        for(int i = 0; i < movies.size(); i++){
            moviesSortedByRatings.add(movies.get(i));
        }

        //Used insertion sort to sort the movies according to rating
        for(int i = 1; i < movies.size(); i++){
            double temp = allRatings.get(i);
            Movie tempMovie = movies.get(i);
            int possibleIndex = i;
            while(possibleIndex>0 && temp < allRatings.get(possibleIndex-1)){
                allRatings.set(possibleIndex, allRatings.get(possibleIndex-1));
                moviesSortedByRatings.set(possibleIndex, moviesSortedByRatings.get(possibleIndex-1));
                possibleIndex--;
            }
            allRatings.set(possibleIndex, temp);
            moviesSortedByRatings.set(possibleIndex, tempMovie);
        }

        System.out.println("These are the top 50 user rated movies:");
        // now, display them all to the user
        for (int i = allRatings.size()-1; i >= allRatings.size()-50; i--)
        {
            String title = moviesSortedByRatings.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = -(i - moviesSortedByRatings.size());

            System.out.println("" + choiceNum + ". " + title + " | " + moviesSortedByRatings.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesSortedByRatings.get(-(movieChoice-moviesSortedByRatings.size()));

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Integer> allRevenue = new ArrayList<Integer>();
        for(Movie m: movies){
            allRevenue.add(m.getRevenue());
        }

        ArrayList<Movie> moviesSortedByRevenue = new ArrayList<Movie>();
        for(int i = 0; i < movies.size(); i++){
            moviesSortedByRevenue.add(movies.get(i));
        }

        //Used insertion sort to sort the movies according to revenue
        for(int i = 1; i < movies.size(); i++){
            int temp = allRevenue.get(i);
            Movie tempMovie = movies.get(i);
            int possibleIndex = i;
            while(possibleIndex>0 && temp < allRevenue.get(possibleIndex-1)){
                allRevenue.set(possibleIndex, allRevenue.get(possibleIndex-1));
                moviesSortedByRevenue.set(possibleIndex, moviesSortedByRevenue.get(possibleIndex-1));
                possibleIndex--;
            }
            allRevenue.set(possibleIndex, temp);
            moviesSortedByRevenue.set(possibleIndex, tempMovie);
        }

        System.out.println("These are the top 50 highest revenue movies:");
        // now, display them all to the user
        for (int i = allRevenue.size()-1; i >= allRevenue.size()-50; i--)
        {
            String title = moviesSortedByRevenue.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = -(i - moviesSortedByRevenue.size());

            System.out.println("" + choiceNum + ". " + title + " | " + moviesSortedByRevenue.get(i).getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesSortedByRevenue.get(-(movieChoice-moviesSortedByRevenue.size()));

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}