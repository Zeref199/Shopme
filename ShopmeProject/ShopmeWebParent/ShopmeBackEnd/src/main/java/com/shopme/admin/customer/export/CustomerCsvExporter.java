package com.shopme.admin.customer.export;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Customer;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerCsvExporter extends AbstractExporter {

    public void export(List<Customer> listCustomers, HttpServletResponse response) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormatter.format(new Date());
        String fileName = "customer_" + timestamp + ".csv";

        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "E-mail", "First Name", "Last Name", "City", "State", "Country", "Enabled"};
        String[] fieldMapping = {"id", "email", "firstName", "lastName", "city", "state", "country", "enabled"};

        csvWriter.writeHeader(csvHeader);

        for(Customer customer : listCustomers){
            csvWriter.write(customer, fieldMapping);
        }

        csvWriter.close();
    }

}
