package com.integracion.balances.controller;

import com.integracion.balances.service.BalancePDFGeneratorService;
import com.integracion.balances.service.DailyBalancePDFGeneratorService;
import com.integracion.balances.service.FilteredBalancePDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller("/pdf")
public class PDFExportController {

    @Autowired
    private final BalancePDFGeneratorService pdfGeneratorService;

    @Autowired
    private final FilteredBalancePDFGeneratorService filteredBalancePDFGeneratorService;

    @Autowired
    private final DailyBalancePDFGeneratorService dailyBalancePDFGeneratorService;

    public PDFExportController(BalancePDFGeneratorService pdfGeneratorService, FilteredBalancePDFGeneratorService filteredBalancePDFGeneratorService, DailyBalancePDFGeneratorService dailyBalancePDFGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.filteredBalancePDFGeneratorService = filteredBalancePDFGeneratorService;
        this.dailyBalancePDFGeneratorService = dailyBalancePDFGeneratorService;
    }

    @GetMapping("/pdf/generate")
    public void gereratePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.export(response);

    }

    @GetMapping("/pdf/generate/{date}")
    public void generateDailyBalancePDF(HttpServletResponse response, @PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        this.dailyBalancePDFGeneratorService.export(response, date);

    }

    @GetMapping("/pdf/generate/{initialDate}/{finalDate}")
    public void generatePDFByDate(HttpServletResponse response, @PathVariable("initialDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate initialDate,
                                  @PathVariable("finalDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate finalDate ) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        this.filteredBalancePDFGeneratorService.export(response, initialDate, finalDate);

    }

}
