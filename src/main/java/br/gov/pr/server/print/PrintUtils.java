package br.gov.pr.server.print;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;

import br.gov.pr.server.print.paper.PaperSize;

public class PrintUtils {

	public static void print(PDDocument document, PaperSize paperSize) throws IOException, PrinterException {
    	
    	PrinterJob job = PrinterJob.getPrinterJob();
    	
    	job.setPageable(new PDFPageable(document));
    	
    	Paper paper = new Paper();
    	
    	paper.setSize(paperSize.getWidth(), paperSize.getHeight());

    	paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());

    	PageFormat pageFormat = new PageFormat();
    	
    	pageFormat.setPaper(paper);
    	
    	Book book = new Book();
    	
    	book.append(new PDFPrintable(document), pageFormat, document.getNumberOfPages());
    	
    	job.setPageable(book);
    	
    	job.print();
    }
	
}
