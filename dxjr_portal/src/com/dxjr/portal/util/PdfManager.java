package com.dxjr.portal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * <p>
 * Description: pdf 文件 处理 <br />
 * </p>
 * 
 * @title PdfManager.java
 * @package com.dxjr.portal.util
 * @author HuangJun
 * @version 0.1 2015年6月4日
 */
public class PdfManager {
	
	private static final Logger logger = Logger.getLogger(PdfManager.class);

	/**
	 * <p>
	 * Description: 根据html转成pdf <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年6月3日
	 * @param htmlPath
	 * @param pdfPath
	 * @throws IOException
	 * @throws DocumentException
	 *             void
	 */
	public static void createPdf(String htmlPath, String pdfPath,String headerImagePath) throws IOException, DocumentException {
		File file = new File(pdfPath);
		Document document = new Document();
		PdfWriter pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(file));
		pdfwriter.open();
		document.open();
		try {
			//添加页眉
			addPdfHeader(document,pdfwriter,headerImagePath);
			// html文件
			InputStream htmlFileStream = new FileInputStream(htmlPath);
			InputStreamReader isr = new InputStreamReader(htmlFileStream, "UTF-8");
			XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, isr);
		} catch (Exception e) {
			logger.error("根据html转成pdf错误", e);
		}
		// 关闭
		document.close();
		pdfwriter.close();
	}

	/**
	 * <p>
	 * Description: 给pdf文件添加水印（协议下载专用） <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年6月3日
	 * @param InPdfFile
	 *            要加水印的原pdf文件路径
	 * @param outPdfFile
	 *            加了水印后要输出的路径
	 * @param markImagePath
	 *            水印图片路径
	 * @param targetPageSize
	 *            目标页数（即在哪页加水印）
	 * @throws Exception
	 *             void
	 */
	public static void addPdfMark(String InPdfFile, String outPdfFile, String markImagePath, int targetPageSize) throws Exception {
		PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outPdfFile));
		try {
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(markImagePath);// 插入水印
			img.setAbsolutePosition(350, 5);// x ,y越小，则距离下面越近

			// 最后一页加水印
			PdfContentByte under = stamp.getUnderContent(targetPageSize);
			under.addImage(img);
		} catch (BadElementException e) {
			logger.error("给pdf文件添加水印错误", e);
		}
		stamp.close();// 关闭
		// 删除
		File tempfile = new File(InPdfFile);
		if (tempfile.exists()) {
			tempfile.delete();
		}
	}
	
	/**
	 * 给PDF文件添加页眉（借款标协议下载专用）
	 * @author WangQianJin
	 * @param InPdfFile 要加页眉的原PDF文件路径
	 * @param outPdfFile 加了页眉后要输出的路径
	 * @param markImagePath 页眉图片路径
	 * @param targetPageSize 目标页数（即在哪页加页眉）
	 * @throws Exception
	 */
	public static void addPdfHeader(Document document,PdfWriter writer,String headerImagePath) {		
		HeaderFooter header=new HeaderFooter(headerImagePath);
        writer.setPageEvent(header);
	}
}