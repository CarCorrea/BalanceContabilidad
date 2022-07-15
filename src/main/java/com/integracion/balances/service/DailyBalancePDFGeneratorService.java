package com.integracion.balances.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
public class DailyBalancePDFGeneratorService {

    @Autowired
    private BalanceService balanceService;

    Locale locale = Locale.US ;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String currentDateTime = dateFormat.format(new Date());

    public void export(HttpServletResponse response, LocalDate date) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(documentDate());
        document.add(documentTitle());
        document.add(documentSubtitle(date));
        document.add(balanceTitle());
        document.add(boletaParragraph(date));
        document.add(facturaTitle());
        document.add(facturaParragraph(date));
        document.add(totalBalanceParragraph(date));
        document.close();
    }

    private Paragraph documentDate(){

        Font detailFont = FontFactory.getFont(FontFactory.HELVETICA);
        detailFont.setSize(6);

        Paragraph currentDate = new Paragraph("Fecha emisión: " + currentDateTime, detailFont);
        currentDate.setAlignment(Paragraph.ALIGN_RIGHT);
        currentDate.setSpacingAfter(20);

        return currentDate;
    }

    private Paragraph documentTitle(){

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);

        Paragraph documentTitle = new Paragraph("Balance diario", titleFont);
        documentTitle.setAlignment(Paragraph.ALIGN_CENTER);

        return documentTitle;
    }

    private Paragraph documentSubtitle(LocalDate date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE);
        subtitleFont.setSize(14);

        Paragraph documentSubtitle = new Paragraph(date.format(formatter), subtitleFont);
        documentSubtitle.setAlignment(Paragraph.ALIGN_CENTER);

        return documentSubtitle;
    }

    private Paragraph balanceTitle(){
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        paragraphFont.setSize(12);

        Paragraph boletaSectionTitle = new Paragraph("Boletas registradas", paragraphFont);
        boletaSectionTitle.setAlignment(Paragraph.ALIGN_LEFT);

        return boletaSectionTitle;
    }

    private Paragraph boletaParragraph(LocalDate date) {

        Font detailFont = FontFactory.getFont(FontFactory.HELVETICA);
        detailFont.setSize(10);

        Paragraph boletaDetail = new Paragraph("Boletas emitidas en periodo: " + balanceService.getDailyBalance(date).getBoletas().getCantidadDocumentos() + " boletas \n " +
                "Monto total boletas: $" + currencyFormatter("boleta", date), detailFont);
        boletaDetail.setSpacingBefore(15);
        boletaDetail.setIndentationLeft(25);
        return boletaDetail;
    }

    private Paragraph facturaTitle(){

        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        paragraphFont.setSize(12);

        Paragraph facturaSectionTitle = new Paragraph("Facturas registradas", paragraphFont);
        facturaSectionTitle.setAlignment(Paragraph.ALIGN_LEFT);
        facturaSectionTitle.setSpacingBefore(20);

        return facturaSectionTitle;
    }

    private Paragraph facturaParragraph(LocalDate date) {

        Font detailFont = FontFactory.getFont(FontFactory.HELVETICA);
        detailFont.setSize(10);

        Paragraph facturasDetail = new Paragraph("Facturas registradas en periodo: "
                + balanceService.getDailyBalance(date).getFacturas().getCantidadDocumentos() + " facturas \n " +
                "Monto total facturas : $" + currencyFormatter("factura", date), detailFont);
        facturasDetail.setSpacingBefore(15);
        facturasDetail.setIndentationLeft(25);
        return facturasDetail;
    }

    private Paragraph totalBalanceParragraph(LocalDate date) {

        Font detailFont = FontFactory.getFont(FontFactory.HELVETICA);
        detailFont.setSize(10);

        Paragraph balanceDetail = new Paragraph("Balance periodo: $" +
                currencyFormatter("balance", date), detailFont);
        balanceDetail.setSpacingBefore(20);
        balanceDetail.setIndentationLeft(25);

        return balanceDetail;
    }

    private String currencyFormatter(String documento, LocalDate date){

        String monto = "";

        NumberFormat numberFormatter;
        numberFormatter = NumberFormat.getNumberInstance(locale);

        if (documento=="boleta"){
            monto = numberFormatter.format(balanceService.getDailyBalance(date).getBoletas().getMontoTotal());

        }else if (documento=="factura"){
            monto = numberFormatter.format(balanceService.getDailyBalance(date).getFacturas().getMontoTotal());

        }else {
            monto = numberFormatter.format(balanceService.getDailyBalance(date).getBalanceFinal());
        }
        return monto;
    }
}
