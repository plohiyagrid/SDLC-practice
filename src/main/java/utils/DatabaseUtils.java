package utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseUtils {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DatabaseUtils() {
        Dotenv dotenv = Dotenv.load();
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dbName = dotenv.get("DB_NAME");
        String dbPort = dotenv.get("DB_PORT", "5432");

        this.username = dbUser;
        this.password = dbPassword;
        this.jdbcUrl = String.format("jdbc:postgresql://localhost:%s/%s", dbPort, dbName);
    }

    public DatabaseUtils(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Executes a SQL query that doesn't return results (e.g., INSERT, UPDATE, DELETE, CREATE).
     * Uses varargs to pass parameters to the prepared statement.
     *
     * @param query SQL query with placeholders
     * @param args  Arguments to replace placeholders in the query
     */
    public void execute(String query, Object... args) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }

    /**
     * Executes a SQL query that doesn't return results (e.g., INSERT, UPDATE, DELETE, CREATE).
     * Takes a Consumer that has access to the PreparedStatement for more complex parameter setting.
     *
     * @param query    SQL query with placeholders
     * @param consumer Consumer that sets parameters on the prepared statement
     */
    public void execute(String query, Consumer<PreparedStatement> consumer) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Let the consumer set parameters
            consumer.accept(statement);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }

    /**
     * Executes a query and returns a single result.
     * Returns null if no results found.
     * Throws an exception if more than one result is found.
     *
     * @param query  SQL query with placeholders
     * @param mapper Function to map ResultSet to desired object type
     * @param args   Arguments to replace placeholders in the query
     * @param <T>    Return type
     * @return Single result of type T or null if no results
     */
    public <T> T findOne(String query, Function<ResultSet, T> mapper, Object... args) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            // No results
            if (!resultSet.next()) {
                return null;
            }

            // Map the result
            T result = mapper.apply(resultSet);

            // Check if there's more than one result
            if (resultSet.next()) {
                throw new RuntimeException("Query returned more than one result: " + query);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }

    /**
     * Executes a query and returns multiple results as a list.
     * Returns an empty list if no results found.
     *
     * @param query  SQL query with placeholders
     * @param mapper Function to map each ResultSet row to desired object type
     * @param args   Arguments to replace placeholders in the query
     * @param <T>    Return type
     * @return List of results of type T (empty if no results)
     */
    public <T> List<T> findMany(String query, Function<ResultSet, T> mapper, Object... args) {
        List<T> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            // Map each result and add to list
            while (resultSet.next()) {
                results.add(mapper.apply(resultSet));
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }
}