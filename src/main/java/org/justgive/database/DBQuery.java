package org.justgive.database;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulates a JDBC query using the connection handling mechanism through
 * JPA.  Opens a connection, accepts bind variables, executes the query,
 * and closes the connection.
 *
 * Sample query call:
 *
 * // Obtain a DBQuery object through DBSessionFactory's newQuery() method
 * String queryString = "select * from charities where city = ? and state = ?";
 * DBQuery query = DBSessionFactory.getInstance().newQuery(queryString);
 *
 * // Bind query parameters by successive calls to bind()
 * // Note that the query string uses '?' for bind placeholders
 * query.bind("Los Angeles");
 * query.bind("CA");
 *
 * // Retrieve the results by calling DBQuery's execute() method
 * List<DBResultRow> resultSet = query.execute();
 *
 * // Iterate through the result rows, calling the get() method on the row.
 * // Results are all Strings, and the argument for get() is the column name.
 * for(DBResultRow row : resultSet){
 *     System.out.println("Name: " + row.get("charityname"));
 * }
 *
 * @author Jeff Risberg
 * @since 2014
 */
public class DBQuery<T> {
    protected static Logger jgLog = LoggerFactory.getLogger(DBQuery.class);

    protected String queryString;
    protected Connection connection;
    protected PreparedStatement statement;
    protected int bindVariableCount = 0;
    protected EntityManagerFactory emf;

    /** Constructor */
    public DBQuery(EntityManagerFactory emf, String queryString) throws DBException {
        this.queryString = queryString;
        this.emf = emf;

        // Get the connection and prepared statement
        open();
    }

    public List<DBResultRow> execute() throws DBException {
        List<DBResultRow> resultRows = new LinkedList<DBResultRow>();

        if (statement == null) {
            throw new DBException("Query statement undefined");
        }

        // Execute the query, close the prepared statement and connection
        try {
            // Execute the statement
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();

                ResultSetMetaData rsMeta = resultSet.getMetaData();
                int numberOfColumns = rsMeta.getColumnCount();

                // Go through the rows and create return values
                // Everything is a String
                while (resultSet.next()) {
                    DBResultRow resultRow = new DBResultRow();

                    for (int columnNumber = 1; columnNumber <= numberOfColumns; columnNumber++) {
                        String columnName = rsMeta.getColumnName(columnNumber);
                        String columnValue = resultSet.getString(columnNumber);

                        resultRow.put(columnName, columnValue);
                    }

                    resultRows.add(resultRow);
                }

                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception e) {
            throw new DBException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {

                }
            }
        }

        return resultRows;
    }

    protected void open() throws DBException {
        if (emf == null) {
            throw new DBException("No EMF for query");
        }
        if (queryString == null) {
            throw new DBException("No query string for query");
        }

        try {
            Session session = emf.createEntityManager().unwrap(Session.class);
            SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
            ConnectionProvider cp = sfi.getConnectionProvider();
            connection = cp.getConnection();

            statement = connection.prepareStatement(queryString);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    // Bind methods
    public void bind(String bindVar) throws DBException {
        try {
            if (bindVar == null) {
                statement.setNull(++bindVariableCount, Types.VARCHAR);
            } else {
                statement.setString(++bindVariableCount, bindVar);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void bind(Integer bindVar) throws DBException {
        try {
            if (bindVar == null) {
                statement.setNull(++bindVariableCount, Types.INTEGER);
            } else {
                statement.setInt(++bindVariableCount, bindVar);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void bind(Double bindVar) throws DBException {
        try {
            if (bindVar == null) {
                statement.setNull(++bindVariableCount, Types.DOUBLE);
            } else {
                statement.setDouble(++bindVariableCount, bindVar);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void bind(Date bindVar) throws DBException {
        try {
            if (bindVar == null) {
                statement.setNull(++bindVariableCount, Types.DATE);
            } else {
                statement.setTimestamp(++bindVariableCount, new Timestamp(bindVar.getTime()));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void bind(Boolean bindVar) throws DBException {
        try {
            if (bindVar == null) {
                statement.setNull(++bindVariableCount, Types.BOOLEAN);
            } else {
                statement.setBoolean(++bindVariableCount, bindVar);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
