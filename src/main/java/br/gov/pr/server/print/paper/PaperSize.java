package br.gov.pr.server.print.paper;

public enum PaperSize {
	A4 (595, 842);
	
	private final double width;
	private final double height;
	
	private PaperSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
