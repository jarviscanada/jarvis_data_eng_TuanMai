package ca.jrvs.apps.jdbc;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {

    final static Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

    public static void main(String... args) {

        BasicConfigurator.configure();

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1111);

            logger.debug(order.toString());



            // CustomerDAO customerDAO = new CustomerDAO(connection);


            /*
            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Adams");
            customer.setEmail("random@email.com");
            customer.setPhone("(123) 456-7890");
            customer.setAddress("1234 Main St.");
            customer.setCity("Arlington");
            customer.setState("VA");
            customer.setZipCode("54321");

            Customer dbCustomer = customerDAO.create(customer);
            System.out.println(dbCustomer);

            dbCustomer = customerDAO.findById(dbCustomer.getId());
            System.out.println(dbCustomer);

            dbCustomer.setEmail("john.adams@email.com");
            dbCustomer = customerDAO.update(dbCustomer);
            System.out.println(dbCustomer);

            customerDAO.delete(dbCustomer.getId());


             */
            /* Update
            Customer customer = customerDAO.findById(10000);
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getEmail());

            customer.setEmail("tuan@jrvs.ca");
            customer = customerDAO.update(customer);
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getEmail());
            */



        } catch (SQLException ex){
            ex.printStackTrace();
        }


    }
}
