package com.dxjr.portal.util;

import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import com.dxjr.portal.base.BaseController;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**   
 * <p>
 * Description:  操作PDF- 加水印  <br />
 * </p>
 * @title PdfLowManager.java
 * @package com.dxjr.portal.util 
 * @author HuangJun
 * @version 0.1 2015年6月9日
 */
public class PdfLowManager {
	private static final Logger logger = Logger.getLogger(PdfLowManager.class);

	/**
	 * <p>
	 * Description: 给pdf文件添加水印（借款标协议下载专用） <br />
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
	public static void addPdfMarkLow(String InPdfFile, String outPdfFile, String markImagePath, int targetPageSize) throws Exception {
		try {
			PdfReader reader = new PdfReader(InPdfFile);
			int n = reader.getNumberOfPages();
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outPdfFile));
			PdfContentByte under;
			Image img = Image.getInstance(markImagePath); // 插入水印
			img.setAbsolutePosition(350, 5);
			under = stamp.getUnderContent(n);
			under.addImage(img);
			stamp.close();

		} catch (Exception e) {
			logger.error("给pdf文件添加水印错误", e);
		}
	}
}